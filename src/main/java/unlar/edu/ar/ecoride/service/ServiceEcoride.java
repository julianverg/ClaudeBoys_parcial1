package unlar.edu.ar.ecoride.service;

import unlar.edu.ar.ecoride.Exeptions.ExcepcionBateriaInsuficiente;
import unlar.edu.ar.ecoride.Exeptions.ExcepcionCobroFallido;
import unlar.edu.ar.ecoride.Exeptions.ExepcionVehiculoNoEncontrado;
import unlar.edu.ar.ecoride.dto.RespuestaDesbloqueo;
import unlar.edu.ar.ecoride.dto.RespuestaFinalizacion;
import unlar.edu.ar.ecoride.model.*;
import unlar.edu.ar.ecoride.tarifa.CriterioEstandar;
import unlar.edu.ar.ecoride.tarifa.CriterioTarifa;
import org.springframework.stereotype.Service;

/**
 * Lógica de negocio de EcoRide PRO.
 *
 * Integra:
 *  - A.1: transiciones de estado del vehículo (iniciar/finalizar viaje).
 *  - A.2: cálculo de tarifa con criterio intercambiable en runtime (Strategy).
 *  - Flujo original: validación de batería, medios de pago y cobro.
 */
@Service
public class ServiceEcoride {

    private static final int BATERIA_MINIMA = 15;

    private final EstacionAnclaje estacionAnclaje;

    /**
     * A.2: criterio de tarifa ACTIVO. Se puede cambiar en caliente con
     * cambiarCriterioTarifa() sin reiniciar la aplicación. Arranca en Estándar.
     */
    private CriterioTarifa criterioTarifaActivo = new CriterioEstandar();

    public ServiceEcoride(EstacionAnclaje estacionAnclaje) {
        this.estacionAnclaje = estacionAnclaje;
    }

    public void cambiarCriterioTarifa(CriterioTarifa nuevoCriterio) {
        this.criterioTarifaActivo = nuevoCriterio;
    }

    public CriterioTarifa getCriterioTarifaActivo() {
        return criterioTarifaActivo;
    }

    /** Localiza el vehículo por patente (O(1)) y valida la batería. */
    public Vehiculo localizarYValidar(String patente) {
        Vehiculo vehiculo = estacionAnclaje.buscarPorPatente(patente);
        if (vehiculo == null) {
            throw new ExepcionVehiculoNoEncontrado(
                    "Vehículo No Encontrado: no existe un vehículo con patente " + patente
                            + " en la estación " + estacionAnclaje.getNombreEstacion() + ".");
        }
        if (vehiculo.getBateria() < BATERIA_MINIMA) {
            throw new ExcepcionBateriaInsuficiente(
                    "Batería Insuficiente: el vehículo " + patente + " tiene "
                            + vehiculo.getBateria() + "% de batería (mínimo " + BATERIA_MINIMA + "%).");
        }
        return vehiculo;
    }

    public double calcularImporteFinal(Usuario usuario, double monto) {
        return usuario.calcularTotal(monto);
    }

    /** Componente de creación de pagos desacoplado. */
    public ProcesadorDePago obtenerMedioDePago(String metodoPago) {
        if (metodoPago.equalsIgnoreCase("TARJETA")) {
            return new TarjetaDeCredito("1234567890123456", "Titular EcoRide", "12/28", "123");
        }
        if (metodoPago.equalsIgnoreCase("BILLETERA")) {
            return new BilleteraVirtual(1000.0);
        }
        throw new IllegalArgumentException("Medio de pago no válido: " + metodoPago
                + ". Valores aceptados: TARJETA, BILLETERA.");
    }

    public void efectuarCobro(ProcesadorDePago procesador, double monto) {
        boolean exito = procesador.cobrar(monto);
        if (!exito) {
            throw new ExcepcionCobroFallido(
                    "No se pudo efectuar el cobro de $" + String.format("%.2f", monto)
                            + " con el medio de pago seleccionado.");
        }
    }

    /**
     * Desbloqueo: localiza, valida, inicia el viaje (transición de estado A.1)
     * y deja el vehículo EN_VIAJE. Aún no se cobra (el cobro va al finalizar).
     */
    public RespuestaDesbloqueo procesarDesbloqueo(String patente, Usuario usuario) {
        Vehiculo vehiculo = localizarYValidar(patente);
        vehiculo.iniciarViaje(); // EN_ESPERA -> EN_VIAJE (lanza si no se puede)

        return new RespuestaDesbloqueo(
                "Desbloqueo exitoso. Vehículo en viaje.",
                vehiculo.getPatente(),
                usuario.getNombreCompleto(),
                vehiculo.getEstado().getDescripcion());
    }

    /**
     * Finalización: calcula la tarifa según el criterio ACTIVO (A.2), aplica el
     * descuento del usuario, cobra, y devuelve el vehículo a EN_ESPERA.
     */
    public RespuestaFinalizacion procesarFinalizacion(String patente, Usuario usuario,
                                                      int minutos, String metodoPago) {
        Vehiculo vehiculo = estacionAnclaje.buscarPorPatente(patente);
        if (vehiculo == null) {
            throw new ExepcionVehiculoNoEncontrado(
                    "Vehículo No Encontrado: no existe un vehículo con patente " + patente + ".");
        }

        // A.2: el costo depende del criterio activo (Estándar / Hora Pico / Climático).
        double costoViaje = criterioTarifaActivo.calcular(minutos, vehiculo.getTarifaBase());
        double importeFinal = calcularImporteFinal(usuario, costoViaje);

        ProcesadorDePago procesador = obtenerMedioDePago(metodoPago);
        efectuarCobro(procesador, importeFinal);

        vehiculo.finalizarViaje(); // EN_VIAJE -> EN_ESPERA (lanza si no se puede)

        return new RespuestaFinalizacion(
                "Viaje finalizado y cobrado.",
                vehiculo.getPatente(),
                importeFinal,
                minutos,
                criterioTarifaActivo.getNombre(),
                vehiculo.getEstado().getDescripcion());
    }
}

package unlar.edu.ar.ecoride.service;

import unlar.edu.ar.ecoride.Exeptions.ExcepcionBateriaInsuficiente;
import unlar.edu.ar.ecoride.Exeptions.ExcepcionCobroFallido;
import unlar.edu.ar.ecoride.Exeptions.ExepcionVehiculoNoEncontrado;
import unlar.edu.ar.ecoride.dto.RespuestaDesbloqueo;
import unlar.edu.ar.ecoride.model.*;
import org.springframework.stereotype.Service;

@Service
public class ServiceEcoride {

    private static final int BATERIA_MINIMA = 15;

    private final EstacionAnclaje estacionAnclaje;

    public ServiceEcoride(EstacionAnclaje estacionAnclaje) {
        this.estacionAnclaje = estacionAnclaje;
    }

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

    public double calcularImporteFinal(Usuario usuario, double tarifaBase) {
        return usuario.calcularTotal(tarifaBase);
    }


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

    public RespuestaDesbloqueo procesarDesbloqueo(String patente, Usuario usuario, String metodoPago) {
        Vehiculo vehiculo = localizarYValidar(patente);
        double importeFinal = calcularImporteFinal(usuario, vehiculo.getTarifaBase());
        ProcesadorDePago procesador = obtenerMedioDePago(metodoPago);
        efectuarCobro(procesador, importeFinal);

        return new RespuestaDesbloqueo(
                "Desbloqueo exitoso.",
                vehiculo.getPatente(),
                usuario.getNombreCompleto(),
                importeFinal,
                metodoPago.toUpperCase());
    }
}

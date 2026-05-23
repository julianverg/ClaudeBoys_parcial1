package unlar.edu.ar.ecoride.service;

import unlar.edu.ar.ecoride.Exeptions.ExepcionVehiculoNoEncontrado;
import unlar.edu.ar.ecoride.Exeptions.ExcepcionBateriaInsuficiente;
import unlar.edu.ar.ecoride.model.*;
import org.springframework.stereotype.Service;

@Service
public class ServiceEcoride {

    private EstacionAnclaje estacionAnclaje;

    public ServiceEcoride(EstacionAnclaje estacionAnclaje) {
        this.estacionAnclaje = estacionAnclaje;
    }

    public String desbloquearVehiculo(String patente, Usuario usuario) { //punto 1 y 2
        Vehiculo vehiculo = estacionAnclaje.buscarPorPatente(patente);
        if (vehiculo == null) {
            throw new ExepcionVehiculoNoEncontrado("Vehículo con patente " + patente + " no encontrado.");
        }
        if (vehiculo.getBateria() < 15) {
            throw new ExcepcionBateriaInsuficiente("El vehículo con patente " + patente + " tiene batería insuficiente.");
        }
        return "Vehículo con patente " + patente + " desbloqueado para el usuario " + usuario.getNombreCompleto() + ".";
    }

    public double calcularImporteFinal(Usuario usuario, double tarifaBase) { //punto 3
        return usuario.calcularTotal(tarifaBase);
    }

    public ProcesadorDePago obtenerMedioDePago(String metodoPago) {
        if (metodoPago.equalsIgnoreCase("TARJETA")) {
            return new TarjetaDeCredito("1234567890123456", "John Doe", "12/25", "123");
        }

        if (metodoPago.equalsIgnoreCase("BILLETERA")) {
            return new BilleteraVirtual(10000.0); // Saldo inicial de $100
        }

        throw new IllegalArgumentException("Medio de pago no válido: " + metodoPago);
    }

    public boolean efectuarCobro(ProcesadorDePago procesador, double monto) {
        procesador.cobrar(monto);
        return true;
    }

    public String procesarDesbloqueo(String patente, Usuario usuario, double tarifaBase, String metodoPago) {

        desbloquearVehiculo(patente, usuario);

        double importeFinal = calcularImporteFinal(usuario, tarifaBase);

        ProcesadorDePago procesador = obtenerMedioDePago(metodoPago);

        boolean cobroExitoso = efectuarCobro(procesador, importeFinal);

        if (cobroExitoso) {
            return "Desbloqueo exitoso. Vehículo: " + patente + ". Monto cobrado: $" + importeFinal;
        }

        return "No se pudo realizar el cobro. El vehículo no fue desbloqueado.";
    }
    
}

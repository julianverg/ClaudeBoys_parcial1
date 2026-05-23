package unlar.edu.ar.ecoride.model;

import java.util.List;

public class EstacionAnclaje{

    private String nombreEstacion;
    private List<Vehiculo> vehiculosDisponibles;

    public EstacionAnclaje(String nombreEstacion, List<Vehiculo> vehiculosDisponibles) {
        this.nombreEstacion = nombreEstacion;
        this.vehiculosDisponibles = vehiculosDisponibles;
    }

    public String getNombreEstacion() {
        return nombreEstacion;
    }

    public List<Vehiculo> getVehiculosDisponibles() {
        return vehiculosDisponibles;
    }

    public Vehiculo buscarPorPatente(String patente) {
        for (Vehiculo vehiculo : vehiculosDisponibles) {
            if (vehiculo.getPatente().equals(patente)) {
                return vehiculo;
            }
        }
        return null; // No se encontró el vehículo
    }
}

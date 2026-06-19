package unlar.edu.ar.ecoride.model;

import unlar.edu.ar.ecoride.Exeptions.ExcepcionEstadoInvalido;

/**
 * Clase base de los vehículos.
 *
 * B.3 (criterio natural): implementa Comparable<Vehiculo> con orden natural por
 * batería ASCENDENTE (los de menos energía primero, para cargarlos antes). Esta
 * es la "prioridad operativa" intrínseca del vehículo.
 *
 * A.1: mantiene su estado (EstadoVehiculo) y delega en él las transiciones del
 * ciclo de vida.
 */
public abstract class Vehiculo implements Comparable<Vehiculo> {

    private String patente;
    private int bateria;
    private double tarifaBase;
    private EstadoVehiculo estado;

    public Vehiculo(String patente, int bateria, double tarifaBase) {
        this.patente = patente;
        this.bateria = bateria;
        this.tarifaBase = tarifaBase;
        this.estado = EstadoVehiculo.EN_ESPERA;
    }

    public String getPatente() {
        return patente;
    }

    public int getBateria() {
        return bateria;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public EstadoVehiculo getEstado() {
        return estado;
    }

    /**
     * Inicia el viaje delegando la regla en el estado actual (patrón State).
     * Si el estado no lo permite, lanza excepción en vez de hacer if anidados.
     */
    public void iniciarViaje() {
        if (!estado.puedeIniciarViaje()) {
            throw new ExcepcionEstadoInvalido(
                    "No se puede iniciar viaje con el vehículo " + patente
                            + ": estado actual " + estado.getDescripcion() + ".");
        }
        this.estado = EstadoVehiculo.EN_VIAJE;
    }

    public void finalizarViaje() {
        if (!estado.puedeFinalizarViaje()) {
            throw new ExcepcionEstadoInvalido(
                    "No se puede finalizar viaje con el vehículo " + patente
                            + ": estado actual " + estado.getDescripcion() + ".");
        }
        this.estado = EstadoVehiculo.EN_ESPERA;
    }

    public void enviarAReparacion() {
        this.estado = EstadoVehiculo.EN_REPARACION;
    }

    /**
     * Orden natural: por batería de MENOR a MAYOR.
     * Usamos Integer.compare (no resta) para no arriesgar overflow.
     */
    @Override
    public int compareTo(Vehiculo otro) {
        return Integer.compare(this.bateria, otro.bateria);
    }
}

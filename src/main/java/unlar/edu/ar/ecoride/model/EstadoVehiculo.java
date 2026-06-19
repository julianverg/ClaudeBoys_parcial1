package unlar.edu.ar.ecoride.model;

/**
 * Patrón State implementado con enum (A.1).
 *
 * Cada fase del ciclo de vida del vehículo sabe POR SÍ MISMA qué acciones
 * permite. Así evitamos if/else anidados o switch gigantes en el modelo: para
 * agregar una fase nueva basta con sumar una constante y definir sus reglas.
 */
public enum EstadoVehiculo {

    /** El vehículo descansa en la estación, listo para ser retirado. */
    EN_ESPERA("En Espera") {
        @Override
        public boolean puedeIniciarViaje() {
            return true;
        }
    },

    /** El usuario lo está conduciendo. No puede alquilarse ni ir a mantenimiento. */
    EN_VIAJE("En Viaje") {
        @Override
        public boolean puedeFinalizarViaje() {
            return true;
        }
    },

    /** Tiene fallas. No puede iniciarse un viaje bajo ninguna circunstancia. */
    EN_REPARACION("En Reparación");

    private final String descripcion;

    EstadoVehiculo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    /** Por defecto ninguna acción está permitida; cada estado habilita lo suyo. */
    public boolean puedeIniciarViaje() {
        return false;
    }

    public boolean puedeFinalizarViaje() {
        return false;
    }
}

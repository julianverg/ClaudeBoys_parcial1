package unlar.edu.ar.ecoride.Exeptions;

/**
 * Se lanza cuando se intenta una transición de estado no permitida
 * (por ejemplo, iniciar viaje con un vehículo En Reparación).
 */
public class ExcepcionEstadoInvalido extends RuntimeException {

    public ExcepcionEstadoInvalido(String mensaje) {
        super(mensaje);
    }
}

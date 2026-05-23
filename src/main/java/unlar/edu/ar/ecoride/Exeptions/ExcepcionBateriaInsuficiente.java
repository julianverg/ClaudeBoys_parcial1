package unlar.edu.ar.ecoride.Exeptions;

public class ExcepcionBateriaInsuficiente extends RuntimeException {

    public ExcepcionBateriaInsuficiente(String mensaje) {
        super(mensaje);
    }

}

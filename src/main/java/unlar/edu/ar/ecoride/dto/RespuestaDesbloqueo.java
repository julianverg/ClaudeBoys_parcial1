package unlar.edu.ar.ecoride.dto;

/**
 * Respuesta exitosa del desbloqueo. Se serializa a JSON automáticamente.
 */
public class RespuestaDesbloqueo {

    private String mensaje;
    private String patente;
    private String usuario;
    private double montoCobrado;
    private String medioPago;

    public RespuestaDesbloqueo(String mensaje, String patente, String usuario, double montoCobrado, String medioPago) {
        this.mensaje = mensaje;
        this.patente = patente;
        this.usuario = usuario;
        this.montoCobrado = montoCobrado;
        this.medioPago = medioPago;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getPatente() {
        return patente;
    }

    public String getUsuario() {
        return usuario;
    }

    public double getMontoCobrado() {
        return montoCobrado;
    }

    public String getMedioPago() {
        return medioPago;
    }
}

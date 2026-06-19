package unlar.edu.ar.ecoride.dto;

/**
 * Respuesta del desbloqueo (DTO). Expone solo datos relevantes para el cliente,
 * sin filtrar entidades internas (apartado C).
 */
public class RespuestaDesbloqueo {

    private String mensaje;
    private String patente;
    private String usuario;
    private String estadoVehiculo;

    public RespuestaDesbloqueo(String mensaje, String patente, String usuario, String estadoVehiculo) {
        this.mensaje = mensaje;
        this.patente = patente;
        this.usuario = usuario;
        this.estadoVehiculo = estadoVehiculo;
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

    public String getEstadoVehiculo() {
        return estadoVehiculo;
    }
}

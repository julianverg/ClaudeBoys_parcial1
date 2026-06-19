package unlar.edu.ar.ecoride.dto;

/**
 * Cuerpo JSON de la petición de desbloqueo (apartado C de la consigna):
 * { "idUsuario": "...", "patente": "...", "metodoPago": "TARJETA" }
 */
public class PeticionDesbloqueo {

    private String idUsuario;
    private String patente;
    private String metodoPago;

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}

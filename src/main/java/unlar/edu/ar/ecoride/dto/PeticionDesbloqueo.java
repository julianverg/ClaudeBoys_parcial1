package unlar.edu.ar.ecoride.dto;

/**
 * Cuerpo JSON de la petición de desbloqueo:
 * { "idUsuario": "...", "patente": "..." }
 */
public class PeticionDesbloqueo {

    private String idUsuario;
    private String patente;

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
}

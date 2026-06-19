package unlar.edu.ar.ecoride.dto;

/**
 * Cuerpo JSON para finalizar un viaje:
 * { "idUsuario": "...", "patente": "...", "minutos": 30, "metodoPago": "TARJETA" }
 */
public class PeticionFinalizacion {

    private String idUsuario;
    private String patente;
    private int minutos;
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

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}

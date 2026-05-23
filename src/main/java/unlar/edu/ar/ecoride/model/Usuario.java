package unlar.edu.ar.ecoride.model;

public abstract class Usuario{

    private String id;
    private String nombreCompleto;

    public Usuario(String id, String nombreCompleto) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
    }

    public String getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public double calcularTotal(double tarifa) {
        return tarifa;
    }
}

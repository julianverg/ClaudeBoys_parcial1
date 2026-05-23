package unlar.edu.ar.ecoride.model;

public class UsuarioPremium  extends Usuario {

    private final double descuento = 0.1; // Descuento del 10%

    public UsuarioPremium(String id, String nombreCompleto) {
        super(id, nombreCompleto);
    }

    @Override
    public double calcularTotal(double tarifa) {
        return tarifa * (1 - descuento); // Descuento del 10% para usuarios premium
    }

}

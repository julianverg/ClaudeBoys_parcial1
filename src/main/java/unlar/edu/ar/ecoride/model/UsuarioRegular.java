package unlar.edu.ar.ecoride.model;

public class UsuarioRegular extends Usuario {

    public UsuarioRegular(String id, String nombreCompleto) {
        super(id, nombreCompleto);
    }

    @Override
    public double calcularTotal(double tarifa) {
        // El usuario regular paga la tarifa completa, sin descuento.
        return tarifa;
    }
}

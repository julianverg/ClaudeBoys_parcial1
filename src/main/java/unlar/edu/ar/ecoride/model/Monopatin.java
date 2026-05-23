package unlar.edu.ar.ecoride.model;

public class Monopatin extends Vehiculo {

    private boolean amortiguacionReforzada;

    public Monopatin(String patente, int bateria, double tarifaBase, boolean amortiguacionReforzada) {
        super(patente, bateria, tarifaBase);
        this.amortiguacionReforzada = amortiguacionReforzada;
    }


    public boolean isAmortiguacionReforzada() {
        return amortiguacionReforzada;
    }
}

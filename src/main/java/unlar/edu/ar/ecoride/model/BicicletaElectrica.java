package unlar.edu.ar.ecoride.model;

public class BicicletaElectrica extends Vehiculo {

    private double capacidadCanasto;

    public BicicletaElectrica(String patente, int bateria, double tarifaBase, double capacidadCanasto) {
        super(patente, bateria, tarifaBase);
        this.capacidadCanasto = capacidadCanasto;
    }

    public double getCapacidadCanasto() {
        return capacidadCanasto;
    }

}

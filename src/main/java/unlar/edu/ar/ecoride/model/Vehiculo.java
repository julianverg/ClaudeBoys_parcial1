package unlar.edu.ar.ecoride.model;



public abstract class Vehiculo {

    private String patente;
    private int bateria;
    private double tarifaBase;

    public Vehiculo(String patente, int bateria, double tarifaBase) {
        this.patente = patente;
        this.bateria = bateria;
        this.tarifaBase = tarifaBase;
    }

    public String getPatente() {
        return patente;
    }   

    public int getBateria() {
        return bateria;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

}

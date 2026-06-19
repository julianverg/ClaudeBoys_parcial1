package unlar.edu.ar.ecoride.dto;

import unlar.edu.ar.ecoride.model.Vehiculo;

/**
 * DTO de vehículo para los reportes ordenados. Expone solo lo relevante
 * (patente, batería, tarifa, estado en formato amigable), sin filtrar la
 * entidad de dominio completa.
 */
public class VehiculoDto {

    private String patente;
    private int bateria;
    private double tarifaBase;
    private String estado;

    public VehiculoDto(String patente, int bateria, double tarifaBase, String estado) {
        this.patente = patente;
        this.bateria = bateria;
        this.tarifaBase = tarifaBase;
        this.estado = estado;
    }

    /** Convierte una entidad Vehiculo en su DTO. */
    public static VehiculoDto desde(Vehiculo v) {
        return new VehiculoDto(v.getPatente(), v.getBateria(), v.getTarifaBase(),
                v.getEstado().getDescripcion());
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

    public String getEstado() {
        return estado;
    }
}

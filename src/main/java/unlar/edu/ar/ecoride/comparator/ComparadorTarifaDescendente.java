package unlar.edu.ar.ecoride.comparator;

import unlar.edu.ar.ecoride.model.Vehiculo;

import java.util.Comparator;

/**
 * B.3 (criterio alternativo): comparator EXTERNO al vehículo que ordena por
 * tarifa base de MAYOR a MENOR (los más caros primero).
 *
 * Es externo a propósito: el criterio es comercial/secundario y no debe
 * interferir con el orden natural por batería (que vive dentro de Vehiculo).
 *
 * Escrito como clase que implementa Comparator (sin Comparator.comparing ni
 * lambdas) porque la consigna prohíbe la API funcional / Stream.
 */
public class ComparadorTarifaDescendente implements Comparator<Vehiculo> {

    @Override
    public int compare(Vehiculo v1, Vehiculo v2) {
        // Invertimos los argumentos para obtener orden descendente.
        // Double.compare evita problemas con decimales y NaN (no usamos resta).
        return Double.compare(v2.getTarifaBase(), v1.getTarifaBase());
    }
}

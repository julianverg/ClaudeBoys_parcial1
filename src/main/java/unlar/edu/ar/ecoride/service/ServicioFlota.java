package unlar.edu.ar.ecoride.service;

import unlar.edu.ar.ecoride.comparator.ComparadorTarifaDescendente;
import unlar.edu.ar.ecoride.model.EstacionAnclaje;
import unlar.edu.ar.ecoride.model.Vehiculo;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * B.3 (estrategias de ordenamiento de flota).
 *
 * - Orden natural (Comparable, definido dentro de Vehiculo): batería de menor a
 *   mayor, para priorizar la carga.
 * - Orden alternativo (Comparator externo): tarifa base de mayor a menor.
 *
 * El criterio alternativo NO interfiere con el natural: el natural vive en
 * compareTo() del vehículo y el alternativo en una clase Comparator aparte.
 */
@Service
public class ServicioFlota {

    private final EstacionAnclaje estacionAnclaje;

    public ServicioFlota(EstacionAnclaje estacionAnclaje) {
        this.estacionAnclaje = estacionAnclaje;
    }

    /** Ordena por prioridad de carga (batería ascendente) usando el orden natural. */
    public List<Vehiculo> ordenarPorPrioridadCarga() {
        List<Vehiculo> vehiculos = estacionAnclaje.obtenerTodos();
        Collections.sort(vehiculos); // usa compareTo() de Vehiculo
        return vehiculos;
    }

    /** Ordena por tarifa base descendente usando el comparator externo. */
    public List<Vehiculo> ordenarPorTarifaDescendente() {
        List<Vehiculo> vehiculos = estacionAnclaje.obtenerTodos();
        Collections.sort(vehiculos, new ComparadorTarifaDescendente());
        return vehiculos;
    }
}

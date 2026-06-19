package unlar.edu.ar.ecoride.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * B.1 (acceso instantáneo): la estación guarda los vehículos en un
 * HashMap<patente, Vehiculo>. Buscar por patente pasa a ser O(1) en promedio,
 * en lugar del recorrido lineal O(n) de la versión anterior. El tiempo de
 * respuesta es constante sin importar si hay 10 o 100.000 vehículos.
 */
public class EstacionAnclaje {

    private String nombreEstacion;
    private final Map<String, Vehiculo> vehiculosPorPatente;

    public EstacionAnclaje(String nombreEstacion, List<Vehiculo> vehiculos) {
        this.nombreEstacion = nombreEstacion;
        this.vehiculosPorPatente = new HashMap<>();
        // Carga inicial con bucle tradicional (sin streams).
        for (Vehiculo v : vehiculos) {
            this.vehiculosPorPatente.put(v.getPatente(), v);
        }
    }

    public String getNombreEstacion() {
        return nombreEstacion;
    }

    /**
     * Búsqueda instantánea por patente: una sola consulta al HashMap, O(1).
     */
    public Vehiculo buscarPorPatente(String patente) {
        return vehiculosPorPatente.get(patente);
    }

    /**
     * Devuelve una copia de los vehículos como lista (para los ordenamientos).
     */
    public List<Vehiculo> obtenerTodos() {
        return new ArrayList<>(vehiculosPorPatente.values());
    }
}

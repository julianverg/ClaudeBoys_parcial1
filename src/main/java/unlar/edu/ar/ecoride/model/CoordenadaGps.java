package unlar.edu.ar.ecoride.model;

import java.util.Objects;

/**
 * Reporte de posición GPS. Implementa equals() y hashCode() para que el
 * HashSet pueda detectar duplicados (dos coordenadas iguales son "la misma").
 */
public class CoordenadaGps {

    private final double latitud;
    private final double longitud;

    public CoordenadaGps(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CoordenadaGps that = (CoordenadaGps) o;
        return Double.compare(latitud, that.latitud) == 0
                && Double.compare(longitud, that.longitud) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitud, longitud);
    }

    @Override
    public String toString() {
        return "(" + latitud + ", " + longitud + ")";
    }
}

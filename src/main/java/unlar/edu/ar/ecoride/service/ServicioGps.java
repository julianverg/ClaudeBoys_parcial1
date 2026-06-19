package unlar.edu.ar.ecoride.service;

import unlar.edu.ar.ecoride.model.CoordenadaGps;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * B.2 (deduplicación de alertas GPS): recibe una lista desordenada con miles de
 * coordenadas (con duplicados) y devuelve una colección de elementos únicos.
 *
 * Resolución óptima en UNA SOLA PASADA usando un HashSet: por cada reporte,
 * intentamos agregarlo al set; si add() devuelve false, ya existía y lo
 * descartamos. Esto es O(n).
 *
 * Evitamos a propósito el bucle anidado (comparar cada reporte contra todos los
 * demás), que sería O(n²) y bloquearía la CPU del servidor.
 */
@Service
public class ServicioGps {

    public List<CoordenadaGps> deduplicar(List<CoordenadaGps> reportes) {
        Set<CoordenadaGps> vistos = new HashSet<>();
        List<CoordenadaGps> unicos = new ArrayList<>();

        // UNA sola pasada: O(n). El HashSet resuelve la pertenencia en O(1).
        for (CoordenadaGps reporte : reportes) {
            if (vistos.add(reporte)) {
                // add() devuelve true solo si NO estaba: es nuevo.
                unicos.add(reporte);
            }
        }
        return unicos;
    }
}

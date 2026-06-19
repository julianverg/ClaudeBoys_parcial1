package unlar.edu.ar.ecoride.controller;

import unlar.edu.ar.ecoride.model.CoordenadaGps;
import unlar.edu.ar.ecoride.service.ServiceEcoride;
import unlar.edu.ar.ecoride.service.ServicioGps;
import unlar.edu.ar.ecoride.tarifa.CriterioEstandar;
import unlar.edu.ar.ecoride.tarifa.CriterioHoraPico;
import unlar.edu.ar.ecoride.tarifa.CriterioTarifa;
import unlar.edu.ar.ecoride.tarifa.CriterioTemporalClimatico;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Endpoints de operación: cambio de criterio de tarifa en runtime (A.2) y
 * deduplicación de reportes GPS (B.2).
 */
@RestController
@RequestMapping("/api/operaciones")
public class ControllerOperaciones {

    private final ServiceEcoride servicioEcoride;
    private final ServicioGps servicioGps;

    public ControllerOperaciones(ServiceEcoride servicioEcoride, ServicioGps servicioGps) {
        this.servicioEcoride = servicioEcoride;
        this.servicioGps = servicioGps;
    }

    /**
     * A.2: cambia el criterio de facturación activo en caliente.
     * Ejemplo: PUT /api/operaciones/criterio-tarifa?tipo=HORA_PICO
     */
    @PutMapping("/criterio-tarifa")
    public Map<String, String> cambiarCriterio(@RequestParam String tipo) {
        CriterioTarifa criterio = resolverCriterio(tipo);
        servicioEcoride.cambiarCriterioTarifa(criterio);

        Map<String, String> resp = new LinkedHashMap<>();
        resp.put("mensaje", "Criterio de tarifa actualizado.");
        resp.put("criterioActivo", criterio.getNombre());
        return resp;
    }

    private CriterioTarifa resolverCriterio(String tipo) {
        if (tipo.equalsIgnoreCase("ESTANDAR")) {
            return new CriterioEstandar();
        }
        if (tipo.equalsIgnoreCase("HORA_PICO")) {
            return new CriterioHoraPico();
        }
        if (tipo.equalsIgnoreCase("CLIMATICO")) {
            return new CriterioTemporalClimatico();
        }
        throw new IllegalArgumentException("Criterio de tarifa no válido: " + tipo
                + ". Valores aceptados: ESTANDAR, HORA_PICO, CLIMATICO.");
    }

    /**
     * B.2: recibe una lista de coordenadas (con duplicados) y devuelve las únicas.
     */
    @PostMapping("/deduplicar-gps")
    public Map<String, Object> deduplicarGps(@RequestBody List<CoordenadaGps> reportes) {
        List<CoordenadaGps> unicos = servicioGps.deduplicar(reportes);

        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("recibidos", reportes.size());
        resp.put("unicos", unicos.size());
        resp.put("coordenadas", unicos);
        return resp;
    }
}

package unlar.edu.ar.ecoride.controller;

import unlar.edu.ar.ecoride.dto.VehiculoDto;
import unlar.edu.ar.ecoride.model.Vehiculo;
import unlar.edu.ar.ecoride.service.ServicioFlota;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Apartado C - Desafío de Filtros y Orden: dos endpoints que exponen los
 * reportes ordenados de B.3, devolviendo DTOs (no entidades internas).
 */
@RestController
@RequestMapping("/api/vehiculos")
public class ControllerVehiculos {

    private final ServicioFlota servicioFlota;

    public ControllerVehiculos(ServicioFlota servicioFlota) {
        this.servicioFlota = servicioFlota;
    }

    /** Orden natural: batería de menor a mayor (prioridad de carga). */
    @GetMapping("/prioridad-carga")
    public List<VehiculoDto> prioridadCarga() {
        return aDtos(servicioFlota.ordenarPorPrioridadCarga());
    }

    /** Orden alternativo: tarifa base de mayor a menor. */
    @GetMapping("/tarifa-descendente")
    public List<VehiculoDto> tarifaDescendente() {
        return aDtos(servicioFlota.ordenarPorTarifaDescendente());
    }

    /** Conversión a DTO con bucle tradicional (sin streams). */
    private List<VehiculoDto> aDtos(List<Vehiculo> vehiculos) {
        List<VehiculoDto> dtos = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            dtos.add(VehiculoDto.desde(v));
        }
        return dtos;
    }
}

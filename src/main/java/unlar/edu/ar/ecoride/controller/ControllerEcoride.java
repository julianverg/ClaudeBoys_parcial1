package unlar.edu.ar.ecoride.controller;

import unlar.edu.ar.ecoride.dto.PeticionDesbloqueo;
import unlar.edu.ar.ecoride.dto.PeticionFinalizacion;
import unlar.edu.ar.ecoride.dto.RespuestaDesbloqueo;
import unlar.edu.ar.ecoride.dto.RespuestaFinalizacion;
import unlar.edu.ar.ecoride.model.Usuario;
import unlar.edu.ar.ecoride.model.UsuarioPremium;
import unlar.edu.ar.ecoride.model.UsuarioRegular;
import unlar.edu.ar.ecoride.service.ServiceEcoride;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Apartado C: exposición profesional de datos.
 *
 * /api/alquileres/desbloquear  -> inicia el viaje (estado EN_VIAJE).
 * /api/alquileres/finalizar    -> calcula tarifa según criterio activo, cobra y
 *                                 devuelve el vehículo a EN_ESPERA.
 *
 * Ambos devuelven DTOs, no entidades internas.
 */
@RestController
public class ControllerEcoride {

    private final ServiceEcoride servicioEcoride;

    public ControllerEcoride(ServiceEcoride servicioEcoride) {
        this.servicioEcoride = servicioEcoride;
    }

    @GetMapping("/api/alquileres/desbloquear")
    public RespuestaDesbloqueo desbloquear(@RequestBody PeticionDesbloqueo peticion) {
        Usuario usuario = construirUsuario(peticion.getIdUsuario());
        return servicioEcoride.procesarDesbloqueo(peticion.getPatente(), usuario);
    }

    @GetMapping("/api/alquileres/finalizar")
    public RespuestaFinalizacion finalizar(@RequestBody PeticionFinalizacion peticion) {
        Usuario usuario = construirUsuario(peticion.getIdUsuario());
        return servicioEcoride.procesarFinalizacion(
                peticion.getPatente(),
                usuario,
                peticion.getMinutos(),
                peticion.getMetodoPago());
    }

    private Usuario construirUsuario(String idUsuario) {
        if (idUsuario != null && idUsuario.toUpperCase().startsWith("PREM")) {
            return new UsuarioPremium(idUsuario, "Usuario Premium " + idUsuario);
        }
        return new UsuarioRegular(idUsuario, "Usuario Regular " + idUsuario);
    }
}

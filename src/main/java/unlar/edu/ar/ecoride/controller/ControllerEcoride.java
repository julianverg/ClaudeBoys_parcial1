package unlar.edu.ar.ecoride.controller;

import unlar.edu.ar.ecoride.Exeptions.ExepcionVehiculoNoEncontrado;
import unlar.edu.ar.ecoride.Exeptions.ExcepcionBateriaInsuficiente;
import unlar.edu.ar.ecoride.model.*;
import unlar.edu.ar.ecoride.service.ServiceEcoride;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class ControllerEcoride {

    private ServiceEcoride servicioEcoride;

    public ControllerEcoride(ServiceEcoride servicioEcoride) {
        this.servicioEcoride = servicioEcoride;
    }

    @GetMapping("/api/alquileres/desbloquear")
    public String desbloquearVehiculo(
            @RequestParam String patente,
            @RequestParam String usuarioId,
            @RequestParam String tipoUsuario,
            @RequestParam String metodoPago,
            @RequestParam double tarifaBase
    ) {
        try {
            Usuario usuario;

            if (tipoUsuario.equalsIgnoreCase("PREMIUM")) {
                usuario = new UsuarioPremium(usuarioId, "Usuario Premium");
            } else {
                usuario = new UsuarioRegular(usuarioId, "Usuario Regular");
            }

            return servicioEcoride.procesarDesbloqueo(patente, usuario, tarifaBase, metodoPago);

        } catch (ExepcionVehiculoNoEncontrado | ExcepcionBateriaInsuficiente e) {
            return e.getMessage();

        } catch (IllegalArgumentException e) {
            return e.getMessage();

        } catch (Exception e) {
            return "Error inesperado al procesar el desbloqueo.";
        }
    }
}

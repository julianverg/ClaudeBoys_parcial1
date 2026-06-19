package unlar.edu.ar.ecoride.Exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Intercepta las excepciones de negocio y las traduce a respuestas HTTP 400
 * con un cuerpo JSON claro, como pide el apartado B de la consigna.
 */
@RestControllerAdvice
public class ManejadorGlobalErrores {

    private ResponseEntity<Map<String, Object>> error(String tipo, String mensaje) {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("estado", "ERROR");
        cuerpo.put("alerta", tipo);
        cuerpo.put("mensaje", mensaje);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cuerpo);
    }

    @ExceptionHandler(ExepcionVehiculoNoEncontrado.class)
    public ResponseEntity<Map<String, Object>> vehiculoNoEncontrado(ExepcionVehiculoNoEncontrado e) {
        return error("Vehículo No Encontrado", e.getMessage());
    }

    @ExceptionHandler(ExcepcionBateriaInsuficiente.class)
    public ResponseEntity<Map<String, Object>> bateriaInsuficiente(ExcepcionBateriaInsuficiente e) {
        return error("Batería Insuficiente", e.getMessage());
    }

    @ExceptionHandler(ExcepcionCobroFallido.class)
    public ResponseEntity<Map<String, Object>> cobroFallido(ExcepcionCobroFallido e) {
        return error("Cobro Fallido", e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> argumentoInvalido(IllegalArgumentException e) {
        return error("Solicitud Inválida", e.getMessage());
    }
}

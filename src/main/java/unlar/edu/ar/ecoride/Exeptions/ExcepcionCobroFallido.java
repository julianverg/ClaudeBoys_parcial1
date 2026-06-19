package unlar.edu.ar.ecoride.Exeptions;

/**
 * Se lanza cuando el medio de pago no puede concretar el cobro
 * (por ejemplo, saldo insuficiente en la billetera virtual).
 */
public class ExcepcionCobroFallido extends RuntimeException {

    public ExcepcionCobroFallido(String mensaje) {
        super(mensaje);
    }
}

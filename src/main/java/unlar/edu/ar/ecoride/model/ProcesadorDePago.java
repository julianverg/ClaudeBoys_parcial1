package unlar.edu.ar.ecoride.model;

/**
 * Abstracción del medio de pago (Tarjeta o Billetera).
 * cobrar() devuelve true si el cobro fue exitoso, false si no se pudo concretar.
 */
public interface ProcesadorDePago {

    boolean cobrar(double monto);
}

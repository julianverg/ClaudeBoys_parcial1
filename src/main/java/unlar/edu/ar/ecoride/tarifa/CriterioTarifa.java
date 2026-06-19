package unlar.edu.ar.ecoride.tarifa;

/**
 * Patrón Strategy (A.2): estrategia de cálculo de tarifa intercambiable.
 *
 * calcular() recibe los minutos del viaje y la tarifa base por minuto del
 * vehículo, y devuelve el costo final según el criterio activo.
 */
public interface CriterioTarifa {

    double calcular(int minutos, double tarifaBasePorMinuto);

    /** Nombre legible del criterio (para mostrar en respuestas). */
    String getNombre();
}

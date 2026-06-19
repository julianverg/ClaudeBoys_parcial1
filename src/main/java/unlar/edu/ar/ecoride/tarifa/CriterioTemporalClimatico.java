package unlar.edu.ar.ecoride.tarifa;

/** Criterio por Temporal Climático: recargo plano de $150 sobre el total. */
public class CriterioTemporalClimatico implements CriterioTarifa {

    private static final double RECARGO_FIJO = 150.0;

    @Override
    public double calcular(int minutos, double tarifaBasePorMinuto) {
        double base = minutos * tarifaBasePorMinuto;
        return base + RECARGO_FIJO;
    }

    @Override
    public String getNombre() {
        return "Temporal Climático (+$150)";
    }
}

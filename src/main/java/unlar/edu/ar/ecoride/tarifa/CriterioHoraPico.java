package unlar.edu.ar.ecoride.tarifa;

/** Criterio Hora Pico: recargo del 40% sobre el costo final calculado. */
public class CriterioHoraPico implements CriterioTarifa {

    private static final double RECARGO = 0.40;

    @Override
    public double calcular(int minutos, double tarifaBasePorMinuto) {
        double base = minutos * tarifaBasePorMinuto;
        return base * (1 + RECARGO);
    }

    @Override
    public String getNombre() {
        return "Hora Pico (+40%)";
    }
}

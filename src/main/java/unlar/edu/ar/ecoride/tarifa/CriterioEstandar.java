package unlar.edu.ar.ecoride.tarifa;

/** Criterio Estándar: minutos transcurridos x tarifa base. */
public class CriterioEstandar implements CriterioTarifa {

    @Override
    public double calcular(int minutos, double tarifaBasePorMinuto) {
        return minutos * tarifaBasePorMinuto;
    }

    @Override
    public String getNombre() {
        return "Estándar";
    }
}

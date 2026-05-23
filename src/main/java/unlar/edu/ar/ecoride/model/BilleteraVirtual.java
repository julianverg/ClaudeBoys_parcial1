package unlar.edu.ar.ecoride.model;

public class BilleteraVirtual implements ProcesadorDePago {

    private double saldo;

    public BilleteraVirtual(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    @Override
    public void cobrar(double monto) {
        if (saldo >= monto) {
            saldo -= monto;
            System.out.println("Cobro de $" + monto + " realizado con billetera virtual. Saldo restante: $" + saldo);
        } else {
            System.out.println("Saldo insuficiente en la billetera virtual.");
        }
    }

}

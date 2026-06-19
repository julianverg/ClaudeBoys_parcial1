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
    public boolean cobrar(double monto) {
        if (saldo >= monto) {
            saldo -= monto;
            System.out.printf("Cobro exitoso de $%.2f realizado con Billetera Virtual. Saldo restante: $%.2f%n", monto, saldo);
            return true;
        }
        System.out.printf("Saldo insuficiente en la Billetera Virtual (saldo $%.2f, monto $%.2f).%n", saldo, monto);
        return false;
    }
}

package unlar.edu.ar.ecoride.model;

public class TarjetaDeCredito implements ProcesadorDePago {
    
    private String numeroTarjeta;
    private String titular;
    private String fechaVencimiento;
    private String cvv;


    public TarjetaDeCredito(String numeroTarjeta, String titular, String fechaVencimiento, String cvv) {
        this.numeroTarjeta = numeroTarjeta;
        this.titular = titular;
        this.fechaVencimiento = fechaVencimiento;
        this.cvv = cvv;
    }

    

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }



    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }



    public String getTitular() {
        return titular;
    }



    public void setTitular(String titular) {
        this.titular = titular;
    }



    public String getFechaVencimiento() {
        return fechaVencimiento;
    }



    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }



    public String getCvv() {
        return cvv;
    }



    public void setCvv(String cvv) {
        this.cvv = cvv;
    }



    @Override
    public void cobrar(double monto) {
        // Lógica para procesar el pago con tarjeta de crédito
        System.out.println("Cobro de $" + monto + " realizado con tarjeta de crédito.");
    }

}

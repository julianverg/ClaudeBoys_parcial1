package unlar.edu.ar.ecoride.dto;

/**
 * Respuesta de la finalización del viaje (DTO). Muestra solo info amigable para
 * el cliente: costo final, tiempo, criterio aplicado y fase actual del vehículo.
 */
public class RespuestaFinalizacion {

    private String mensaje;
    private String patente;
    private double costoFinal;
    private int minutosTranscurridos;
    private String criterioAplicado;
    private String estadoVehiculo;

    public RespuestaFinalizacion(String mensaje, String patente, double costoFinal,
                                 int minutosTranscurridos, String criterioAplicado, String estadoVehiculo) {
        this.mensaje = mensaje;
        this.patente = patente;
        this.costoFinal = costoFinal;
        this.minutosTranscurridos = minutosTranscurridos;
        this.criterioAplicado = criterioAplicado;
        this.estadoVehiculo = estadoVehiculo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getPatente() {
        return patente;
    }

    public double getCostoFinal() {
        return costoFinal;
    }

    public int getMinutosTranscurridos() {
        return minutosTranscurridos;
    }

    public String getCriterioAplicado() {
        return criterioAplicado;
    }

    public String getEstadoVehiculo() {
        return estadoVehiculo;
    }
}

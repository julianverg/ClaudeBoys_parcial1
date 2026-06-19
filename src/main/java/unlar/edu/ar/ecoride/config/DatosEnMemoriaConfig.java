package unlar.edu.ar.ecoride.config;

import unlar.edu.ar.ecoride.model.BicicletaElectrica;
import unlar.edu.ar.ecoride.model.EstacionAnclaje;
import unlar.edu.ar.ecoride.model.Monopatin;
import unlar.edu.ar.ecoride.model.Vehiculo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestión de datos en memoria (sin base de datos), como exige la consigna.
 *
 * Crea el bean EstacionAnclaje con una lista de vehículos precargados para que
 * el ServiceEcoride pueda inyectarlo y operar.
 */
@Configuration
public class DatosEnMemoriaConfig {

    @Bean
    public EstacionAnclaje estacionAnclaje() {
        List<Vehiculo> vehiculos = new ArrayList<>();

        // patente, batería (0-100), tarifaBase, característica propia
        vehiculos.add(new Monopatin("MON-001", 80, 450.0, true));
        vehiculos.add(new Monopatin("MON-002", 10, 450.0, false));   // batería < 15% -> falla
        vehiculos.add(new BicicletaElectrica("BIC-001", 95, 600.0, 25.5));
        vehiculos.add(new BicicletaElectrica("BIC-002", 50, 700.0, 30.0));
        vehiculos.add(new Monopatin("MON-003", 14, 400.0, true));    // batería < 15% -> falla

        // Un vehículo en reparación: no debe poder iniciarse un viaje con él.
        Monopatin enTaller = new Monopatin("MON-004", 60, 500.0, false);
        enTaller.enviarAReparacion();
        vehiculos.add(enTaller);

        return new EstacionAnclaje("Estación Central", vehiculos);
    }
}

# EcoRide PRO — Sistema de Movilidad Urbana (Spring Boot)

Evolución del TP de EcoRide. Programación III, UNLaR 2026.
Spring Boot 3, Java 21. Datos en memoria, sin base de datos.

> Restricción del examen: lógica imperativa tradicional. Sin lambdas, sin Stream
> API, sin paralelismo. Todo con for/for-each/while y clases que implementan las
> interfaces de comparación.

## Cómo ejecutar

```bash
./mvnw spring-boot:run     # o: mvn spring-boot:run
```
Levanta en `http://localhost:8080`.

## Patrones y optimizaciones implementadas

| Consigna | Solución | Dónde |
|----------|----------|-------|
| A.1 Ciclo de vida del vehículo | Patrón State con enum (`EstadoVehiculo`) | `model/EstadoVehiculo`, `model/Vehiculo` |
| A.2 Tarifa adaptativa en runtime | Patrón Strategy (`CriterioTarifa` + 3 criterios) | `tarifa/` |
| B.1 Búsqueda instantánea | `HashMap<patente, Vehiculo>` → O(1) | `model/EstacionAnclaje` |
| B.2 Deduplicación GPS una pasada | `HashSet` → O(n), sin bucles anidados | `service/ServicioGps` |
| B.3 Orden natural | `Comparable` por batería asc (dentro de Vehiculo) | `model/Vehiculo#compareTo` |
| B.3 Orden alternativo | `Comparator` externo por tarifa desc | `comparator/ComparadorTarifaDescendente` |
| C DTOs profesionales | Respuestas sin exponer entidades | `dto/` |

## Endpoints

### Alquileres
```
GET /api/alquileres/desbloquear   body: {"idUsuario":"PREM-01","patente":"MON-001"}
GET /api/alquileres/finalizar     body: {"idUsuario":"PREM-01","patente":"MON-001","minutos":30,"metodoPago":"TARJETA"}
```
- Desbloquear pasa el vehículo a EN_VIAJE. Finalizar calcula la tarifa según el
  criterio activo, cobra y lo devuelve a EN_ESPERA.
- idUsuario que empieza con `PREM` → Premium (10% descuento).

### Reportes ordenados
```
GET /api/vehiculos/prioridad-carga      # batería de menor a mayor (orden natural)
GET /api/vehiculos/tarifa-descendente   # tarifa de mayor a menor (orden alternativo)
```

### Operaciones
```
PUT  /api/operaciones/criterio-tarifa?tipo=HORA_PICO   # ESTANDAR | HORA_PICO | CLIMATICO
POST /api/operaciones/deduplicar-gps    body: [{"latitud":1.0,"longitud":2.0}, ...]
```

## Ejemplos curl

```bash
# Desbloquear
curl -X GET http://localhost:8080/api/alquileres/desbloquear \
  -H "Content-Type: application/json" \
  -d '{"idUsuario":"U1","patente":"MON-001"}'

# Cambiar criterio a hora pico (afecta el próximo finalizar)
curl -X PUT "http://localhost:8080/api/operaciones/criterio-tarifa?tipo=HORA_PICO"

# Finalizar viaje (30 min)
curl -X GET http://localhost:8080/api/alquileres/finalizar \
  -H "Content-Type: application/json" \
  -d '{"idUsuario":"U1","patente":"MON-001","minutos":30,"metodoPago":"TARJETA"}'

# Reportes
curl http://localhost:8080/api/vehiculos/prioridad-carga
curl http://localhost:8080/api/vehiculos/tarifa-descendente

# Deduplicar GPS
curl -X POST http://localhost:8080/api/operaciones/deduplicar-gps \
  -H "Content-Type: application/json" \
  -d '[{"latitud":1.0,"longitud":2.0},{"latitud":1.0,"longitud":2.0},{"latitud":3.0,"longitud":4.0}]'
```

## Vehículos precargados

| Patente | Tipo | Batería | Tarifa | Estado inicial |
|---------|------|---------|--------|----------------|
| MON-001 | Monopatín | 80% | 450 | En Espera |
| MON-002 | Monopatín | 10% | 450 | En Espera (falla por batería) |
| BIC-001 | Bicicleta | 95% | 600 | En Espera |
| BIC-002 | Bicicleta | 50% | 700 | En Espera |
| MON-003 | Monopatín | 14% | 400 | En Espera (falla por batería) |
| MON-004 | Monopatín | 60% | 500 | En Reparación (no se puede desbloquear) |

Ver `PERFORMANCE.md` para el anexo técnico de rendimiento.

# EcoRide — Sistema de Movilidad Urbana (Spring Boot)

TP de Programación III. Módulo central de EcoRide: desbloqueo, cálculo de tarifa
y cobro de vehículos eléctricos, expuesto como API REST con Spring Boot.

## Cómo ejecutar

```bash
./mvnw spring-boot:run     # o: mvn spring-boot:run
```

La app levanta en `http://localhost:8080` con los vehículos precargados en
memoria (sin base de datos), definidos en `config/DatosEnMemoriaConfig.java`.

## Arquitectura por capas

- `model/` — clases de dominio (Vehiculo y subtipos, Usuario y subtipos,
  ProcesadorDePago y sus implementaciones, EstacionAnclaje).
- `service/` — `ServiceEcoride`: lógica de negocio (localizar, validar, calcular,
  cobrar).
- `controller/` — `ControllerEcoride`: punto de acceso HTTP GET.
- `Exeptions/` — excepciones de negocio + `ManejadorGlobalErrores` (traduce a HTTP 400).
- `dto/` — objetos de petición y respuesta para el JSON.
- `config/` — datos en memoria.

## Endpoint

```
GET /api/alquileres/desbloquear
```

Cuerpo JSON (apartado C de la consigna):

```json
{
  "idUsuario": "PREM-01",
  "patente": "MON-001",
  "metodoPago": "TARJETA"
}
```

- `idUsuario` que empieza con `PREM` → Usuario Premium (10% de descuento).
- `metodoPago` acepta `TARJETA` o `BILLETERA`.
- La tarifa base es propia de cada vehículo.

### Ejemplo (camino exitoso)

```bash
curl -X GET http://localhost:8080/api/alquileres/desbloquear \
  -H "Content-Type: application/json" \
  -d '{"idUsuario":"PREM-01","patente":"MON-001","metodoPago":"TARJETA"}'
```

### Casos de error (responden HTTP 400 con JSON de alerta)

```bash
# Vehículo no encontrado
-d '{"idUsuario":"U1","patente":"NO-EXISTE","metodoPago":"TARJETA"}'

# Batería insuficiente (MON-002 tiene 10%)
-d '{"idUsuario":"U1","patente":"MON-002","metodoPago":"TARJETA"}'

# Medio de pago inválido
-d '{"idUsuario":"U1","patente":"MON-001","metodoPago":"EFECTIVO"}'
```

> Nota: la consigna pide GET con cuerpo JSON. Es válido, pero algunas
> herramientas ignoran el body en GET; probalo con curl o Postman, que sí lo
> envían. Si la cátedra lo prefiere, cambiar `@GetMapping` por `@PostMapping`
> es un cambio de una línea.

## Vehículos precargados

| Patente | Tipo | Batería | Tarifa | Nota |
|---------|------|---------|--------|------|
| MON-001 | Monopatín | 80% | 450 | OK |
| MON-002 | Monopatín | 10% | 450 | falla por batería |
| BIC-001 | Bicicleta | 95% | 600 | OK |
| BIC-002 | Bicicleta | 50% | 600 | OK |
| MON-003 | Monopatín | 14% | 450 | falla por batería |

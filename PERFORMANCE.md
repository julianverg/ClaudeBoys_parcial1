# PERFORMANCE.md — Anexo Técnico de Rendimiento

EcoRide PRO — Programación III (UNLaR, 2026)

Este documento explica las decisiones de optimización exigidas en el apartado B
de la consigna.

## 1. Por qué la nueva búsqueda es más rápida que la búsqueda lineal anterior

En la primera versión, `EstacionAnclaje` guardaba los vehículos en una `List` y
para encontrar uno por patente recorría la lista de principio a fin comparando
patente por patente. Esa búsqueda es **lineal**, con complejidad **O(n)**: si hay
100.000 vehículos, en el peor caso hace 100.000 comparaciones.

En esta versión los vehículos se guardan en un `HashMap<String, Vehiculo>` donde
la clave es la patente. Buscar es una sola operación `get(patente)`, que calcula
el hash de la patente y va directo al "casillero" correspondiente. La complejidad
es **O(1) en promedio** (tiempo constante): tarda prácticamente lo mismo con 10 o
con 100.000 vehículos, porque no recorre nada.

En resumen: pasamos de "revisar todos los elementos hasta encontrarlo" a "ir
directo al elemento", a costa de un poco más de memoria para la tabla hash.

## 2. Cómo la deduplicación de alertas GPS evita los bucles anidados

La solución ingenua para quitar duplicados es comparar cada reporte contra todos
los demás con dos bucles anidados. Eso es **O(n²)**: con 10.000 coordenadas serían
100 millones de comparaciones, lo que satura la CPU del servidor.

Nuestro algoritmo (`ServicioGps.deduplicar`) hace **una sola pasada O(n)** usando
un `HashSet`:

- Recorremos la lista una vez con un único `for`.
- Por cada coordenada intentamos `set.add(coordenada)`.
- `add()` devuelve `true` solo si la coordenada **no estaba**; en ese caso la
  sumamos al resultado. Si devuelve `false`, ya existía y la descartamos.

La clave es que `HashSet` resuelve "¿ya vi esto?" en **O(1)** gracias a `equals()`
y `hashCode()` definidos en `CoordenadaGps`. Así, en lugar de comparar cada
elemento contra todos (n × n), comparamos cada elemento una sola vez contra una
estructura que responde de inmediato (n × 1).

## 3. Cómo resolvimos el orden natural sin romper el orden por tarifa

La consigna pide dos ordenamientos que no deben interferir entre sí:

- **Orden natural (prioridad de carga):** batería de menor a mayor. Lo definimos
  **dentro** de `Vehiculo`, implementando `Comparable<Vehiculo>` y su método
  `compareTo()`. Es la prioridad operativa intrínseca del vehículo, por eso vive
  en la propia clase y se usa con `Collections.sort(lista)`.

- **Orden alternativo (tarifa descendente):** es un criterio comercial y
  secundario. Lo definimos en una **clase externa** `ComparadorTarifaDescendente`
  que implementa `Comparator<Vehiculo>`. La clase `Vehiculo` ni se entera de que
  existe; se usa con `Collections.sort(lista, new ComparadorTarifaDescendente())`.

Como cada criterio vive en un lugar distinto (uno en `compareTo`, otro en una
clase `Comparator` aparte), **ordenar por tarifa no modifica ni pisa el orden
natural por batería**. Podemos pedir cualquiera de los dos en cualquier momento
sin que uno afecte al otro. Ambos comparadores usan `Integer.compare()` /
`Double.compare()` en lugar de restas, para evitar errores de overflow.

> Nota sobre la restricción del examen: toda la lógica de comparación,
> deduplicación y ordenamiento está escrita con bucles tradicionales y clases que
> implementan las interfaces, **sin lambdas, sin Stream API y sin paralelismo**,
> como exige la consigna.

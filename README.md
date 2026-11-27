# Pruebas unitarias en Spring Boot

Proyecto académico para demostrar pruebas unitarias de un servicio de productos con **Spring Boot 3.5.6**, **Java 17**, **JUnit 5**, **Mockito** y **AssertJ**.

## Requisitos
- JDK 17 o superior
- Maven 3.6 o superior

## Ejecutar las pruebas

Desde la raíz del proyecto:

```bash
mvn clean test
```

## Iniciar la aplicación

```bash
mvn spring-boot:run
```

La aplicación utiliza H2 en memoria. La consola se encuentra en `http://localhost:8080/h2-console`.

## API REST

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/productos` | Consultar todos los productos |
| GET | `/api/productos/{id}` | Consultar un producto por ID |
| POST | `/api/productos` | Registrar un producto |

Ejemplo de JSON para crear un producto:

```json
{
  "nombre": "Taladro",
  "precio": 1250.00,
  "stock": 8
}
```

## Pruebas incluidas

- Consulta de todos los productos mediante `findAll`.
- Creación de un producto mediante `create`.
- Validación del rechazo de un nombre vacío.
- Verificación de los datos enviados al repositorio con `ArgumentCaptor`.
- Uso de `@Mock`, `@InjectMocks`, Mockito y AssertJ.

Las pruebas del servicio aíslan la capa de persistencia mediante un repositorio simulado; no requieren levantar H2.

## Estructura del proyecto

```
src/
 ├── main/
 │   ├── java/com/example/productos/
 │   │   ├── controller/ProductoController.java
 │   │   ├── model/Producto.java
 │   │   ├── repository/ProductoRepository.java
 │   │   ├── service/ProductoService.java
 │   │   └── ProductoApplication.java
 │   └── resources/application.properties
 └── test/java/com/example/productos/service/ProductoServiceTest.java
```

## Evidencia de ejecución

Para generar la evidencia solicitada por la práctica, ejecuta `mvn clean test` en tu equipo y toma una captura de la terminal donde se observe el resultado `BUILD SUCCESS` y las pruebas ejecutadas. No se incluye una captura simulada: la evidencia debe corresponder a una ejecución real.

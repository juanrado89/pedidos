# Pedidos API

API REST para la gestión de un sistema de pedidos, desarrollada con
**Java 21** y **Spring Boot 3.5.6**.

El proyecto está orientado a aplicar conceptos habituales en un backend
moderno: arquitectura por capas, autenticación y autorización con JWT,
persistencia con JPA/Hibernate, PostgreSQL, validación, gestión de
pedidos y pagos, control de stock e integración básica con Apache Kafka.

## Tecnologías

-   Java 21
-   Spring Boot 3.5.6
-   Spring Web
-   Spring Security
-   Spring Data JPA / Hibernate
-   PostgreSQL
-   JWT (Access Token + Refresh Token)
-   BCrypt
-   MapStruct
-   Bean Validation
-   Apache Kafka
-   Swagger / OpenAPI
-   Docker Compose
-   Maven

## Funcionalidades principales

-   Registro y gestión de clientes.
-   Gestión de direcciones y productos.
-   Creación y gestión de pedidos y líneas de pedido.
-   Gestión de pagos y estados.
-   Actualización de stock asociada al flujo de pago.
-   Cálculo de precios e impuestos.
-   Autenticación mediante Access Token y Refresh Token.
-   Autorización basada en roles `CUSTOMER` y `ADMIN`.
-   Validación de acceso a recursos pertenecientes al cliente
    autenticado.
-   Paginación en consultas de productos.
-   Manejo centralizado de excepciones.
-   Documentación mediante Swagger / OpenAPI.
-   Publicación de eventos de creación de pedidos mediante Kafka.

## Arquitectura

La aplicación sigue una arquitectura por capas:

``` text
Controller
    |
    v
Service
    |
    v
Repository
    |
    v
PostgreSQL
```

Los DTOs separan los modelos expuestos por la API de las entidades de
persistencia y **MapStruct** realiza el mapeo entre ambos.

``` mermaid
flowchart LR
    Client[Cliente REST] --> Security[Spring Security / JWT]
    Security --> Controller
    Controller --> Service
    Service --> Repository
    Repository --> DB[(PostgreSQL)]
    Service --> Kafka[Apache Kafka]
```

## Modelo de dominio

Entidades principales:

-   `Customer`
-   `Address`
-   `Product`
-   `Order`
-   `OrderItem`
-   `Payment`
-   `RefreshToken`

``` mermaid
erDiagram
    CUSTOMER ||--o{ ADDRESS : tiene
    CUSTOMER ||--o{ ORDER : realiza
    ORDER ||--|{ ORDER_ITEM : contiene
    PRODUCT ||--o{ ORDER_ITEM : referencia
    ORDER ||--o| PAYMENT : tiene
```

## Seguridad

La API utiliza **Spring Security** con una configuración *stateless*
basada en JWT.

El flujo de autenticación incluye:

1.  El usuario inicia sesión.
2.  La API genera un **Access Token** y un **Refresh Token**.
3.  El Access Token permite acceder a endpoints protegidos.
4.  El Refresh Token permite obtener un nuevo Access Token.
5.  Los Refresh Tokens se persisten y pueden revocarse.

Los JWT incluyen información como `userId`, email y rol. La aplicación
diferencia principalmente entre `CUSTOMER` y `ADMIN`.

Determinados recursos comprueban además que el usuario autenticado sea
propietario del recurso solicitado para evitar exponer información de
otros clientes.

## Pedidos, pagos y stock

El proyecto implementa lógica de negocio para gestionar el ciclo de
pedidos y pagos:

``` text
Creación del pedido
        |
        v
Creación / asociación del pago
        |
        v
Cambio del pago a PAID
        |
        v
Actualización del stock
```

Se validan transiciones de estado para evitar operaciones inválidas,
como procesar nuevamente un pago ya completado.

El cálculo de precios utiliza `BigDecimal` y lógica específica de
*pricing*, incluyendo los impuestos correspondientes según la categoría
del producto.

## Kafka

La aplicación incluye una integración básica con **Apache Kafka** para
publicar eventos relacionados con la creación de pedidos, como
`OrderCreatedEvent`.

``` mermaid
sequenceDiagram
    participant C as Cliente
    participant API as Pedidos API
    participant DB as PostgreSQL
    participant K as Kafka

    C->>API: Crear pedido
    API->>DB: Persistir pedido
    API->>K: Publicar OrderCreatedEvent
    API-->>C: Respuesta
```

La integración tiene actualmente un alcance sencillo y sirve para
introducir comunicación basada en eventos.

## API y documentación

La API utiliza **Swagger / OpenAPI** para documentar endpoints,
parámetros y modelos de petición y respuesta.

## Configuración

Entre las variables de configuración utilizadas se encuentran:

``` text
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_SECRET
```

Las credenciales y secretos reales no deben almacenarse en el
repositorio.

## Ejecución local

### Requisitos

-   Java 21
-   Maven
-   PostgreSQL
-   Apache Kafka para las funcionalidades relacionadas con eventos

Clonar el repositorio:

``` bash
git clone https://github.com/juanrado89/pedidos.git
cd pedidos
```

Configurar las variables de entorno necesarias y ejecutar con Maven
Wrapper:

``` bash
./mvnw spring-boot:run
```

En Windows:

``` powershell
.\mvnw.cmd spring-boot:run
```

El repositorio incluye configuración de **Docker Compose** para la
infraestructura Kafka.

## Testing

Actualmente el proyecto dispone de una prueba básica de carga del
contexto de Spring. La ampliación de pruebas unitarias y de integración
es una de las mejoras previstas.

## Mejoras futuras

-   Ampliar la cobertura de tests unitarios y de integración.
-   Evolucionar la integración con Kafka y el procesamiento asíncrono.
-   Mejorar observabilidad y logging.
-   Incorporar automatización CI/CD.
-   Separar con mayor claridad la configuración de desarrollo y
    producción.

## Objetivo del proyecto

**Pedidos** es un proyecto personal creado para profundizar en
desarrollo backend con Java y Spring Boot, aplicando seguridad,
persistencia, diseño de APIs REST y lógica de negocio sobre un dominio
más completo que un CRUD básico.

## Autor

**Juan Alberto Rado Hernández**

-   GitHub: https://github.com/juanrado89
-   LinkedIn:
    https://www.linkedin.com/in/juan-alberto-rado-hern%C3%A1ndez-0ba83a243/

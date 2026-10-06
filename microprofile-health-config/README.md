# microprofile-health-config

The Orders endpoint written against MicroProfile and Jakarta APIs only, run on Helidon MP 4.

## Goal

Show JAX-RS, CDI, MicroProfile Config and MicroProfile Health in one resource, so the same source can move to another MicroProfile runtime such as Open Liberty or Payara.

## Run it

```bash
mvn -q compile exec:java
curl -s localhost:8080/orders
curl -s localhost:8080/orders/currency
curl -s localhost:8080/health/ready
ORDERS_CURRENCY=USD mvn -q exec:java
```

Expected: `/orders` returns the two orders, `/orders/currency` returns `EUR`, `/health/ready` reports status `UP` with a check named `orders`, and with `ORDERS_CURRENCY=USD` the currency becomes `USD`.

Not run end to end: the code compiles, but the server was not started and queried in this environment, so the output above comes from reading the code.

## What it proves

- `OrdersResource.java` injects `orders.currency` with `@ConfigProperty`; `META-INF/microprofile-config.properties` sets it to `EUR`, and a system property or env var overrides it.
- The nested `OrdersReady` bean, marked `@Readiness`, appears on `/health/ready` with data `items: 2`.
- `Main.java` is one line of Helidon startup; the rest is standard APIs.

## Trade-offs

- You get vendor portability and standard APIs.
- CDI container startup costs more than Javalin or Helidon SE, and runtime reflection makes native image harder than with Micronaut.
- The specifications lag framework-native features.

## When not to use it

- When you will never change vendors and want the lowest startup cost.
- When you need features outside the MicroProfile specs.

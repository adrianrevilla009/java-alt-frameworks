# javalin-minimal

The Orders endpoint in Javalin 6: one `App.java`, no DI container and no annotations.

## Goal

Show the smallest useful HTTP service for the shared Orders domain: `GET /orders` and `GET /orders/{id}`, with virtual threads enabled for request handling.

## Run it

```bash
mvn -q compile exec:java
curl -s localhost:8080/orders
curl -s localhost:8080/orders/2
```

Expected: `[{"id":1,"item":"book","qty":2},{"id":2,"item":"pen","qty":10}]` for the list, and `/orders/2` returns the pen. An unknown id returns status 404.

Not run end to end: the code compiles, but the server was not started and queried in this environment, so the output above comes from reading `App.java`.

## What it proves

- `Javalin.create(cfg -> cfg.useVirtualThreads = true)` in `App.java` gives thread-per-request handling on Jetty without reactive code.
- The two routes and the `Order` record fit in about 20 lines.
- Javalin 6 has no bundled JSON mapper, so `pom.xml` adds `jackson-databind` 2.17.2 explicitly.

## Trade-offs

- It starts fast and has little to learn, but you wire config, health and validation yourself.
- Routes and dependencies get no compile-time checks.
- Jackson must be added and pinned by hand; older versions cannot serialize records.

## When not to use it

- Large teams that want conventions, built-in config, health checks and DI; Micronaut or Helidon MP fit better.
- Services that need many cross-cutting features out of the box.

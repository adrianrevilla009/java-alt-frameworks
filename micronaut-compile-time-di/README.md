# micronaut-compile-time-di

The Orders endpoint on Micronaut 4, with injection wired by annotation processors at build time.

## Goal

Show dependency injection resolved by annotation processing during compilation, instead of reflection and classpath scanning at startup.

## Run it

```bash
mvn -q compile
ls target/classes/lab/orders | grep -i definition
mvn -q mn:run
curl -s localhost:8080/orders
```

Expected: the `ls` lists generated `*$Definition` classes, and the `curl` returns the two orders as JSON.

Not run end to end: the code compiles, but the server was not started and queried in this environment, so the output above comes from reading the code. The parent is `micronaut-parent` 4.6.3, with the processor versions pinned in `pom.xml`.

## What it proves

- `OrderController` receives `OrderService` through its constructor, and the compiler generates the bean definitions for both.
- `Order` is `@Serdeable`, so JSON is produced by generated serializers, not reflection.
- `Application.java` is only a `Micronaut.run` call; there is no scanning configuration.

## Trade-offs

- Startup is fast, memory is low, and injection errors show up at compile time.
- Annotation processors slow builds and complicate IDE setup.
- The community and integrations are smaller than Spring's, and the model differs from Spring despite similar annotations.

## When not to use it

- When the team depends on Spring-only libraries.
- When the code relies on runtime reflection or dynamic proxies.

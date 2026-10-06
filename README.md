# java-alt-frameworks

The same small Orders endpoint written in Micronaut, Helidon, Vert.x, Javalin, MicroProfile, Kotlin coroutines and a plain servlet WAR, so you can compare how each model wires a service.

## What is inside

| Folder | What it shows | Run |
| --- | --- | --- |
| [`micronaut-compile-time-di`](./micronaut-compile-time-di) | Micronaut 4 with dependency injection generated at compile time | `mvn -q mn:run` |
| [`helidon-nima`](./helidon-nima) | Helidon SE 4 web server, one virtual thread per request | `mvn -q compile exec:java` |
| [`vertx-event-loop`](./vertx-event-loop) | Vert.x event loop and the worker pool for blocking work | `mvn -q compile exec:java` |
| [`javalin-minimal`](./javalin-minimal) | Javalin on Jetty with virtual threads, one `main` and no container | `mvn -q compile exec:java` |
| [`microprofile-health-config`](./microprofile-health-config) | JAX-RS, CDI, Config and Health on Helidon MP 4 | `mvn -q compile exec:java` |
| [`kotlin-coroutines`](./kotlin-coroutines) | Three slow lookups run concurrently with coroutines | `mvn -q compile exec:java` |
| [`app-servers-tomcat-wildfly-liberty`](./app-servers-tomcat-wildfly-liberty) | One servlet WAR for Tomcat, WildFly and Open Liberty | `mvn -q package` |

Every folder serves or computes the same two orders (a book and a pen), the shared Orders domain. Startup time and memory were not measured, so there is no comparison table of those numbers.

## Prerequisites

- Java 21
- Maven 3.8 or later
- For the WAR folder only: a Tomcat 10.1, WildFly 34 or Open Liberty 24 install (none are bundled)
- `curl` to query the servers

## How to read it

Start with `javalin-minimal` for the smallest service, then `micronaut-compile-time-di` and `microprofile-health-config` for the two DI styles. `vertx-event-loop` and `kotlin-coroutines` cover the non-blocking models. Only `kotlin-coroutines` has been run end to end; the other folders compile, but their servers were not started and queried.

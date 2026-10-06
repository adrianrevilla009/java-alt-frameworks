# helidon-nima

The Orders endpoint on Helidon SE 4, whose web server runs each request on a virtual thread.

## Goal

Show the Helidon SE 4 web server (also called Nima), which is built on virtual threads with no Netty underneath, so blocking handlers can stay simple.

## Run it

```bash
mvn -q compile exec:java
curl -s localhost:8080/orders
curl -s localhost:8080/thread
```

Expected: `/orders` returns the two orders as a JSON string, and `/thread` returns `virtual`.

Not run end to end: the code compiles, but the server was not started and queried in this environment, so the output above comes from reading `App.java`. The JSON body is a literal string, so no mapper is needed.

## What it proves

- `App.routing` registers `GET /orders` and `GET /thread` on a Helidon `HttpRouting.Builder`.
- `/thread` returns `Thread.currentThread().isVirtual()` as text, so you can check the thread model yourself.
- The whole service is one 18-line class, with no DI and no reactive API.

## Trade-offs

- The mental model and stack traces stay simple, because handlers are plain blocking code.
- Helidon SE has no DI; the MP flavor adds CDI at a higher startup cost.
- The ecosystem is newer and smaller than Spring's.

## When not to use it

- When you need a rich starter ecosystem.
- When the team already standardizes on Spring Boot.

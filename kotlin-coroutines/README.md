# kotlin-coroutines

A console program in `Main.kt` that prices three Orders with coroutines and checks how long it took.

## Goal

Show Kotlin coroutines on the Orders domain: three slow price lookups run concurrently with `async` and `awaitAll`, and the program asserts the elapsed time. It is a console program rather than an HTTP server because the subject is the concurrency model.

## Run it

```bash
mvn -q compile exec:java
```

Expected output (run here with JDK 21):

```
totals=[6, 30, 3] in 222ms
OK: 3 lookups of 200ms ran concurrently on one thread
```

## What it proves

- `price()` in `Main.kt` is a `suspend` function that calls `delay(200)`, so three lookups finish in about 220 ms, not the roughly 600 ms a sequential loop would take.
- `check(ms < 500)` fails the program if the lookups ever run one after another.
- `delay` suspends the coroutine without blocking a thread, so everything runs inside one `runBlocking` call.

## Trade-offs

- Concurrent code reads like sequential code, and cancellation comes built in.
- It adds the Kotlin compiler plugin to a Java build, and `suspend` spreads through every caller.
- It overlaps with Java 21 virtual threads, which scale blocking code without a new language.

## When not to use it

- In an all-Java team on Java 21, where virtual threads solve the same problem with no new language.
- When you need an HTTP service: this folder has no server.

# vertx-event-loop

The Orders endpoint on Vert.x Web, with two extra routes that show which thread handles a request.

## Goal

Show the event-loop model: `/thread` is served on an event-loop thread, and `/blocking` hands sleeping work to the worker pool with `executeBlocking`.

## Run it

```bash
mvn -q compile exec:java
curl -s localhost:8080/orders
curl -s localhost:8080/thread
curl -s localhost:8080/blocking
```

Expected: `/orders` returns the two orders as JSON, `/thread` returns a name like `vert.x-eventloop-thread-N`, and `/blocking` returns `vert.x-worker-thread-N` after about 200 ms.

Not run end to end: the code compiles, but the server was not started and queried in this environment, so the output above comes from reading `App.java`.

## What it proves

- A verticle in `App.java` serves all connections from a small fixed set of event-loop threads.
- The `/blocking` handler sleeps 200 ms inside `executeBlocking`, so the event loop stays free.
- The thread name in each response lets you see the model without a profiler.

## Trade-offs

- It reaches very high connection density on few threads.
- The callback and future style is harder to read and debug than thread-per-request code.
- One blocking call on the event loop stalls every connection on that loop.

## When not to use it

- Teams that prefer imperative code and depend on blocking libraries such as JDBC; the virtual-thread folders fit that better.
- Services where nobody on the team will watch for accidental blocking calls.

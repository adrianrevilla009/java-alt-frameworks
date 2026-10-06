package lab.orders;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class App extends AbstractVerticle {
    static final JsonArray ORDERS = new JsonArray()
            .add(new JsonObject().put("id", 1).put("item", "book").put("qty", 2))
            .add(new JsonObject().put("id", 2).put("item", "pen").put("qty", 10));

    @Override
    public void start() {
        Router router = Router.router(vertx);
        router.get("/orders").handler(ctx -> ctx.json(ORDERS));
        // Shows which thread serves the request: an event-loop thread, never block it.
        router.get("/thread").handler(ctx -> ctx.end(Thread.currentThread().getName()));
        router.get("/blocking").handler(ctx -> vertx.<String>executeBlocking(() -> {
            Thread.sleep(200); // blocking work belongs on the worker pool
            return Thread.currentThread().getName();
        }).onSuccess(ctx::end));
        vertx.createHttpServer().requestHandler(router).listen(8080);
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new App());
    }
}

package lab.orders;

import io.javalin.Javalin;
import java.util.List;

public class App {
    record Order(int id, String item, int qty) {}

    static final List<Order> ORDERS = List.of(new Order(1, "book", 2), new Order(2, "pen", 10));

    static Javalin create() {
        return Javalin.create(cfg -> cfg.useVirtualThreads = true)
                .get("/orders", ctx -> ctx.json(ORDERS))
                .get("/orders/{id}", ctx -> {
                    int id = Integer.parseInt(ctx.pathParam("id"));
                    ORDERS.stream().filter(o -> o.id() == id).findFirst()
                            .ifPresentOrElse(ctx::json, () -> ctx.status(404));
                });
    }

    public static void main(String[] args) {
        create().start(8080);
    }
}

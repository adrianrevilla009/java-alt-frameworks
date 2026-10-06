package lab.orders;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Singleton;
import java.util.List;

@Controller("/orders")
public class OrderController {
    @Serdeable
    public record Order(int id, String item, int qty) {}

    // Injected through a bean definition generated at compile time (see target/classes/*$Definition.class).
    private final OrderService service;

    OrderController(OrderService service) {
        this.service = service;
    }

    @Get
    List<Order> list() {
        return service.all();
    }

    @Singleton
    static class OrderService {
        List<Order> all() {
            return List.of(new Order(1, "book", 2), new Order(2, "pen", 10));
        }
    }
}

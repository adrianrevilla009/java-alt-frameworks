package lab.orders;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Path("/orders")
@ApplicationScoped
public class OrdersResource {
    public record Order(int id, String item, int qty) {}

    // Value comes from META-INF/microprofile-config.properties; override with -Dorders.currency=USD or env ORDERS_CURRENCY.
    @Inject
    @ConfigProperty(name = "orders.currency", defaultValue = "EUR")
    String currency;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> list() {
        return List.of(new Order(1, "book", 2), new Order(2, "pen", 10));
    }

    @GET
    @Path("/currency")
    public String currency() {
        return currency;
    }

    @Readiness
    @ApplicationScoped
    public static class OrdersReady implements HealthCheck {
        @Override
        public HealthCheckResponse call() {
            return HealthCheckResponse.named("orders").up().withData("items", 2).build();
        }
    }
}

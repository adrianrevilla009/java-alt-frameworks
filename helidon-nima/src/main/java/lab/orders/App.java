package lab.orders;

import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;

public class App {
    static final String ORDERS = "[{\"id\":1,\"item\":\"book\",\"qty\":2},{\"id\":2,\"item\":\"pen\",\"qty\":10}]";

    static void routing(HttpRouting.Builder rules) {
        rules.get("/orders", (req, res) -> res.header("Content-Type", "application/json").send(ORDERS))
             // Helidon 4 runs every request on a virtual thread; this proves it.
             .get("/thread", (req, res) -> res.send(Thread.currentThread().isVirtual() ? "virtual" : "platform"));
    }

    public static void main(String[] args) {
        WebServer.builder().port(8080).routing(App::routing).build().start();
    }
}

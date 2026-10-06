# app-servers-tomcat-wildfly-liberty

A single `OrdersServlet` packaged as `orders.war`, meant to be deployed unchanged to three servers.

## Goal

Build one Jakarta Servlet 6 WAR and deploy the identical artifact to Tomcat 10.1, WildFly 34 and Open Liberty 24.

## Run it

```bash
mvn -q package
# Tomcat 10.1:   cp target/orders.war $CATALINA_HOME/webapps/ && $CATALINA_HOME/bin/startup.sh
# WildFly 34:    cp target/orders.war $JBOSS_HOME/standalone/deployments/ && $JBOSS_HOME/bin/standalone.sh
# Open Liberty:  cp target/orders.war $WLP_HOME/usr/servers/defaultServer/dropins/ && $WLP_HOME/bin/server run
curl -s localhost:8080/orders/orders
```

Expected: the build produces `target/orders.war`, and the `curl` returns `[{"id":1,"item":"book","qty":2},{"id":2,"item":"pen","qty":10}]`. The context path is the war name, `/orders`, and the servlet is mapped to `/orders`.

Not run end to end: the WAR builds, but no application server is installed here, so it was never deployed. Servers are installed by hand and none are bundled. Open Liberty needs the `servlet-6.0` feature in its `server.xml`.

## What it proves

- `OrdersServlet.java` uses only the `provided`-scope Jakarta Servlet API, so the WAR holds just application code.
- Each server supplies its own runtime, so the same file should deploy to all three.
- `pom.xml` uses `packaging` war, so the output name `orders.war` fixes the context path.

## Trade-offs

- A shared server amortizes JVM cost and patching across apps, and the WAR stays small.
- You are tied to the server's Jakarta version: Tomcat 10 and later use `jakarta.*`, not `javax.*`.
- The inner loop is slower and shared classpaths can conflict, compared with embedded frameworks.

## When not to use it

- For a single app in a container, where an executable JAR with an embedded server is simpler.
- When you need a framework's own features such as DI or health endpoints; a bare servlet has none.

package lnguyen.springvertx;

import io.vertx.core.Vertx;

public class VertxSystem {

    private final Vertx vertx;

    public VertxSystem(Vertx vertx) {
        this.vertx = vertx;
    }

    public Vertx getVertx() {
        return vertx;
    }

    public void deployService(VertxService service) {

    }
}

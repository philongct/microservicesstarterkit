package lnguyen.springvertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;

public abstract class VertxService extends AbstractVerticle {

    public enum ServiceType {
        STANDARD, WORKER, MULTITHREAD
    }

    public abstract ServiceType getServiceType();

    public boolean isDeployed() {
        return this.context != null;
    }

    @Override
    public synchronized void init(Vertx vertx, Context vertxContext) {
        super.init(vertx, vertxContext);
    }
}

package lnguyen.springvertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public abstract class SpringVertxService extends AbstractVerticle {

    public enum ServiceType {
        STANDARD, WORKER, MULTITHREAD
    }

    public abstract ServiceType getServiceType();

    public abstract boolean isSingleton();

    public boolean isDeployed() {
        return this.context != null;
    }

    @Override
    public synchronized void init(Vertx vertx, Context vertxContext) {
        if (isSingleton() && isDeployed()) {
            throw new IllegalStateException("Service already deployed");
        }

        super.init(vertx, vertxContext);
    }

    // do not allow children to override this
    final public void start(Future<Void> startFuture) throws Exception {
        super.start(startFuture);
    }

    protected EventBus getEventBus() {
        return this.vertx.eventBus();
    }
}

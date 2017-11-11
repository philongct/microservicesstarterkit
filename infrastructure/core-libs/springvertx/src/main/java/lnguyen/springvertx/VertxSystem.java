package lnguyen.springvertx;

import io.vertx.core.*;

public interface VertxSystem {

    Vertx getVertx();

    void deployService(Class<? extends SpringVertxService> serviceClass);

    void deployService(Class<? extends SpringVertxService> serviceClass, Handler<AsyncResult<String>> asyncResultHandler);

    void deployService(Class<? extends SpringVertxService> serviceClass, DeploymentOptions deploymentOptions);

    void deployService(Class<? extends SpringVertxService> serviceClass, DeploymentOptions deploymentOptions, Handler<AsyncResult<String>> asyncResultHandler);
}

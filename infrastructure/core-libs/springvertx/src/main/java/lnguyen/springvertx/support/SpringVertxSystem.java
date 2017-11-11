package lnguyen.springvertx.support;

import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import lnguyen.springvertx.SpringVertxService;
import lnguyen.springvertx.VertxSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class SpringVertxSystem implements VertxSystem {

    private static final Logger logger = LoggerFactory.getLogger(SpringVertxSystem.class);

    private final Vertx vertx;

    private ApplicationContext appCtx;

    public SpringVertxSystem(Vertx vertx, ApplicationContext appCtx) {
        this.vertx = vertx;
        this.appCtx = appCtx;
    }

    public Vertx getVertx() {
        return vertx;
    }

    public void deployService(Class<? extends SpringVertxService> serviceClass) {
        deployService(serviceClass, (DeploymentOptions) null);
    }

    public void deployService(Class<? extends SpringVertxService> serviceClass, DeploymentOptions deploymentOptions) {
        deployService(serviceClass, deploymentOptions, null);
    }

    public void deployService(Class<? extends SpringVertxService> serviceClass, Handler<AsyncResult<String>> asyncResultHandler) {
        deployService(serviceClass, null, asyncResultHandler);
    }

    public void deployService(Class<? extends SpringVertxService> serviceClass, DeploymentOptions deploymentOptions, Handler<AsyncResult<String>> asyncResultHandler) {
        SpringVertxService service = appCtx.getBean(serviceClass);
        DeploymentOptions options = deploymentOptions == null ? new DeploymentOptions() : new DeploymentOptions(deploymentOptions);
        switch (service.getServiceType()) {
            case WORKER:
                options.setWorker(true);
                break;
            case MULTITHREAD:
                options.setWorker(true);
                options.setMultiThreaded(true);
                break;
        }

        this.vertx.deployVerticle(service, options, asyncResultHandler);
    }
}

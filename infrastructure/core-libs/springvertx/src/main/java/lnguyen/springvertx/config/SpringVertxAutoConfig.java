package lnguyen.springvertx.config;

import io.vertx.core.Vertx;
import lnguyen.springvertx.VertxSystem;
import lnguyen.springvertx.support.IntegrationEventCodec;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

@Configuration
public class SpringVertxAutoConfig {

    @Primary
    @Lazy
    public VertxSystem vertxSystem() {
        Vertx vertx = Vertx.vertx();
        vertx.eventBus().registerCodec(new IntegrationEventCodec());
        return new VertxSystem(vertx);
    }
}

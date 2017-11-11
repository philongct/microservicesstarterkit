package lnguyen.springvertx.config;

import javax.annotation.PostConstruct;

import io.vertx.core.Vertx;
import lnguyen.springvertx.VertxSystem;
import lnguyen.springvertx.event.EventEmitter;
import lnguyen.springvertx.support.EventMessageCodec;
import lnguyen.springvertx.support.SpringVertxSystem;
import lnguyen.springvertx.support.event.DefaultEventEmitter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Configuration
public class SpringVertxAutoConfig {

    @Primary
    @Lazy
    @Bean
    public VertxSystem vertxSystem(ApplicationContext applicationContext) {
        Vertx vertx = Vertx.vertx();
        vertx.eventBus().registerCodec(new EventMessageCodec());
        return new SpringVertxSystem(vertx, applicationContext);
    }

    @Bean
    public EventEmitter eventEmitter() {
        VertxSystem vertxSystem = vertxSystem(null);
        DefaultEventEmitter eventEmitter = new DefaultEventEmitter();
        return eventEmitter;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void deployEventEmitter() {
        vertxSystem(null).deployService(DefaultEventEmitter.class);
    }

}

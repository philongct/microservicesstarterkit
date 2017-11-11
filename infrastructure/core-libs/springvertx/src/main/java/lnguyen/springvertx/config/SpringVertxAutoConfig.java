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

import static io.vertx.core.logging.LoggerFactory.LOGGER_DELEGATE_FACTORY_CLASS_NAME;

@Configuration
public class SpringVertxAutoConfig {

    static {
        System.setProperty(LOGGER_DELEGATE_FACTORY_CLASS_NAME, "io.vertx.core.logging.SLF4JLogDelegateFactory");
    }

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

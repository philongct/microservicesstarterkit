package lnguyen.springvertx;

import javax.swing.*;

import lnguyen.springvertx.config.SpringVertxAutoConfig;
import lnguyen.springvertx.support.SpringVertxSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Configuration
@Import({SpringVertxAutoConfig.class, FooEventHandler.class, BarEventHandler.class})
public class EventSystemTestConfig {
}

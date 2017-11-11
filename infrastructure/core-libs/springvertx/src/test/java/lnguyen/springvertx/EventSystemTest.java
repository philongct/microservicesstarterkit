package lnguyen.springvertx;

import lnguyen.springvertx.event.EventEmitter;
import lnguyen.springvertx.events.BarEvent;
import lnguyen.springvertx.events.FooEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EventSystemTestConfig.class})
public class EventSystemTest {

    @Autowired
    private FooEventHandler fooEventHandler;

    @Autowired
    private BarEventHandler barEventHandler;

    @Autowired
    private VertxSystem vertxSystem;

    @Autowired
    private EventEmitter eventEmitter;

    @Before
    public void deployHandler() {
        vertxSystem.deployService(FooEventHandler.class);
        vertxSystem.deployService(BarEventHandler.class);
    }

    @Test
    public void test() throws InterruptedException {
        Thread.sleep(1000L);
        eventEmitter.send(new FooEvent());
        Thread.sleep(100);
        assertEquals(new FooEvent().get(), fooEventHandler.val);
        assertEquals(new BarEvent().get(), barEventHandler.val);
    }
}

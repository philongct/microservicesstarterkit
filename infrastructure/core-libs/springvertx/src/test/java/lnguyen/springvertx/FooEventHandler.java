package lnguyen.springvertx;

import lnguyen.springvertx.event.AbstractEventHandlerService;
import lnguyen.springvertx.event.EventEmitter;
import lnguyen.springvertx.events.FooEvent;
import lnguyen.springvertx.events.BarEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FooEventHandler extends AbstractEventHandlerService<FooEvent> {

    public String val;

    @Autowired
    private EventEmitter eventEmitter;

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public boolean isLocal() {
        return true;
    }

    @Override
    public void handle(FooEvent event) {
        val = event.get();
        eventEmitter.publish(new BarEvent());
    }
}

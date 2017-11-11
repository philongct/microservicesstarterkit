package lnguyen.springvertx;

import lnguyen.springvertx.event.AbstractEventHandlerService;
import lnguyen.springvertx.events.BarEvent;
import lnguyen.springvertx.events.FooEvent;
import org.springframework.stereotype.Component;

@Component
public class BarEventHandler extends AbstractBarEventHandler {

    public String val;

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public boolean isLocal() {
        return true;
    }

    @Override
    public void handle(BarEvent event) {
        val = event.get();
    }
}

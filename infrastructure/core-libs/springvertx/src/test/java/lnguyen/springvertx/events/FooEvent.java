package lnguyen.springvertx.events;

import lnguyen.springvertx.event.IntegrationEvent;
import lnguyen.springvertx.support.event.LocalEvent;

public class FooEvent implements IntegrationEvent {
    public String get() {
        return "foo";
    }

    @Override
    public boolean isLocal() {
        return true;
    }
}

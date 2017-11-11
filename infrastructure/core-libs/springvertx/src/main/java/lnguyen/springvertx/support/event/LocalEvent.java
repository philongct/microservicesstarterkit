package lnguyen.springvertx.support.event;

import lnguyen.springvertx.event.IntegrationEvent;

public abstract class LocalEvent implements IntegrationEvent {
    @Override
    public boolean isLocal() {
        return true;
    }
}

package lnguyen.springvertx.support.event;

import lnguyen.springvertx.event.IntegrationEvent;

public abstract class GlobalEvent implements IntegrationEvent {
    @Override
    public boolean isLocal() {
        return false;
    }
}

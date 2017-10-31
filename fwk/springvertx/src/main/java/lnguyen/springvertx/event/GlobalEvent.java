package lnguyen.springvertx.event;

public abstract class GlobalEvent implements IntegrationEvent {
    @Override
    public boolean isLocal() {
        return false;
    }
}

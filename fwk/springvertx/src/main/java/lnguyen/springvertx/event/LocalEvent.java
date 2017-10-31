package lnguyen.springvertx.event;

public abstract class LocalEvent implements IntegrationEvent {
    @Override
    public boolean isLocal() {
        return true;
    }
}

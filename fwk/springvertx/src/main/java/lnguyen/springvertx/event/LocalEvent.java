package lnguyen.springvertx.event;

public abstract class LocalEvent implements VertxEvent {
    @Override
    public boolean isLocal() {
        return true;
    }
}

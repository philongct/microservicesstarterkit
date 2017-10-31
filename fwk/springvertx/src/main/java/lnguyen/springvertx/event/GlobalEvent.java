package lnguyen.springvertx.event;

public abstract class GlobalEvent implements VertxEvent {
    @Override
    public boolean isLocal() {
        return false;
    }
}

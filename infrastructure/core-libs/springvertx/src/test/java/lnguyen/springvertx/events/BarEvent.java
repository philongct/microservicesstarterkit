package lnguyen.springvertx.events;

import lnguyen.springvertx.support.event.LocalEvent;

public class BarEvent extends LocalEvent {
    public String get() {
        return "bar";
    }
}

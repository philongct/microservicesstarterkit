package lnguyen.springvertx.event;

import io.vertx.core.eventbus.EventBus;

public interface EventEmitter {

    EventBus send(IntegrationEvent event);

    EventBus send(IntegrationEvent event, long timeout);

    EventBus publish(IntegrationEvent event);

}

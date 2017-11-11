package lnguyen.springvertx.event;

import io.vertx.core.eventbus.MessageConsumer;
import lnguyen.springvertx.SpringVertxService;
import lnguyen.springvertx.support.event.EventToEventBusAddressResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEventHandlerService<E extends IntegrationEvent> extends SpringVertxService {

    private static final Logger logger = LoggerFactory.getLogger(AbstractEventHandlerService.class);

    @Override
    public ServiceType getServiceType() {
        return ServiceType.WORKER;
    }

    @Override
    final public void start() {
        MessageConsumer<E> consumer;
        String address = EventToEventBusAddressResolver.resolveAddress(this);
        if (isLocal()) {
            consumer = getEventBus().<E>localConsumer(address);
        } else {
            consumer = getEventBus().<E>consumer(address);
        }

        consumer.handler(message -> {
            message.reply("Received by " + this);
            handle(message.body());
        });

        logger.info("Handler {} of event {} started", this, address);
    }

    public abstract boolean isLocal();

    public abstract void handle(E event);
}

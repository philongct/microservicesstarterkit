package lnguyen.springvertx.support.event;

import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import lnguyen.springvertx.SpringVertxService;
import lnguyen.springvertx.event.EventEmitter;
import lnguyen.springvertx.event.IntegrationEvent;
import lnguyen.springvertx.support.EventMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultEventEmitter extends SpringVertxService implements EventEmitter {

    private static final Logger logger = LoggerFactory.getLogger(DefaultEventEmitter.class);

    @Override
    public void start() {
        logger.info("Event emitter started");
    }

    @Override
    public EventBus send(IntegrationEvent event) {
        return send(event, 0);
    }

    @Override
    public EventBus send(IntegrationEvent event, long timeout) {
        DeliveryOptions options = new DeliveryOptions();
        options.setCodecName(EventMessageCodec.CODEC_NAME);
        if (timeout > 0) {
            options.setSendTimeout(timeout);
        }

        return getEventBus().send(EventToEventBusAddressResolver.resolveAddress(event), event, options, res -> {
            if (res.failed() && logger.isWarnEnabled()) {
                logger.warn("No handler for event " + event);
            }
        });
    }

    @Override
    public EventBus publish(IntegrationEvent event) {
        DeliveryOptions options = new DeliveryOptions();
        options.setCodecName(EventMessageCodec.CODEC_NAME);

        return getEventBus().publish(EventToEventBusAddressResolver.resolveAddress(event), event, options);
    }

    @Override
    public ServiceType getServiceType() {
        return ServiceType.WORKER;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}

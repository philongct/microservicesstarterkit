package lnguyen.springvertx.support.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import lnguyen.springvertx.event.AbstractEventHandlerService;
import lnguyen.springvertx.event.IntegrationEvent;

public class EventToEventBusAddressResolver {
    public static String resolveAddress(AbstractEventHandlerService<? extends IntegrationEvent> handlerInstance) {
        return getEventClass(handlerInstance).getName();
    }

    public static String resolveAddress(IntegrationEvent event) {
        return event.getClass().getName();
    }

    private static Class<? extends IntegrationEvent> getEventClass(AbstractEventHandlerService<? extends IntegrationEvent> instance) {
        Class<?> clazz = instance.getClass();
        Type classType = clazz.getGenericSuperclass();

        while(!classType.getTypeName().startsWith(AbstractEventHandlerService.class.getName())) {
            clazz = clazz.getSuperclass();
            if (clazz.getName().startsWith(Object.class.getName())) {
                throw new IllegalStateException("Unknown error");
            }
            classType = clazz.getGenericSuperclass();
        }

        Type[] types = ((ParameterizedType) classType).getActualTypeArguments();
        return (Class<? extends IntegrationEvent>) types[0];
    }
}

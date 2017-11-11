package lnguyen.springvertx.support;

import com.google.gson.Gson;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.eventbus.impl.CodecManager;
import lnguyen.springvertx.event.IntegrationEvent;

public class EventMessageCodec implements MessageCodec<IntegrationEvent, IntegrationEvent> {

    public static final String CODEC_NAME = EventMessageCodec.class.getName();

    private MessageCodec<String, String> stringMessageCodec = CodecManager.STRING_MESSAGE_CODEC;

    @Override
    public void encodeToWire(Buffer buffer, IntegrationEvent integrationEvent) {
        if (integrationEvent.isLocal()) {
            throw new IllegalAccessError("Not support local event");
        }

        stringMessageCodec.encodeToWire(buffer, new Gson().toJson(new SerializableObjectWrapper<IntegrationEvent>(integrationEvent)));
    }

    @Override
    public IntegrationEvent decodeFromWire(int i, Buffer buffer) {
        String jsonContent = stringMessageCodec.decodeFromWire(i, buffer);
        SerializableObjectWrapper<IntegrationEvent> wrapper = new Gson().fromJson(jsonContent, SerializableObjectWrapper.class);
        try {
            return wrapper.getObject();
        } catch (ClassNotFoundException e) {
            throw new IllegalAccessError("Class not found " + e.getLocalizedMessage());
        }
    }

    @Override
    public IntegrationEvent transform(IntegrationEvent integrationEvent) {
        return integrationEvent;
    }

    @Override
    public String name() {
        return CODEC_NAME;
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}

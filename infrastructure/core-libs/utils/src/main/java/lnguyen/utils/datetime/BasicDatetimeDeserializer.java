package lnguyen.utils.datetime;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class BasicDatetimeDeserializer extends StdDeserializer<Date> {

    public BasicDatetimeDeserializer() {
        this(null);
    }

    public BasicDatetimeDeserializer(Class<Date> t) {
        super(t);
    }

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return DatetimeUtils.BASIC_DT.parseDateTime(jsonParser.getText()).toDate();
    }
}

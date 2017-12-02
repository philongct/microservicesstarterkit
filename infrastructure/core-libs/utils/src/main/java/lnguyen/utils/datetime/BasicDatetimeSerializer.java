package lnguyen.utils.datetime;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class BasicDatetimeSerializer extends StdSerializer<Date> {

    public BasicDatetimeSerializer() {
        this(null);
    }

    public BasicDatetimeSerializer(Class<Date> t) {
        super(t);
    }

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(DatetimeUtils.BASIC_DT.print(date.getTime()));
    }
}

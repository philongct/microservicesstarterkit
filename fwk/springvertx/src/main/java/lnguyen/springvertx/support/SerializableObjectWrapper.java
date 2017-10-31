package lnguyen.springvertx.support;

import java.io.Serializable;
import java.lang.reflect.Type;

import com.google.gson.Gson;

public class SerializableObjectWrapper<T> implements Serializable {

    private String objectClass;

    private String serialized;

    public SerializableObjectWrapper(T object) {
        this.objectClass = object.getClass().getName();
        this.serialized = new Gson().toJson(object);
    }

    public T getObject() throws ClassNotFoundException {
        return new Gson().fromJson(serialized, (Type) Class.forName(objectClass));
    }
}

package org.apache.dubbo.common.serialize.jackson.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * ExceptionJsonDeserializer
 * <p>
 * Creation Time: 2022-10-08 17:52:04
 *
 * @author Kevin Huang
 * @since 3.0.0
 */
public class ThrowableJsonDeserializer extends JsonDeserializer<Throwable> {
    public static final ThrowableJsonDeserializer INSTANCE = new ThrowableJsonDeserializer();

    @Override
    public Throwable deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        byte[] bytes = jsonParser.getBinaryValue();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        try (ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream)) {
            return (Throwable) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException(e.getMessage(), e);
        }
    }
}

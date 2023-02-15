package org.apache.dubbo.common.serialize.jackson.throwable;

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

    @Override
    public Throwable deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        byte[] bytes = jsonParser.getBinaryValue();
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return (Throwable) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

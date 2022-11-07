package org.apache.dubbo.common.serialize.jackson.throwable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * ExceptionJsonDeserializer
 * <p>
 * Creation Time: 2022-10-08 17:52:04
 *
 * @author Kevin Huang
 * @since 3.0.0
 */
public class ThrowableJsonDeserializer extends JsonDeserializer<Throwable> {
    private final ObjectMapper objectMapper;

    public ThrowableJsonDeserializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Throwable deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        byte[] bytes = jsonParser.getBinaryValue();
        return objectMapper.readValue(bytes, Throwable.class);
    }
}

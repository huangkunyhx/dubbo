package org.apache.dubbo.common.serialize.jackson.throwable;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * ExceptionJsonSerializer
 * <p>
 * Creation Time: 2022-10-08 17:49:49
 *
 * @author Kevin Huang
 * @since 3.0.0
 */
public class ThrowableJsonSerializer extends JsonSerializer<Throwable> {
    private final ObjectMapper objectMapper;

    public ThrowableJsonSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void serialize(Throwable throwable, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        byte[] bytes = objectMapper.writeValueAsBytes(throwable);
        gen.writeBinary(bytes);
    }
}

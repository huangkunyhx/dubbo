package org.apache.dubbo.common.serialize.jackson.throwable;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * ExceptionJsonSerializer
 * <p>
 * Creation Time: 2022-10-08 17:49:49
 *
 * @author Kevin Huang
 * @since 3.0.0
 */
public class ThrowableJsonSerializer extends JsonSerializer<Throwable> {

    @Override
    public void serialize(Throwable throwable, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(throwable);
            objectOutputStream.flush();
            gen.writeBinary(outputStream.toByteArray());
        }
    }
}

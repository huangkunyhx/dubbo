package org.apache.dubbo.common.serialize.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.serialize.ObjectInput;
import org.apache.dubbo.common.serialize.ObjectOutput;
import org.apache.dubbo.common.serialize.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.apache.dubbo.common.serialize.Constants.JACKSON_SERIALIZATION_ID;

/**
 * JacksonSerialization
 * <p>
 * Creation Time: 2022-10-27 09:16:17
 *
 * @author Kevin Huang
 * @since 3.1.1.1
 */
public class JacksonSerialization implements Serialization {
    private final ObjectMapper objectMapper;

    public JacksonSerialization() {
        this.objectMapper = new ObjectMapper();
    }

    public JacksonSerialization(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte getContentTypeId() {
        return JACKSON_SERIALIZATION_ID;
    }

    @Override
    public String getContentType() {
        return "text/json";
    }

    @Override
    public ObjectOutput serialize(URL url, OutputStream output) throws IOException {
        return new JacksonObjectOutput(output, this.objectMapper);
    }

    @Override
    public ObjectInput deserialize(URL url, InputStream input) throws IOException {
        return new JacksonObjectInput(input, this.objectMapper);
    }
}

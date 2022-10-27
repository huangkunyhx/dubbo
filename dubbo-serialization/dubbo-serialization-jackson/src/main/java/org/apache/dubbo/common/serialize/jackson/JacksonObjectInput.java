package org.apache.dubbo.common.serialize.jackson;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.dubbo.common.serialize.ObjectInput;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * JacksonObjectInput
 * <p>
 * Creation Time: 2022-10-27 16:35:59
 *
 * @author Kevin Huang
 * @since 3.1.1.1
 */
public class JacksonObjectInput implements ObjectInput {
    private final InputStream is;
    private final ObjectMapper objectMapper;

    public JacksonObjectInput(InputStream is, ObjectMapper objectMapper) {
        this.is = is;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean readBool() throws IOException {
        return readObject(boolean.class);
    }

    @Override
    public byte readByte() throws IOException {
        return readObject(byte.class);
    }

    @Override
    public short readShort() throws IOException {
        return readObject(short.class);
    }

    @Override
    public int readInt() throws IOException {
        return readObject(int.class);
    }

    @Override
    public long readLong() throws IOException {
        return readObject(long.class);
    }

    @Override
    public float readFloat() throws IOException {
        return readObject(float.class);
    }

    @Override
    public double readDouble() throws IOException {
        return readObject(double.class);
    }

    @Override
    public String readUTF() throws IOException {
        return readObject(String.class);
    }

    @Override
    public byte[] readBytes() throws IOException {
        int length = is.read();
        byte[] bytes = new byte[length];
        int read = is.read(bytes, 0, length);
        if (read != length) {
            throw new IllegalArgumentException("deserialize failed. expected read length: " + length + " but actual read: " + read);
        }
        return bytes;
    }

    @Override
    public Object readObject() throws IOException {
        return this.readObject(Object.class, null);
    }

    @Override
    public <T> T readObject(Class<T> cls) throws IOException {
        return this.readObject(cls, null);
    }

    @Override
    public <T> T readObject(Class<T> cls, Type type) throws IOException {
        int length = readLength();
        byte[] bytes = new byte[length];
        int read = is.read(bytes, 0, length);
        if (read != length) {
            throw new IllegalArgumentException("deserialize failed. expected read length: " + length + " but actual read: " + read);
        }
        if (null != type) {
            return this.objectMapper.readValue(bytes, this.objectMapper.constructType(type));
        } else {
            return this.objectMapper.readValue(bytes, cls);
        }
    }

    private int readLength() throws IOException {
        byte[] bytes = new byte[Integer.BYTES];
        int read = is.read(bytes, 0, Integer.BYTES);
        if (read != Integer.BYTES) {
            throw new IllegalArgumentException("deserialize failed. expected read length: " + Integer.BYTES + " but actual read: " + read);
        }
        int value = 0;
        for (byte b : bytes) {
            value = (value << 8) + (b & 0xFF);
        }
        return value;
    }
}

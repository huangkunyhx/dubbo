/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.common.serialize.jackson;

import org.apache.dubbo.common.serialize.ObjectOutput;

import java.io.IOException;
import java.io.OutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JacksonObjectOutput
 * <p>
 * Creation Time: 2022-10-27 16:47:10
 *
 * @author Kevin Huang
 * @since 3.1.1.1
 */
public class JacksonObjectOutput implements ObjectOutput {
    private final OutputStream os;
    private final ObjectMapper objectMapper;

    public JacksonObjectOutput(OutputStream out, ObjectMapper objectMapper) {
        this.os = out;
        this.objectMapper = objectMapper;
    }

    @Override
    public void writeBool(boolean v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeByte(byte v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeShort(short v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeInt(int v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeLong(long v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeFloat(float v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeDouble(double v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeUTF(String v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeBytes(byte[] b) throws IOException {
        os.write(b.length);
        os.write(b);
    }

    @Override
    public void writeBytes(byte[] b, int off, int len) throws IOException {
        os.write(len);
        os.write(b, off, len);
    }

    @Override
    public void flushBuffer() throws IOException {
        os.flush();
    }

    @Override
    public void writeObject(Object obj) throws IOException {
        byte[] bytes = this.objectMapper.writer().writeValueAsBytes(obj);
        writeLength(bytes.length);
        os.write(bytes);
        os.flush();
    }

    private void writeLength(int value) throws IOException {
        byte[] bytes = new byte[Integer.BYTES];
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            bytes[length - i - 1] = (byte) (value & 0xFF);
            value >>= 8;
        }
        os.write(bytes);
    }
}

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

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.serialize.ObjectInput;
import org.apache.dubbo.common.serialize.ObjectOutput;
import org.apache.dubbo.common.serialize.Serialization;
import org.apache.dubbo.common.serialize.jackson.throwable.ThrowableJsonDeserializer;
import org.apache.dubbo.common.serialize.jackson.throwable.ThrowableJsonSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

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
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Throwable.class, new ThrowableJsonSerializer());
        simpleModule.addDeserializer(Throwable.class, new ThrowableJsonDeserializer());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        this.objectMapper = objectMapper;
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

package com.skillbox.devpub.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class TimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//        jsonGenerator.writeString(localDateTime.toEpochSecond(ZoneOffset.UTC) + "");
        jsonGenerator.writeString(localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond() + "");
    }
}

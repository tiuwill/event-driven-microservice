package com.cardservice.query.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonParserUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    private JsonParserUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String parseObjectToJson(Object objectClass) {
        try {
            return objectMapper.writeValueAsString(objectClass);
        } catch (Exception e) {
            log.error("Error parsing object to JSON: {}", e.getMessage());
            throw new RuntimeException("Error parsing object to JSON", e);
        }
    }

    public static <T> T readValue(String eventData, Class<T> targetClass) {
        try {
            return objectMapper.readValue(eventData, targetClass);
        } catch (Exception e) {
            log.error("Error parsing JSON to object: {}", e.getMessage());
            throw new RuntimeException("Error parsing JSON to object", e);
        }
    }

}

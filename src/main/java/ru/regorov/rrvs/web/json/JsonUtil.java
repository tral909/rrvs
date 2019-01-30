package ru.regorov.rrvs.web.json;

import com.fasterxml.jackson.databind.ObjectReader;

import java.io.IOException;
import java.util.List;

import static ru.regorov.rrvs.web.json.JacksonObjectMapper.getMapper;

public class JsonUtil {

    public static <T> List<T> readValues(String json, Class<T> type) {
        ObjectReader reader = getMapper().readerFor(type);
        try {
            return reader.<T>readValues(json).readAll();
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read array from JSON:\n'" + json + "'", e);
        }
    }

    public static <T> T readValue(String json, Class<T> type) {
        try {
            return getMapper().readValue(json, type);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read from JSON:\n'" + json + "'", e);
        }
    }

    public static <T> String writeValue(T obj) {
        try {
            return getMapper().writeValueAsString(obj);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }
}

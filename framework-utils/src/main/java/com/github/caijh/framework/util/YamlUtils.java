package com.github.caijh.framework.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.github.caijh.framework.core.exception.YamlParseException;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class YamlUtils {

    private static final ObjectMapper YAML_READER = new YAMLMapper();
    private static final ObjectMapper YAML_WRITER = new YAMLMapper();
    private static final ObjectMapper JSON_READER = new ObjectMapper();
    private static final ObjectMapper JSON_WRITER = new ObjectMapper();

    static {
        YAML_READER.disable(FAIL_ON_UNKNOWN_PROPERTIES);
    }

    private YamlUtils() {

    }

    public static String toJsonString(String yaml) {
        try {
            Object obj = YAML_READER.readValue(yaml, Object.class);
            return JSON_WRITER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new YamlParseException(e);
        }
    }

    public static String toYamlString(String json) {
        try {
            JsonNode jsonNode = JSON_READER.readTree(json);
            return YAML_WRITER.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            throw new YamlParseException(e);
        }
    }

    public static <T> T toJava(File yamlFile, Class<T> type) {
        try {
            return YAML_READER.readValue(yamlFile, type);
        } catch (IOException e) {
            throw new YamlParseException(e);
        }
    }

}

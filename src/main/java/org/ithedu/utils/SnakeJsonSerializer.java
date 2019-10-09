/*
 * Copyright 2016-2018 the original author or authors.
 *
 * All right reserved by JiurongTech
 */

package org.ithedu.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class SnakeJsonSerializer {
  public static final ObjectMapper MAPPER;

  static {
    MAPPER = new ObjectMapper();
    MAPPER.findAndRegisterModules();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    MAPPER.setDateFormat(df);
    MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    MAPPER.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    MAPPER.setSerializationInclusion(Include.NON_NULL);
  }

  public static <T> T deSerialize(String json, Class<T> type) {
    T value;
    try {
      value = MAPPER.readValue(json, type);
    } catch (IOException e) {
      throw new JsonDeserializeException(e);
    }
    return value;
  }

  public static <T> T deSerialize(String json, TypeReference<T> typeReference) {
    T value;
    try {
      value = MAPPER.readValue(json, typeReference);
    } catch (IOException e) {
      throw new JsonDeserializeException(e);
    }
    return value;
  }

  public static String serialize(Object object) {
    String value;
    try {
      value = MAPPER.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new JsonSerializeException(e);
    }
    return value;
  }

  public static JsonNode toJsonNode(Object object) {
    JsonNode node = MAPPER.convertValue(object, JsonNode.class);
    return node;
  }

  public static String format(Object object) {
    String value;
    try {
      value = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new JsonSerializeException(e);
    }
    return value;
  }

  public static JsonNode readTree(String json) {
    JsonNode node;
    try {
      node = MAPPER.readTree(json);
    } catch (IOException e) {
      throw new JsonDeserializeException(e);
    }
    return node;
  }

  public static <T> T deSerialize(String json, JavaType type) {
    T value;
    try {
      value = MAPPER.readValue(json, type);
    } catch (IOException e) {
      throw new JsonDeserializeException(e);
    }
    return value;
  }

  public static <T> T treeToValue(JsonNode node, Class<T> targetClass) {
    try {
      return MAPPER.treeToValue(node, targetClass);
    } catch (JsonProcessingException e) {
      throw new JsonDeserializeException(e);
    }
  }

  public static <T> T treeToValue(JsonNode node, JavaType type) {
    try {
      return MAPPER.readValue(node.traverse(), type);
    } catch (IOException e) {
      throw new JsonDeserializeException(e);
    }
  }

  public static <T> T treeToValue(JsonNode node, TypeReference<T> type) {
    try {
      return MAPPER.readValue(node.traverse(), type);
    } catch (IOException e) {
      throw new JsonDeserializeException(e);
    }
  }
}

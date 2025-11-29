package com.rrp.exec.dashboard.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;

public class JsonLoader {

  private static final ObjectMapper mapper = new ObjectMapper();

  public static <T> T load(String path, Class<T> clazz) {
    try (InputStream is = JsonLoader.class.getClassLoader().getResourceAsStream(path)) {
      if (is == null) throw new IllegalArgumentException("Resource not found: " + path);
      return mapper.readValue(is, clazz);
    } catch (Exception e) {
      throw new RuntimeException("Failed to load mock data from " + path, e);
    }
  }

  public static <T> List<T> loadList(String path, Class<T> clazz) {
    try (InputStream is = JsonLoader.class.getClassLoader().getResourceAsStream(path)) {
      if (is == null) throw new IllegalArgumentException("Resource not found: " + path);
      return mapper.readValue(
          is, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
    } catch (Exception e) {
      throw new RuntimeException("Failed to load mock list from " + path, e);
    }
  }
}

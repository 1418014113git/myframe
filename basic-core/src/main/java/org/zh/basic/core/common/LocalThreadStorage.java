
package org.zh.basic.core.common;

import java.util.HashMap;
import java.util.Map;

import org.zh.basic.common.exception.GlobalErrorEnum;
import org.zh.basic.common.exception.GlobalErrorException;

/**
 * 本地存储获取.
 */
public class LocalThreadStorage {

  private static ThreadLocal<Map<String, Object>> localStorage = new ThreadLocal<Map<String, Object>>();

  public static void put(String key, Object value) {
    Map<String, Object> storage = localStorage.get();
    if (storage == null) {
      storage = new HashMap<String, Object>();
      localStorage.set(storage);
    }
    storage.put(key, value);
  }

  public static String get(String key) {
    return get(key, String.class);
  }

  public static Integer getInt(String key) {
    return get(key, Integer.class);
  }

  public static Long getLong(String key) {
    return get(key, Long.class);
  }

  public static Boolean getBoolean(String key) {
    return get(key, Boolean.class);
  }

  @SuppressWarnings("unchecked")
  private static <T> T get(String key, Class<T> clazz) {
    Map<String, Object> storage = localStorage.get();
    if (storage == null) {
      return null;
    }
    Object value = storage.get(key);
    if (value == null) {
      return null;
    }
    if (String.class.isInstance(value) || Integer.class.isInstance(value) || Boolean.class.isInstance(value)
        || Long.class.isInstance(value)) {
      return (T) value;
    } else {
      throw new GlobalErrorException(GlobalErrorEnum.UNSUPPORT);
    }
  }

  public static void clear() {
    localStorage.remove();
  }
}

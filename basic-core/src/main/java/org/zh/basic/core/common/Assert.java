/*
 * Copyright (C) 2017 On36 Labs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.common;

import java.util.Collection;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import org.zh.basic.common.exception.GlobalErrorEnum;
import org.zh.basic.common.exception.GlobalErrorException;


/**
 * <功能描述/>
 */
public abstract class Assert {

  public static void notNull(Object data) {
    if (data == null || data.toString().trim().length() == 0) {
      throw new GlobalErrorException(GlobalErrorEnum.DATA_NOT_VALID);
    }
  }

  public static void notNull(Object data, String message) {
    if (data == null || data.toString().trim().length() == 0) {
      throw new GlobalErrorException(GlobalErrorEnum.DATA_NOT_VALID.getCode(), message);
    }
  }

  public static void notNull(Object data, GlobalErrorEnum errorEnum, String message) {
    if (data == null || data.toString().trim().length() == 0) {
      throw new GlobalErrorException(errorEnum.getCode(),  String.format(errorEnum.getMessage(), message));
    }
  }

  public static void notEmpty(Map<?, ?> map, String message) {
    if (CollectionUtils.isEmpty(map)) {
      throw new GlobalErrorException(GlobalErrorEnum.DATA_NOT_VALID.getCode(), message);
    }
  }

  public static void notEmpty(Map<?, ?> map, GlobalErrorEnum errorEnum, String message) {
    if (CollectionUtils.isEmpty(map)) {
      throw new GlobalErrorException(errorEnum.getCode(), String.format(errorEnum.getMessage(), message));
    }
  }

  public static void notEmpty(Object[] array, String message) {
    if (ObjectUtils.isEmpty(array)) {
      throw new GlobalErrorException(GlobalErrorEnum.DATA_NOT_VALID.getCode(), message);
    }
  }

  public static void notEmpty(Object[] array, GlobalErrorEnum errorEnum, String message) {
    if (ObjectUtils.isEmpty(array)) {
      throw new GlobalErrorException(errorEnum, message);
    }
  }

  public static void notEmpty(Collection<?> collection, String message) {
    if (CollectionUtils.isEmpty(collection)) {
      throw new GlobalErrorException(GlobalErrorEnum.DATA_NOT_VALID.getCode(), message);
    }
  }

  public static void notEmpty(Collection<?> collection, GlobalErrorEnum errorEnum, String message) {
    if (CollectionUtils.isEmpty(collection)) {
      throw new GlobalErrorException(errorEnum, message);
    }
  }
}

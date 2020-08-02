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

package org.zh.basic.core.util;

import org.slf4j.MDC;

import org.zh.basic.common.Constant;

/**
 * <功能描述/>
 *
 */
public class SpanIdGenerator {
  public static String nextSpanId() {
    String spanId = MDC.get(Constant.MDC_SPAN_ID);
    if (spanId == null) {
      return "0";
    }
    String index = MDC.get(Constant.MDC_NEXT_INDEX) == null ? "1" : MDC.get(Constant.MDC_NEXT_INDEX);
    int idx = Integer.parseInt(index);
    MDC.put(Constant.MDC_NEXT_INDEX, String.valueOf(++idx));
    if ("0".equals(spanId)) {
      return String.format("0.%s", index);
    }

    return String.format("%s.%s", spanId, index);
  }
}

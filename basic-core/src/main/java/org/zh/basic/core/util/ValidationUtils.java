/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.util;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import org.zh.basic.common.exception.GlobalErrorEnum;
import org.zh.basic.common.exception.GlobalErrorException;

/**
 * <功能描述/>
 *
 * @author zhanghr
 * @date 2018年8月21日 下午2:30:44
 * @version 1.0
 */
public abstract class ValidationUtils {

  public static void notNull(Object value, String message) {
    if (value == null || value.toString().trim().length() == 0) {
      throw new GlobalErrorException(GlobalErrorEnum.PARAM_NOT_VALID.getCode(), message);
    }
  }

  public static void notEmpty(Map<?, ?> map, String message) {
    if (CollectionUtils.isEmpty(map)) {
      throw new GlobalErrorException(GlobalErrorEnum.PARAM_NOT_VALID.getCode(), message);
    }
  }

  public static void notEmpty(Object[] array, String message) {
    if (ObjectUtils.isEmpty(array)) {
      throw new GlobalErrorException(GlobalErrorEnum.PARAM_NOT_VALID.getCode(), message);
    }
  }

  public static void notEmpty(Collection<?> collection, String message) {
    if (CollectionUtils.isEmpty(collection)) {
      throw new GlobalErrorException(GlobalErrorEnum.PARAM_NOT_VALID.getCode(), message);
    }
  }

  public static void max(Object value, Integer max, String message) {
    if (value == null || (max != null && value.toString().trim().length() > max)) {
      throw new GlobalErrorException(GlobalErrorEnum.PARAM_NOT_VALID.getCode(), message);
    }
  }

  public static void min(Object value, Integer min, String message) {
    if (value == null || (min != null && value.toString().trim().length() < min)) {
      throw new GlobalErrorException(GlobalErrorEnum.PARAM_NOT_VALID.getCode(), message);
    }
  }

  public static void length(Object value, Integer min, Integer max, String message) {
    min(value, min, message);
    max(value, max, message);
  }

  public static void regexp(Object value, String expression, String message) {
    if (value == null || !Pattern.matches(expression, value.toString())) {
      throw new GlobalErrorException(GlobalErrorEnum.PARAM_NOT_VALID.getCode(), message);
    }
  }

  public static void email(Object value, String message) {
    regexp(value, "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*", message);
  }

  public static void mobilePhone(Object value, String message) {
    regexp(value, "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\\\d{8}$", message);
  }

  public static void idcard(Object value, String message) {
    regexp(value,
        "(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$)",
        message);
  }

}

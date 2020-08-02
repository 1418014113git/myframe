/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.advice;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * <功能描述/>
 */
public class RequestMapArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    // return false;
    return Map.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

    Class<?> paramType = parameter.getParameterType();

    Map<String, String[]> parameterMap = webRequest.getParameterMap();
    if (MultiValueMap.class.isAssignableFrom(paramType)) {
      MultiValueMap<String, String> result = new LinkedMultiValueMap<String, String>(parameterMap.size());
      for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
        for (String value : entry.getValue()) {
          result.add(entry.getKey(), value);
        }
      }
      return result;
    } else {
      Map<String, String> result = new LinkedHashMap<String, String>(parameterMap.size());
      for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
        if (entry.getValue().length > 0) {
          result.put(entry.getKey(), entry.getValue()[0]);
        }
      }
      return result;
    }
  }
}

/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.advice;

import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;

import org.zh.basic.common.Result;

/**
 * <功能描述/>
 *
 * @author kaven
 * @date 2018年7月11日 上午11:34:37
 * @version 1.0
 */
@ControllerAdvice
public class GsonResponseBodyAdvice extends AbstractResponseBodyAdvice<Object> implements Ordered {

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return GsonHttpMessageConverter.class.isAssignableFrom(converterType);
  }

  @Override
  protected Object beforeBodyWriteInternal(Object body, MethodParameter returnType, MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
      ServerHttpResponse response) {
    if (!(body instanceof Result) || body == null) {
      return Result.ok(body);
    }
    return body;
  }

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE - 50;
  }

}

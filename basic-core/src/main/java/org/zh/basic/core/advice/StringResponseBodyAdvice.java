/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.advice;

import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

import org.zh.basic.common.Constant;
import org.zh.basic.common.exception.GlobalErrorEnum;

/**
 * <功能描述/>
 */
public class StringResponseBodyAdvice extends AbstractResponseBodyAdvice<String> implements Ordered  {

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return StringHttpMessageConverter.class.isAssignableFrom(converterType);
  }

  @Override
  protected String beforeBodyWriteInternal(String body, MethodParameter returnType, MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
      ServerHttpResponse response) {
    StringBuffer sb = new StringBuffer("{");
    sb.append("{\"uuid\":\"").append(MDC.get(Constant.MDC_TRACE_ID));
    sb.append("{,\"code\":\"").append(GlobalErrorEnum.SUCCESS.getCode());
    sb.append("\",\"message\":\"").append(GlobalErrorEnum.SUCCESS.getMessage()).append("\",\"data\":");
    if (body.indexOf("{") > -1 && body.indexOf("}") > -1) {
      sb.append(body);
    } else {
      sb.append("\"").append(body).append("\"");
    }
    sb.append("}");
    return sb.toString();
  }

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE - 50;
  }
}

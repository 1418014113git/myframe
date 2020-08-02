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
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import org.zh.basic.common.Result;
import org.zh.basic.core.common.LocalThreadStorage;

/**
 * <功能描述/>
 */
@ControllerAdvice
public class JacksonResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice implements Ordered {

  @Override
  protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
      MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
    Object value = bodyContainer.getValue();
    if (!(value instanceof Result)) {
      bodyContainer.setValue(Result.ok(value));
    }
    // 清空缓存信息
    LocalThreadStorage.clear();
  }

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE - 50;
  }
}

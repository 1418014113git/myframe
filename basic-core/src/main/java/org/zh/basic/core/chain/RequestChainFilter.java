/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */


package org.zh.basic.core.chain;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import org.zh.basic.common.Constant;
import org.zh.basic.core.util.IpAddressUtils;
import org.zh.basic.core.util.SpanIdGenerator;

/*
拦截器
 */
/**
 * <功能描述/>
 */
public class RequestChainFilter extends OncePerRequestFilter {

  private String[] args;

  public RequestChainFilter(String... args) {
    this.args = args;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String traceId = request.getHeader(Constant.X_TRACE_ID);
      String spanId = request.getHeader(Constant.X_SPAN_ID);
      if (StringUtils.isEmpty(traceId)) {
        traceId = java.util.UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        spanId = SpanIdGenerator.nextSpanId();
      }

      MDC.put(Constant.MDC_TRACE_ID, traceId);
      MDC.put(Constant.MDC_SPAN_ID, spanId);
      MDC.put(Constant.MDC_CLIENT_IP, IpAddressUtils.getClientIpAddr(request));
      MDC.put(Constant.MDC_HOST_NAME, IpAddressUtils.getHostName());
      checkParam(request);
      filterChain.doFilter(request, response);
    } finally {
      MDC.clear();
    }
  }

  private void checkParam(HttpServletRequest request) {
    for (String param : args) {
      String value = request.getHeader(param);
      if (StringUtils.isEmpty(value)) {
        value = request.getParameter(param);
      }
      if (!StringUtils.isEmpty(value)) {
        MDC.put(param, value);
      }
    }
  }
}

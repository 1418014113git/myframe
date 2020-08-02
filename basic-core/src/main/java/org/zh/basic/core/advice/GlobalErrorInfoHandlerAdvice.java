/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.advice;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import org.zh.basic.common.Result;
import org.zh.basic.common.exception.GlobalErrorEnum;
import org.zh.basic.common.exception.GlobalErrorException;


/**
 * 统一处理异常.
 */
@ControllerAdvice
public class GlobalErrorInfoHandlerAdvice implements Ordered {
  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorInfoHandlerAdvice.class);

  @ExceptionHandler(value = GlobalErrorException.class)
  @ResponseBody
  public Result errorHandlerOverJson(HttpServletRequest request, GlobalErrorException exception) {
    String method = request.getMethod();
    String uri = request.getRequestURI();
    String queryString = request.getQueryString();
    
    LOGGER.error("Error occured , method: {}, URI: {}, params: {}", method, uri, queryString, exception);
    return Result.fail(exception.getCode(), exception.getMessage());
  }

  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public Result errorHandlerOverJson(HttpServletRequest request, Exception exception) {

    String msg = exception.getMessage();
    String method = request.getMethod();
    String uri = request.getRequestURI();
    String queryString = request.getQueryString();
    LOGGER.error("Error occured , method: {}, URI: {}, params: {}", method, uri, queryString, exception);

    if (!StringUtils.isEmpty(msg))
      return Result.fail(GlobalErrorEnum.UNKNOW.getCode(), msg);

    return Result.fail(GlobalErrorEnum.UNKNOW);
  }

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE - 50;
  }

}

/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */


package org.zh.basic.core.advice;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.zh.basic.common.Result;
import org.zh.basic.common.exception.GlobalErrorEnum;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class BasicErrorController extends AbstractErrorController {
  private static final Logger LOGGER = LoggerFactory.getLogger(BasicErrorController.class);
  private final ErrorProperties errorProperties;

  public BasicErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
    this(errorAttributes, errorProperties, Collections.<ErrorViewResolver>emptyList());
  }

  public BasicErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties,
      List<ErrorViewResolver> errorViewResolvers) {
    super(errorAttributes, errorViewResolvers);
    Assert.notNull(errorProperties, "ErrorProperties must not be null");
    this.errorProperties = errorProperties;
  }

  @Override
  public String getErrorPath() {
    return this.errorProperties.getPath();
  }

  // @RequestMapping(produces = "text/html")
  // public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
  // HttpStatus status = getStatus(request);
  // Map<String, Object> model = Collections
  // .unmodifiableMap(getErrorAttributes(request, isIncludeStackTrace(request,
  // MediaType.TEXT_HTML)));
  // response.setStatus(status.value());
  // ModelAndView modelAndView = resolveErrorView(request, response, status, model);
  // return modelAndView == null ? new ModelAndView("4xx.html", model) : modelAndView;
  // }

  /**
   * 默认错误返回处理.
   * 
   * @param request http request
   * @return ResponseEntity
   */
  @RequestMapping
  @ResponseBody
  public ResponseEntity<Result> error(HttpServletRequest request) {
    Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
    LOGGER.error(body.toString());
    String errMessage = (String) body.get("error");
    HttpStatus status = getStatus(request);
    return new ResponseEntity<Result>(Result.fail(GlobalErrorEnum.ERROR.getCode(), errMessage), status);
  }

  /**
   * Determine if the stacktrace attribute should be included.
   *
   * @param request the source request
   * @param produces the media type produced (or {@code MediaType.ALL})
   * @return if the stacktrace attribute should be included
   */
  protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
    ErrorProperties.IncludeStacktrace include = getErrorProperties().getIncludeStacktrace();
    if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
      return true;
    }
    if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
      return getTraceParameter(request);
    }
    return false;
  }

  /**
   * Provide access to the error properties.
   *
   * @return the error properties
   */
  protected ErrorProperties getErrorProperties() {
    return this.errorProperties;
  }
}


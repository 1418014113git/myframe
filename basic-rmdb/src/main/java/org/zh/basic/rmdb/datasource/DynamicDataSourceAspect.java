/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.rmdb.datasource;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



/**
 * 切换数据源Advice.
 *
 * @author kaven
 * @date 2018年6月13日 上午11:02:09
 * @version 1.0
 */

@Aspect
@Order(-1) // 保证该AOP在@Transactional之前执行
@Component
public class DynamicDataSourceAspect {
  private final static Logger LOGGER = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

  @Pointcut("execution(* org.zh.basic.core.service.IBaseService.*(..))")
  public void changeDataSource() {

  }

  @Before("changeDataSource()")
  public void changeDataSource(JoinPoint point) throws Throwable {
    ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    String dsId = null;
    if (sra != null && sra.getRequest() != null) {
      HttpServletRequest request = sra.getRequest();
      dsId = request.getHeader("ds");
    }
    if (StringUtils.isEmpty(dsId)) {
      Signature signature = point.getSignature();
      MethodSignature methodSignature = (MethodSignature) signature;
      Method method = methodSignature.getMethod();
      if (method.isAnnotationPresent(TargetDataSource.class)) {
        TargetDataSource ds = method.getAnnotation(TargetDataSource.class);
        dsId = ds.value();
      }
    }
    if (!DynamicDataSourceContextHolder.containsDataSource(dsId)) {
      LOGGER.warn("DataSource[{}] is not existed, change to the default DataSource --> dataSource", dsId);
    } else {
      LOGGER.warn("change the default DataSource: dataSource --> {}", dsId);
      DynamicDataSourceContextHolder.setDataSourceType(dsId);
    }
  }

  @After("changeDataSource()")
  public void restoreDataSource(JoinPoint point) {
    DynamicDataSourceContextHolder.clearDataSourceType();
  }
}

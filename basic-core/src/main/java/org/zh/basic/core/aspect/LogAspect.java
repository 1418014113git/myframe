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

package org.zh.basic.core.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import org.zh.basic.common.exception.GlobalErrorEnum;
import org.zh.basic.common.exception.GlobalErrorException;
//aop
@Aspect
@Component
@Order(1)
public class LogAspect {
  private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

  @Pointcut("execution(* org.zh..*Controller.*(..))")
  public void printLog() {

  }

  @Around("printLog()")
  private Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    long startTime = System.currentTimeMillis();
    RequestAttributes ra = RequestContextHolder.getRequestAttributes();
    ServletRequestAttributes sra = (ServletRequestAttributes) ra;
    HttpServletRequest request = sra.getRequest();

    // 获取请求相关信息
    String url = request.getRequestURL().toString();
    String method = request.getMethod();
    Object[] queryString = pjp.getArgs();

    // 获取调用方法和类
    Signature signature = pjp.getSignature();
    String className = signature.getDeclaringTypeName();
    String methodName = signature.getName();
    LOGGER.info("Request start,{}#{}(), method: {}, URL: {}, params: {}", className, methodName, method, url,
        queryString);
    try {
      Object result = pjp.proceed();
      long endTime = System.currentTimeMillis();
      LOGGER.info("Request end, {}#{}(), method: {}, URL: {}, time: {}ms, result: {} ", className, methodName, method,
          url, (endTime - startTime), result);
      return result;
    } catch (Exception e) {
      long endTime = System.currentTimeMillis();
      LOGGER.error("Request error, {}#{}(), method: {}, URL: {}, time: {}ms", className, methodName, method, url,
          (endTime - startTime), e);
      if (e instanceof GlobalErrorException) {
        throw e;
      } else {
        throw new GlobalErrorException(GlobalErrorEnum.CONTROLER_ERROR, e);
      }
    }
  }
}

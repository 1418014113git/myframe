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

package org.zh.basic.common;

public abstract class Constant {

  /**
   * 请求别名.
   */
  public static final String CONTROLLER_ALIAS = "alias";
  /**
   * 主键自增长
   */
  public static final String CONTROLLER_AUTO_INCREMENT = "primary_key_auto_increment";

  /**
   * 请求方法名.
   */
  public static final String CONTROLLER_METHOD = "controller_method";
  public static final String CONTROLLER_METHOD_GET_ONE = "GET_ONE";
  public static final String CONTROLLER_METHOD_GET_LIST = "GET_LIST";
  public static final String CONTROLLER_METHOD_UPDATE_ONE = "UPDATE_ONE";
  public static final String CONTROLLER_METHOD_UPDATE_LIST = "UPDATE_LIST";
  public static final String CONTROLLER_METHOD_INSERT_ONE = "INSERT_ONE";
  public static final String CONTROLLER_METHOD_INSERT_LIST = "INSERT_LIST";
  public static final String CONTROLLER_METHOD_DELETE_ONE = "DELETE_ONE";
  public static final String CONTROLLER_METHOD_DELETE_LIST = "DELETE_LIST";

  public static final String OPERATOR_SAVE = "Save";
  public static final String OPERATOR_REMOVE = "Remove";
  public static final String OPERATOR_QUERY = "Query";
  public static final String OPERATOR_UPDATE = "Update";

  /**
   * 序列固定
   */
  public static final String ALIAS_SEQUENCE = "SEQUENCE";

  /**
   * 分页信息
   */
  public static final String CONTROLLER_PAGE = "controller_page";
  public static final String CONTROLLER_PAGE_CURPAGE = "page_curpage";
  public static final String CONTROLLER_PAGE_PAGESIZE = "page_pagesize";
  public static final String CONTROLLER_PAGE_TOTALCOUNT = "page_totalcount";



  /**
   * 操作类型.
   */
  public static final String OPERATOR_TYPE_DB = "RMDB";
  public static final String OPERATOR_TYPE_MONGO = "MONGO";
  public static final String OPERATOR_TYPE_URL = "URL";
  /**
   * 操作语句类型.
   */
  public static final String OPERATOR_VALUE_TYPE_SQL = "0";// SQL语句
  public static final String OPERATOR_VALUE_TYPE_PROCDURE = "1";// 存储过程
  public static final String OPERATOR_VALUE_TYPE_JSON = "2";// JSON语句
  /**
   * 会话ID.
   */
  public static final String MDC_TRACE_ID = "traceId";
  public static final String MDC_SPAN_ID = "spanId";
  public static final String MDC_NEXT_INDEX = "nextIndex";
  public static final String MDC_CLIENT_IP = "clientIp";
  public static final String MDC_HOST_NAME = "hostName";
  public static final String MDC_USER_NAME = "userName";

  public static final String X_TRACE_ID = "X-Trace-Id";
  public static final String X_SPAN_ID = "X-Span-Id";

  /**
   * 组合URL和请求方式
   * 
   * @param alias
   * @param method
   * @return
   */
  public static String getOperatorConfig(String alias, String method) {
    return String.format("%s-%s", alias, method).toUpperCase();
  }

  /**
   * 获取自定义Handler的名称
   * 
   * @param alias
   * @param operator
   * @return
   */
  public static String getHandlerBeanName(String alias, String operator) {
    return String.format("%s%sHandler", alias, operator);
  }

}

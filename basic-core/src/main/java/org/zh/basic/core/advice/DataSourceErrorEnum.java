
package org.zh.basic.core.advice;

import org.zh.basic.common.exception.ErrorInfoInterface;

public enum DataSourceErrorEnum implements ErrorInfoInterface {

  /**
   * 通用成功返回码.
   */
  DS_CONFIG_NOFOUND("60001", "The datasource config of [%s] is not found"),
  OPERATE_CREATE_ERROR("6002",
      "Initialization table[db_operator] error");
  private String code;
  private String message;
  DataSourceErrorEnum(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return this.code;
  }

  public String getMessage() {
    return this.message;
  }

}

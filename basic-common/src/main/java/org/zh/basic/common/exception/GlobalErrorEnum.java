/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.common.exception;

/**
 * 统一定义异常编码类.
 *
 * @author kaven
 * @date 2018年6月12日 下午3:12:37
 * @version 1.0
 */
public enum GlobalErrorEnum implements ErrorInfoInterface {

  /**
   * 通用成功返回码.
   */
  SUCCESS("000000", "OK"),
  /**
   * 通用成功返回码.
   */
  LOGINED("000000", "Login OK"),
  UNLOGIN("000001", "A wrong input"),
  UNAUTHORIZED("000002","Authorized failed"),
  EXPIRED("000003", "Token expired"),
  /**
   * 通用错误返回码.
   */
  PARAM_NOT_VALID("999886", "Invalid Parameter"),
  /**
   * 通用错误返回码.
   */
  ID_NOT_VALID("999887", "Invalid Primary key of [%]"),
  /**
   * 通用错误返回码.
   */
  DATA_NOT_VALID("999888", "Invalid"),
  /**
   * 通用错误返回码.
   */
  SQL_ERROR("999901", "Execute SQL exception"),
  CONTROLER_ERROR("999902", "Program exception"),
  /**
   * 通用错误返回码.
   */
  OPERATE_CFG_ERROR("999994", "The action config of [%s] is wrong"),
  /**
   * 通用错误返回码.
   */
  OPERATE_NO_FOUND("999995", "The action of [%s] is not found"),
  /**
   * 通用错误返回码.
   */
  OPERATE_NO_SUPPORT("999996", "The action of [%s] is not supported"),
  /**
   * 通用错误返回码.
   */
  UNSUPPORT("999997", "Not support"),
  /**
   * 通用错误返回码.
   */
  UNKNOW("999998", "Unknow error occured"),
  /**
   * 通用错误返回码.
   */
  ERROR("999999", "Internal Server Error");


  private String code;

  private String message;

  GlobalErrorEnum(String code, String message) {
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

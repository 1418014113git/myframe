/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.zh.basic.common;

import org.slf4j.MDC;

import org.zh.basic.common.exception.ErrorInfoInterface;
import org.zh.basic.common.exception.GlobalErrorEnum;

/**
 * 接口返回结果类.
 */
public class Result {

  private boolean success;
  private Object code;
  private String message;
  private String uuid;
  private Object data;

  protected Result(Object code, String message, Object data) {
    super();
    this.code = code;
    this.message = message;
    this.data = data;
    this.uuid = MDC.get(Constant.MDC_TRACE_ID);
    if (GlobalErrorEnum.SUCCESS.getCode().equals(this.code)) {
      this.success = true;
    }
  }

  protected Result(Object code, String message) {
    this(code, message, null);
  }

  public Object getData() {
    return data;
  }

  protected void setData(Object data) {
    this.data = data;
  }

  public Object getCode() {
    return code;
  }

  protected void setCode(Object code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  protected void setMessage(String message) {
    this.message = message;
  }

  public boolean isSuccess() {
    return success;
  }

  protected void setSuccess(boolean isSuccess) {
    this.success = isSuccess;
  }

  /**
   * 创建返回结果.
   * 
   * @param code 返回码
   * @param message 返回信息
   * @param data 返回数据
   * @return Result
   */
  public static Result create(Object code, String message, Object data) {
    Result ok = new Result(code, message, data);
    return ok;
  }

  /**
   * 创建返回成功结果.
   * 
   * @param data 数据
   * @return Result
   */
  public static Result ok(Object data) {
    Result ok = new Result(GlobalErrorEnum.SUCCESS.getCode(), GlobalErrorEnum.SUCCESS.getMessage(), data);
    return ok;
  }

  /**
   * 返回错误结果.
   * 
   * @param code 返回码
   * @param errMessage 错误信息
   * @return Result
   */
  public static Result fail(Object code, String errMessage) {
    Result ok = new Result(code, errMessage);
    return ok;
  }

  /**
   * 返回错误结果.
   * @return Result
   */
  public static Result fail(ErrorInfoInterface error) {
    Result ok = new Result(error.getCode(), error.getMessage());
    return ok;
  }

  /**
   * 返回默认错误结果.
   * 
   * @return Result
   */
  public static Result fail() {
    return fail(GlobalErrorEnum.ERROR);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Result [code=");
    builder.append(code);
    builder.append(", message=");
    builder.append(message);
    builder.append(", uuid=");
    builder.append(uuid);
    builder.append(", data=");
    builder.append(data);
    builder.append(", success=");
    builder.append(success);
    builder.append("]");
    return builder.toString();
  }
}

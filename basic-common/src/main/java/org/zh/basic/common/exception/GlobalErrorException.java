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
 * 统一全局异常类.

 */
public class GlobalErrorException extends RuntimeException {

  private static final long serialVersionUID = -5628631565003779139L;

  private Object code;

  private String message;

  public GlobalErrorException(ErrorInfoInterface errorInfo, Object... args) {
    super(String.format(errorInfo.getMessage(), args), null);
    this.code = errorInfo.getCode();
    this.message = String.format(errorInfo.getMessage(), args);
  }

  public GlobalErrorException(ErrorInfoInterface errorInfo, Throwable cause, Object... args) {
    super(String.format(errorInfo.getMessage(), args), cause);
    this.code = errorInfo.getCode();
    this.message = String.format(errorInfo.getMessage(), args);
  }

  public GlobalErrorException(Object code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
    this.message = message;
  }

  public GlobalErrorException(Object code, String message) {
    this(code, message, null);
  }

  public Object getCode() {
    return this.code;
  }

  public String getMessage() {
    return this.message;
  }
}

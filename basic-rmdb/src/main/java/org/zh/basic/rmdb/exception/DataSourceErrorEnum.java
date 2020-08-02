/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.rmdb.exception;

import org.zh.basic.common.exception.ErrorInfoInterface;

/**
 * <功能描述/>
 *
 * @author kaven
 * @date 2018年6月13日 上午11:02:09
 * @version 1.0
 */
public enum DataSourceErrorEnum implements ErrorInfoInterface {
  /**
   * 通用成功返回码.
   */
  DS_CONFIG_NOFOUND("60001", "The datasource config of [%s] is not found");


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

/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.opt;

/**
 * <功能描述/>
 *
 * @author zhanghr
 * @date 2018年8月21日 下午2:59:01
 * @version 1.0
 */
public class OperatorRuleItem implements Comparable<OperatorRuleItem> {

  private Boolean required;
  private Integer min;
  private Integer max;
  private String pattern;
  private String message;

  public Boolean getRequired() {
    return required;
  }

  public void setRequired(Boolean required) {
    this.required = required;
  }

  public Integer getMin() {
    return min;
  }

  public void setMin(Integer min) {
    this.min = min;
  }

  public Integer getMax() {
    return max;
  }

  public void setMax(Integer max) {
    this.max = max;
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public int compareTo(OperatorRuleItem o) {
    if (o == null) {
      return -1;
    }
    if (getRequired() != null && getRequired()) {
      return -1;
    }
    if (o.getRequired() != null && o.getRequired()) {
      return 1;
    }
    return 0;
  }
}

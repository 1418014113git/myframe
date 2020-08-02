/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.opt;

import java.util.List;
import java.util.Map;

/**
 * <功能描述/>
 *
 * @author kaven
 * @date 2018年6月14日 上午10:03:59
 * @version 1.0
 */
public class OperatorConfig {

  private String statement;
  private String optType;
  private String optStatementType;
  private String optTarget;
  private String optPk;
  private String optRules;
  private String beforeOpt;
  private String optValue;
  private String optValueType;
  private String afterOpt;
  private String optDesc;

  private Map<String, List<OperatorRuleItem>> rules;

  public String getStatement() {
    return statement;
  }

  public void setStatement(String statement) {
    this.statement = statement;
  }

  public String getOptType() {
    return optType;
  }

  public void setOptType(String optType) {
    this.optType = optType;
  }

  public String getOptStatementType() {
    return optStatementType;
  }

  public void setOptStatementType(String optStatementType) {
    this.optStatementType = optStatementType;
  }

  public String getOptPk() {
    return optPk;
  }

  public void setOptPk(String optPk) {
    this.optPk = optPk;
  }

  public String getBeforeOpt() {
    return beforeOpt;
  }

  public void setBeforeOpt(String beforeOpt) {
    this.beforeOpt = beforeOpt;
  }

  public String getOptValue() {
    return optValue;
  }

  public void setOptValue(String optValue) {
    this.optValue = optValue;
  }

  public String getOptValueType() {
    return optValueType;
  }

  public void setOptValueType(String optValueType) {
    this.optValueType = optValueType;
  }

  public String getAfterOpt() {
    return afterOpt;
  }

  public void setAfterOpt(String afterOpt) {
    this.afterOpt = afterOpt;
  }

  public String getOptDesc() {
    return optDesc;
  }

  public void setOptDesc(String optDesc) {
    this.optDesc = optDesc;
  }

  public String getOptTarget() {
    return optTarget;
  }

  public void setOptTarget(String optTarget) {
    this.optTarget = optTarget;
  }

  public String getOptRules() {
    return optRules;
  }

  public void setOptRules(String optRules) {
    this.optRules = optRules;
  }

  public Map<String, List<OperatorRuleItem>> getRules() {
    return rules;
  }

  public void setRules(Map<String, List<OperatorRuleItem>> rules) {
    this.rules = rules;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("OperatorConfig [statement=");
    builder.append(statement);
    builder.append(", optType=");
    builder.append(optType);
    builder.append(", optStatementType=");
    builder.append(optStatementType);
    builder.append(", optTarget=");
    builder.append(optTarget);
    builder.append(", optPk=");
    builder.append(optPk);
    builder.append(", beforeOpt=");
    builder.append(beforeOpt);
    builder.append(", optValue=");
    builder.append(optValue);
    builder.append(", afterOpt=");
    builder.append(afterOpt);
    builder.append(", optDesc=");
    builder.append(optDesc);
    builder.append("]");
    return builder.toString();
  }
}

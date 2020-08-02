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
 * @author kaven
 * @date 2018年6月13日 下午12:53:59
 * @version 1.0
 */
public class OperatorConfigSql {
  private String optName;// 操作名称
  private String optModel;// 操作模块
  private String optMethod;// 操作方式
  private String optSql;// 执行的sql语句
  private String optSqlType;// 执行的sql语句类型
  private String upTime;// 更新时间
  private String optPk;// 操作主键
  private String optValue;// 操作值
  private String optDesc;// 描述

  public String getOptName() {
    return optName;
  }

  public void setOptName(String optName) {
    this.optName = optName;
  }

  public String getOptModel() {
    return optModel;
  }

  public void setOptModel(String optModel) {
    this.optModel = optModel;
  }

  public String getOptMethod() {
    return optMethod;
  }

  public void setOptMethod(String optMethod) {
    this.optMethod = optMethod;
  }

  public String getOptSql() {
    return optSql;
  }

  public void setOptSql(String optSql) {
    this.optSql = optSql;
  }

  public String getOptSqlType() {
    return optSqlType;
  }

  public void setOptSqlType(String optSqlType) {
    this.optSqlType = optSqlType;
  }

  public String getUpTime() {
    return upTime;
  }

  public void setUpTime(String upTime) {
    this.upTime = upTime;
  }

  public String getOptPk() {
    return optPk;
  }

  public void setOptPk(String optPk) {
    this.optPk = optPk;
  }

  public String getOptValue() {
    return optValue;
  }

  public void setOptValue(String optValue) {
    this.optValue = optValue;
  }

  public String getOptDesc() {
    return optDesc;
  }

  public void setOptDesc(String optDesc) {
    this.optDesc = optDesc;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("OperatorConfig [optName=");
    builder.append(optName);
    builder.append(", optModel=");
    builder.append(optModel);
    builder.append(", optMethod=");
    builder.append(optMethod);
    builder.append(", optSql=");
    builder.append(optSql);
    builder.append(", optSqlType=");
    builder.append(optSqlType);
    builder.append(", upTime=");
    builder.append(upTime);
    builder.append(", optPk=");
    builder.append(optPk);
    builder.append(", optValue=");
    builder.append(optValue);
    builder.append(", optDesc=");
    builder.append(optDesc);
    builder.append(']');
    return builder.toString();
  }



}

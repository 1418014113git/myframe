/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.rmdb.mode;

/**
 * <功能描述/>
 *
 * @author kaven
 * @date 2018年6月13日 上午11:53:26
 * @version 1.0
 */
public class Htable {
  private Integer tableId;
  private String tableAlias;
  private String realTableName;
  private String filterColumn;
  private String filterColumnValue;
  private String tableDesc;

  public Integer getTableId() {
    return tableId;
  }

  public void setTableId(int tableId) {
    this.tableId = tableId;
  }

  public String getTableAlias() {
    return tableAlias;
  }

  public void setTableAlias(String tableAlias) {
    this.tableAlias = tableAlias;
  }

  public String getRealTableName() {
    return realTableName;
  }

  public void setRealTableName(String realTableName) {
    this.realTableName = realTableName;
  }

  public String getFilterColumn() {
    return filterColumn;
  }

  public void setFilterColumn(String filterColumn) {
    this.filterColumn = filterColumn;
  }

  public String getFilterColumnValue() {
    return filterColumnValue;
  }

  public void setFilterColumnValue(String filterColumnValue) {
    this.filterColumnValue = filterColumnValue;
  }

  public String getTableDesc() {
    return tableDesc;
  }

  public void setTableDesc(String tableDesc) {
    this.tableDesc = tableDesc;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Htable [tableId=");
    builder.append(tableId);
    builder.append(", tableAlias=");
    builder.append(tableAlias);
    builder.append(", realTableName=");
    builder.append(realTableName);
    builder.append(", filterColumn=");
    builder.append(filterColumn);
    builder.append(", filterColumnValue=");
    builder.append(filterColumnValue);
    builder.append(", tableDesc=");
    builder.append(tableDesc);
    builder.append("]");
    return builder.toString();
  }
}

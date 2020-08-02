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
public class Hcolumn {

  private Integer columnId;
  private String columnName;
  private String comumnAlias;
  private String jdbcType;
  private String columnNick;
  private Integer tableId;
  private boolean pk;
  private boolean showable;
  private boolean editable;
  private boolean addable;
  private boolean nullabel;
  private boolean autoIncrement;
  private Integer sequence;
  private String comments;

  public boolean isAutoIncrement() {
    return autoIncrement;
  }

  public void setAutoIncrement(boolean autoIncrement) {
    this.autoIncrement = autoIncrement;
  }

  public boolean isNullabel() {
    return nullabel;
  }

  public void setNullabel(boolean nullabel) {
    this.nullabel = nullabel;
  }

  public Integer getColumnId() {
    return columnId;
  }

  public void setColumnId(Integer columnId) {
    this.columnId = columnId;
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public String getComumnAlias() {
    return comumnAlias;
  }

  public void setComumnAlias(String comumnAlias) {
    this.comumnAlias = comumnAlias;
  }

  public String getJdbcType() {
    return jdbcType;
  }

  public void setJdbcType(String jdbcType) {
    this.jdbcType = jdbcType;
  }

  public String getColumnNick() {
    return columnNick;
  }

  public void setColumnNick(String columnNick) {
    this.columnNick = columnNick;
  }

  public Integer getTableId() {
    return tableId;
  }

  public void setTableId(int tableId) {
    this.tableId = tableId;
  }

  public boolean isPk() {
    return pk;
  }

  public void setPk(boolean pk) {
    this.pk = pk;
  }

  public boolean isShowable() {
    return showable;
  }

  public void setShowable(boolean showable) {
    this.showable = showable;
  }

  public boolean isEditable() {
    return editable;
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  public boolean isAddable() {
    return addable;
  }

  public void setAddable(boolean addable) {
    this.addable = addable;
  }

  public Integer getSequence() {
    return sequence;
  }

  public void setSequence(Integer sequence) {
    this.sequence = sequence;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Hcolumn [columnId=");
    builder.append(columnId);
    builder.append(", columnName=");
    builder.append(columnName);
    builder.append(", comumnAlias=");
    builder.append(comumnAlias);
    builder.append(", jdbcType=");
    builder.append(jdbcType);
    builder.append(", columnNick=");
    builder.append(columnNick);
    builder.append(", tableId=");
    builder.append(tableId);
    builder.append(", pk=");
    builder.append(pk);
    builder.append(", showable=");
    builder.append(showable);
    builder.append(", editable=");
    builder.append(editable);
    builder.append(", addable=");
    builder.append(addable);
    builder.append(", sequence=");
    builder.append(sequence);
    builder.append(", comments=");
    builder.append(comments);
    builder.append("]");
    return builder.toString();
  }
}

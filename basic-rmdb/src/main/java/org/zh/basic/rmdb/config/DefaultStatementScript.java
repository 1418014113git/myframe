/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.rmdb.config;

/**
 * <功能描述/>
 *
 * @author kaven
 * @date 2018年6月13日 上午11:53:26
 * @version 1.0
 */
public interface DefaultStatementScript {
  public static final String DBTABLE_GET_LIST = "DBTABLE.LIST";
  public static final String DBTABLE_GET_LIST_SCRIPT = "SELECT " + "table_id," + "table_alias," + "real_table_name,"
      + "filter_column," + "filter_value," + "table_desc" + " FROM " + "db_table";

  public static final String DBCOLUMN_GET_LIST = "DBCOLUMN.LIST";
  public static final String DBCOLUMN_GET_LIST_SCRIPT = "SELECT\r\n" + "column_id,\r\n" + "column_name,\r\n"
      + "column_alias,\r\n" + "jdbc_type,\r\n" + "column_nick,\r\n" + "table_id,\r\n" + "pk,\r\n" + "showable,\r\n"
      + "editable,\r\n" + "addable,\r\n" + "sequence,\r\n" + "comments\r\n" + "FROM\r\n" + "db_column";
  
  public static final String DBALIAS_GET_LIST = "DBALIAS.LIST";
  public static final String DBALIAS_GET_LIST_SCRIPT =
      "SELECT\r\n" + "id,\r\n" + "alias,\r\n" + "method,\r\n" + "chains\r\n" + "FROM\r\n" + "db_alias";

  public static final String DBOPERATOR_GET_LIST = "DBOPERATOR.LIST";
  public static final String DBOPERATOR_GET_LIST_SCRIPT =
      "SELECT STATEMENT, OPT_TYPE, OPT_STATEMENT_TYPE, OPT_VALUE, OPT_VALUE_TYPE,OPT_RULES, OPT_PK, BEFORE_OPT, AFTER_OPT,OPT_DESC FROM db_operator where OPT_TYPE = #{optType}";
}

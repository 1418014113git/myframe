/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.zh.basic.rmdb.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DatabaseDriver;

import org.zh.basic.rmdb.mode.Hcolumn;
import org.zh.basic.rmdb.mode.Htable;


/**
 * 数据库信息工具类>
 *
 * @author kaven
 * @date 2018年6月13日 上午14:02:09
 * @version 1.0
 */
public class DbInfoUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(DbInfoUtil.class);

  private static Field[] typesFields = Types.class.getDeclaredFields();

  /**
   * 获取数据库下所有表信息
   * 
   * @param dataSource
   * @return
   */
  public static List<Htable> getAllTables(Connection conn, DatabaseDriver dbDriver, String dbName) {
    List<Htable> list = new ArrayList<>();
    Statement statement = null;
    try {
      statement = conn.createStatement();
      ResultSet rs = getDbTablesResultSet(dbDriver, statement, dbName);
      int startTableId = -1;
      while (rs.next()) {
        Htable mapTable = new Htable();
        mapTable.setTableId(startTableId);
        String tableName = rs.getString("tableName");
        if (!tableName.startsWith("h_")) {
          mapTable.setRealTableName(tableName);
          mapTable.setTableAlias(tableName.replaceAll("_", ""));
          String describe = rs.getString("comment");
          mapTable.setTableDesc(describe);
          list.add(mapTable);
          startTableId--;
        }
      }
    } catch (Exception e) {
      LOGGER.info("DbInfoUtil getAllTableInfo Exception!", e);
    }
    return list;

  }

  /**
   * 获取指定表下所有列信息
   * 
   * @param dataSource
   * @param tableName
   * @return
   */
  public static List<Hcolumn> getAllColumns(Connection conn, DatabaseDriver dbDriver, String tableName, int tableId) {
    List<Hcolumn> result = new ArrayList<>();
    try {
      DatabaseMetaData dbMeta = conn.getMetaData();
      ResultSet rs = dbMeta.getColumns(conn.getCatalog(), getSchema(dbDriver), tableName, "%");
      Map<String, Map<String, Object>> priKeyMap = getPriKeyList(conn, dbDriver, tableName);

      while (rs.next()) {
        Hcolumn mapColumn = new Hcolumn();
        mapColumn.setTableId(tableId);
        String columnName = rs.getString("COLUMN_NAME");
        mapColumn.setColumnName(columnName);
        mapColumn.setComumnAlias(column2Field(columnName));
        String describe = rs.getString("REMARKS");
        mapColumn.setComments(describe);
        int dataType = rs.getInt("DATA_TYPE");
        mapColumn.setJdbcType(getDataType(dataType));
        setPrimaryKey(priKeyMap, columnName, mapColumn);
        int nullable = rs.getInt("NULLABLE");
        mapColumn.setNullabel(nullable == 1);
        mapColumn.setShowable(true);
        mapColumn.setAddable(true);
        mapColumn.setEditable(true);
        mapColumn.setAutoIncrement(rs.getBoolean("IS_AUTOINCREMENT"));
        result.add(mapColumn);
      }
    } catch (Exception e) {
      LOGGER.info("DbInfoUtil getAllTableInfo Exception!", e);
    }
    return result;
  }

  /**
   * 调整数据库列名转为java属性名.
   * 
   * @param colName 列名
   * @return String
   */
  public static String column2Field(String colName) {
    String[] arr = colName.split("_");
    StringBuffer sb = new StringBuffer(arr[0]);
    int size = arr.length;
    for (int index = 1; index < size; index++) {
      sb.append(upperFirstChar(arr[index].toCharArray()));
    }
    return sb.toString();
  }

  private static char[] upperFirstChar(char[] fieldName) {
    if (fieldName[0] >= 97 && fieldName[0] <= 122) {
      fieldName[0] = (char) (fieldName[0] - 32);
    }
    return fieldName;
  }

  /**
   * 获取指定表下所有主键信息
   * 
   * @param conn
   * @param dbType
   * @param tableName
   * @return
   * @throws Exception
   */
  private static Map<String, Map<String, Object>> getPriKeyList(Connection conn, DatabaseDriver dbDriver,
      String tableName) throws Exception {
    DatabaseMetaData dbMeta = conn.getMetaData();
    ResultSet rs = dbMeta.getPrimaryKeys(conn.getCatalog(), getSchema(dbDriver), tableName);
    Map<String, Map<String, Object>> result = new HashMap<>();
    while (rs.next()) {
      String primaryKey = rs.getString("COLUMN_NAME");
      Map<String, Object> map = new HashMap<>();
      map.put("columnName", primaryKey);
      result.put(primaryKey, map);
    }
    return result;
  }

  /**
   * 设定指定列主键信息
   * 
   * @param priKeyMap
   * @param columnName
   * @param map
   * @throws SQLException
   */
  private static void setPrimaryKey(Map<String, Map<String, Object>> priKeyMap, String columnName, Hcolumn map)
      throws SQLException {
    if (priKeyMap.containsKey(columnName)) {
      map.setPk(true);
    } else {
      map.setPk(false);
    }
  }

  private static String getSchema(DatabaseDriver dbDriver) throws Exception {
    String schemaPattern = null;
    switch (dbDriver) {
      case MYSQL:
      case MARIADB:
        schemaPattern = "%";
        break;
      default:
        break;
    }
    return schemaPattern;
  }

  private static String getDataType(int dataType) {
    try {
      for (Field field : typesFields) {
        if (Modifier.isStatic(field.getModifiers())) {
          int value = (int) field.get(Types.class);
          if (dataType == value) {
            return field.getName().toUpperCase();
          }
        }

      }
    } catch (Exception e) {
      LOGGER.info("DbInfoUtil getDataType Exception!", e);
    }

    return null;
  }

  private static ResultSet getDbTablesResultSet(DatabaseDriver dbDriver, Statement statement, String dbName)
      throws SQLException {
    ResultSet rs = null;
    StringBuilder sql = new StringBuilder();
    switch (dbDriver) {
      case MYSQL:
      case MARIADB:
        statement.executeQuery("USE information_schema;");
        sql.append("SELECT TABLE_NAME AS TABLENAME,TABLE_COMMENT AS COMMENT FROM TABLES WHERE TABLE_SCHEMA='")
            .append(dbName).append("';");
        rs = statement.executeQuery(sql.toString());
        break;
      default:
        break;
    }
    return rs;
  }
}

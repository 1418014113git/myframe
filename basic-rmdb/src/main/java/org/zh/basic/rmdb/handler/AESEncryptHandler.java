/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.rmdb.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import org.zh.basic.rmdb.utils.AES;

/**
 * <功能描述/>
 *
 * @author zhanghr
 * @date 2018年8月8日 下午3:53:11
 * @version 1.0
 */
@MappedJdbcTypes(JdbcType.UNDEFINED)
@MappedTypes(String.class)
public class AESEncryptHandler extends BaseTypeHandler<String> {
  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setString(i, AES.encrypt(parameter));
  }

  @Override
  public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String columnValue = rs.getString(columnName);
    return AES.decrypt(columnValue);
  }

  @Override
  public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String columnValue = rs.getString(columnIndex);
    return AES.decrypt(columnValue);
  }

  @Override
  public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    String columnValue = cs.getString(columnIndex);
    return AES.decrypt(columnValue);
  }
}

/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.zh.basic.rmdb.utils;

import java.lang.reflect.Method;

import javax.sql.DataSource;

/**
 * <功能描述/>
 *
 * @author kaven
 * @date 2018年6月13日 上午11:02:09
 * @version 1.0
 */
public class DataSourceInjectUtils {

  public static <T> void inject(DataSource dataSource, String methodName, Object value, Class<T> parameterType) {
    try {
      Method maxPoolSizeMethod = dataSource.getClass().getMethod(methodName, parameterType);
      maxPoolSizeMethod.invoke(dataSource, value);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

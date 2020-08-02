/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.rmdb.datasource;

import java.util.ArrayList;
import java.util.List;

/**
 * <功能描述/>
 *
 * @author kaven
 * @date 2018年6月13日 上午11:02:09
 * @version 1.0
 */
public class DynamicDataSourceContextHolder {
  private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
  static List<String> dataSourceIds = new ArrayList<String>();

  public static void setDataSourceType(String dataSourceType) {
    contextHolder.set(dataSourceType);
  }

  public static String getDataSourceType() {
    return contextHolder.get();
  }

  public static void clearDataSourceType() {
    contextHolder.remove();
  }

  /**
   * 判断指定DataSrouce当前是否存在
   */
  public static boolean containsDataSource(String dataSourceId) {
    return dataSourceIds.contains(dataSourceId);
  }
}

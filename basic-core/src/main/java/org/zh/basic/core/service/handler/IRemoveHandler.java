/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.service.handler;

import java.util.Map;

/**
 * <功能描述/>
 *
 * @author kaven
 * @date 2018年6月22日 下午3:25:24
 * @version 1.0
 */
public interface IRemoveHandler {
  /**
   * 删除数据.
   * 
   * @param requestMap 删除条件
   * @throws Exception 返回异常
   */
  void remove(Map<String, Object> requestMap) throws Exception;

  /**
   * 根据主键ID删除数据.
   * 
   * @param id 主键ID
   * @throws Exception 返回异常
   */
  void remove(String id) throws Exception;
}

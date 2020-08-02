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
 * @date 2018年6月22日 下午3:25:14
 * @version 1.0
 */
public interface IUpdateHandler {
  /**
   * 根据主键ID修改数据.
   * 
   * @param id 主键ID
   * @param requestMap 需要修改的数据
   * @return Object
   * @throws Exception 返回异常
   */
  Object update(String id, Map<String, Object> requestBody) throws Exception;
  /**
   * 根据主键ID修改数据.
   * 
   * @param requestParam 请求参数
   * @param requestBody 需要修改的数据
   * @return Object
   * @throws Exception 返回异常
   */
  @Deprecated
  Object update(Map<String, Object> requestParam, Map<String, Object> requestBody) throws Exception;

}

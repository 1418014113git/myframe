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
 * @date 2018年6月22日 下午3:24:38
 * @version 1.0
 */
public interface IQueryHandler {

  /**
   * 根据主键ID查询一条数据.
   * 
   * @param id 主键ID
   * @return Object
   * @throws Exception 返回异常
   */
  Object get(String id) throws Exception;
  /**
   * 查询一条数据.
   * 
   * @param requestMap 查询条件
   * @return Object
   * @throws Exception 返回异常
   */
  @Deprecated
  Object get(Map<String, Object> requestMap) throws Exception;

  /**
   * 查询集合数据.
   * 
   * @param requestMap 查询条件
   * @return Object
   * @throws Exception 返回异常
   */
  Object list(Map<String, Object> requestMap) throws Exception;

  /**
   * 分页查询集合数据.
   * 
   * @param requestMap 查询条件
   * @param currentPage 当前页
   * @param pageSize 页数
   * @return Object
   * @throws Exception 返回异常
   */
  Object page(Map<String, Object> requestMap, int currentPage, int pageSize) throws Exception;
}

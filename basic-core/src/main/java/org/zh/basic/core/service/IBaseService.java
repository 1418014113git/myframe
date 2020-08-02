/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.service;

import java.util.Map;

import org.zh.basic.core.service.handler.IQueryHandler;
import org.zh.basic.core.service.handler.IRemoveHandler;
import org.zh.basic.core.service.handler.ISaveHandler;
import org.zh.basic.core.service.handler.IUpdateHandler;

/**
 * <功能描述/>
 *
 * @author kaven
 * @date 2018年6月12日 下午3:14:11
 * @version 1.0
 */
public interface IBaseService extends ISaveHandler, IUpdateHandler, IRemoveHandler, IQueryHandler {
  
  /**
   * 查询一条数据.
   * 
   * @param requestMap 查询条件
   * @return Object
   * @throws Exception 返回异常
   */
  Object get(Map<String, Object> requestMap) throws Exception;
  
  /**
   * 查询全部数据.
   * 
   * @return Object
   * @throws Exception 返回异常
   */
  Object findAll() throws Exception;

  /**
   * 批量修改数据.
   * 
   * @param requestParam 请求参数
   * @param requestBody 需要修改的数据
   * @return Object
   * @throws Exception 返回异常
   */
  Object update(Map<String, Object> requestParam, Map<String, Object> requestBody) throws Exception;

  /**
   * 
   * @param seqName
   * @return
   * @throws Exception
   */
  public Object getSequence(String seqName) throws Exception;
}

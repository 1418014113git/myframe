/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.service.handler.impl;

import java.util.Map;

import org.zh.basic.core.service.IBaseService;
import org.zh.basic.core.service.handler.IQueryHandler;

/**
 * <功能描述/>
 *
 * @author kaven
 * @date 2018年6月22日 下午3:34:39
 * @version 1.0
 */
public abstract class AbstractQueryHandler implements IQueryHandler {
  protected IBaseService baseService;

  public AbstractQueryHandler(IBaseService baseService) {
    this.baseService = baseService;
  }

  @Override
  public Object get(String id) throws Exception {
    return baseService.get(id);
  }

  @Override
  @Deprecated
  public Object get(Map<String, Object> requestMap) throws Exception {
    return baseService.get(requestMap);
  }

  @Override
  public Object list(Map<String, Object> requestMap) throws Exception {
    return baseService.list(requestMap);
  }

  @Override
  public Object page(Map<String, Object> requestMap, int currentPage, int pageSize) throws Exception {
    return baseService.page(requestMap, currentPage, pageSize);
  }
}

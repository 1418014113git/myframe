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
import org.zh.basic.core.service.handler.IUpdateHandler;

/**
 * <功能描述/>
 *
 * @author kaven
 * @date 2018年6月22日 下午3:28:54
 * @version 1.0
 */
public abstract class AbstractUpdateHandler implements IUpdateHandler {

  protected IBaseService baseService;

  public AbstractUpdateHandler(IBaseService baseService) {
    this.baseService = baseService;
  }

  @Override
  public Object update(String id, Map<String, Object> requestBody) throws Exception {
    return baseService.update(id, requestBody);
  }
  @Override
  @Deprecated
  public Object update(Map<String, Object> requestParam, Map<String, Object> requestBody) throws Exception {
    return baseService.update(requestParam, requestBody);
  }
}

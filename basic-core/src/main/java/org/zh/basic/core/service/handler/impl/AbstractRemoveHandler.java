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
import org.zh.basic.core.service.handler.IRemoveHandler;

/**
 * <功能描述/>
 *
 * @author kaven
 * @date 2018年6月22日 下午3:37:01
 * @version 1.0
 */
public abstract class AbstractRemoveHandler implements IRemoveHandler {
  protected IBaseService baseService;

  public AbstractRemoveHandler(IBaseService baseService) {
    this.baseService = baseService;
  }

  @Override
  public void remove(Map<String, Object> requestMap) throws Exception {
    baseService.remove(requestMap);
  }

  @Override
  public void remove(String id) throws Exception {
    baseService.remove(id);
  }
}

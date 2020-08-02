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
import org.zh.basic.core.service.handler.ISaveHandler;

public abstract class AbstractSaveHandler implements ISaveHandler {
  protected IBaseService baseService;

  public AbstractSaveHandler(IBaseService baseService) {
    this.baseService = baseService;
  }

  @Override
  public Object save(Map<String, Object> requestBody) throws Exception {
    return baseService.save(requestBody);
  }
}

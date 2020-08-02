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
 * @date 2018年6月22日 下午3:22:21
 * @version 1.0
 */
public interface ISaveHandler {

  /**
   * 创建数据.
   * 
   * @param requestMap 请求数据
   * @return Object
   * @throws Exception 返回异常
   */
  Object save(Map<String, Object> requestMap) throws Exception;
}

/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.opt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * 操作上下文信息.
 *
 * @author kaven
 * @date 2018年6月13日 下午12:53:59
 * @version 1.0
 */
public class OperatorContext {

  private Object id;
  private Map<String, Object> body;
  private List<Map<String, Object>> bodies;
  private OperatorConfig preOptConfig;
  private OperatorConfig curOptConfig;
  private Map<String, Object> requestParams;
  private Map<String, Object> scopeParams = new HashMap<String, Object>();

  public OperatorContext(Object id, Map<String, Object> body, Map<String, Object> requestParams) {
    super();
    this.id = id;
    this.body = body;
    this.requestParams = requestParams == null ? new HashMap<String, Object>() : requestParams;
    if (this.body != null) {
      this.requestParams.putAll(body);
    }
  }

  public OperatorContext(List<Map<String, Object>> bodies) {
    super();
    this.bodies = bodies;
    this.requestParams = requestParams == null ? new HashMap<String, Object>() : requestParams;
  }

  public Object getId() {
    return id;
  }

  public Map<String, Object> getBody() {
    return body;
  }

  public List<Map<String, Object>> getBodies() {
    return bodies;
  }

  public OperatorConfig getCurOptConfig() {
    return curOptConfig;
  }

  public void setCurOptConfig(OperatorConfig curOptConfig) {
    this.preOptConfig = this.curOptConfig;
    this.curOptConfig = curOptConfig;
    if (!StringUtils.isEmpty(id)) {
      String pk = this.curOptConfig.getOptPk();
      getRequestParams().put(StringUtils.isEmpty(pk) ? "id" : pk, id);
    }
  }

  public OperatorConfig getPreOptConfig() {
    return preOptConfig;
  }

  public Map<String, Object> getRequestParams() {
    return requestParams;
  }

  public Map<String, Object> getScopeParams() {
    return scopeParams;
  }

}

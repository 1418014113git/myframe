/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.opt.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import org.zh.basic.core.opt.OperatorAlias;
import org.zh.basic.core.opt.OperatorConfig;


/**
 * <功能描述/>
 *
 * @author kaven
 * @date 2018年6月13日 下午12:53:59
 * @version 1.0
 */
@Component
public class OperatorManager {
  private static Map<String, OperatorAlias> mappedAliases = new ConcurrentHashMap<String, OperatorAlias>();
  private static Map<String, OperatorConfig> mappedStatemnets = new ConcurrentHashMap<String, OperatorConfig>();


  public OperatorAlias getOptAlias(String key) {
    return mappedAliases.get(key);
  }

  public OperatorConfig getOptCfg(String key) {
    return mappedStatemnets.get(key);
  }

  public Map<String, OperatorAlias> getOptAliasMap() {
    return mappedAliases;
  }

  public Map<String, OperatorConfig> getOptCfgMap() {
    return mappedStatemnets;
  }
}

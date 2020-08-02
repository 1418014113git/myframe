/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.rmdb.opt.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.zh.basic.core.opt.IBeforeOpterator;
import org.zh.basic.core.opt.OperatorContext;
import org.zh.basic.core.opt.OperatorRuleItem;
import org.zh.basic.core.util.ValidationUtils;

/**
 * <功能描述/>
 *
 * @author zhanghr
 * @date 2018年8月21日 下午2:04:01
 * @version 1.0
 */
@Component("beforeRmdbOperator")
public class BeforeRmdbOperator implements IBeforeOpterator {

  @Override
  public Object before(OperatorContext context, Object frontResult) {
    Map<String, List<OperatorRuleItem>> rules = context.getCurOptConfig().getRules();
    if (rules != null) {// 规则已经转换直接检验规则
      checkRules(context.getRequestParams(), rules);
    } else {
      String rulesJson = context.getCurOptConfig().getOptRules();
      if (!StringUtils.isEmpty(rulesJson)) {// 解析校验规则JSON
        Map<String, List<OperatorRuleItem>> newRules = parser(rulesJson);
        context.getCurOptConfig().setRules(newRules);
        checkRules(context.getRequestParams(), newRules);
      }
    }
    return null;
  }

  private Map<String, List<OperatorRuleItem>> parser(String rulesJson) {
    JSONObject jo = JSON.parseObject(rulesJson);
    Map<String, List<OperatorRuleItem>> rules = new HashMap<>();
    for (Map.Entry<String, Object> entry : jo.entrySet()) {
      List<OperatorRuleItem> ruleItems = null;
      String key = entry.getKey();
      Object value = entry.getValue();
      if (value instanceof JSONArray) {
        JSONArray ja = (JSONArray) value;
        ruleItems = JSON.parseArray(ja.toJSONString(), OperatorRuleItem.class);
        Collections.sort(ruleItems);
      }
      if (ruleItems != null && !ruleItems.isEmpty()) {
        rules.put(key, ruleItems);
      }
    }
    return rules;
  }

  private void checkRules(Map<String, Object> requestParams, Map<String, List<OperatorRuleItem>> rules) {
    for (Map.Entry<String, List<OperatorRuleItem>> entry : rules.entrySet()) {
      String key = entry.getKey();
      List<OperatorRuleItem> ruleItems = entry.getValue();
      Object value = requestParams.get(key);
      for (OperatorRuleItem ruleItem : ruleItems) {
        if (ruleItem.getRequired() != null && ruleItem.getRequired()) {
          ValidationUtils.notNull(value, ruleItem.getMessage());
        } else if (!StringUtils.isEmpty(value) && (ruleItem.getMin() != null || ruleItem.getMax() != null)) {
          ValidationUtils.length(value, ruleItem.getMin(), ruleItem.getMax(), ruleItem.getMessage());
        } else if (!StringUtils.isEmpty(value) && ruleItem.getPattern() != null) {
          ValidationUtils.regexp(value, ruleItem.getPattern(), ruleItem.getMessage());
        }
      }
    }
  }

}

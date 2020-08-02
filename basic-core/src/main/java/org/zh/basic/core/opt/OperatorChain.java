/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.opt;

import org.springframework.util.StringUtils;

import org.zh.basic.common.exception.GlobalErrorEnum;
import org.zh.basic.common.exception.GlobalErrorException;
import org.zh.basic.core.util.SpringUtils;

/**
 * <功能描述/>
 *
 * @author kaven
 * @date 2018年6月13日 下午2:18:53
 * @version 1.0
 */
public class OperatorChain implements IOperator {

  private IOperator currentOpt;
  private IOperator nextChain;
  private OperatorConfig currentCfg;

  public OperatorChain(OperatorConfig optConfig) {
    String optType = optConfig.getOptType();
    if (!StringUtils.isEmpty(optType) && SpringUtils.hasBean(optType.toUpperCase())) {
      currentOpt = SpringUtils.getBean(optType.toUpperCase(), IOperator.class);
    } else {
      throw new GlobalErrorException(GlobalErrorEnum.OPERATE_CFG_ERROR, String.format("optType:%s", optType));
    }
    this.currentCfg = optConfig;
  }

  @Override
  public Object execute(OperatorContext context, Object frontResult) {
    context.setCurOptConfig(currentCfg);
    if (nextChain == null) {
      return currentOpt.execute(context, frontResult);
    }
    return nextChain.execute(context, currentOpt.execute(context, frontResult));
  }

  public void next(IOperator nextChain) {
    this.nextChain = nextChain;
  }
}

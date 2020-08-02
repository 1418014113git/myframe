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

import org.zh.basic.core.util.SpringUtils;


/**
 * 基础操作抽象类.
 *
 * @author kaven
 * @date 2018年6月13日 下午12:53:59
 * @version 1.0
 */
public abstract class AbstractOperator implements IOperator {

  /**
   * 执行一个动作的前置处理逻辑.
   * 
   * @param context 当前动作执行的上下文信息
   * @param frontResult 前一个动作执行的结果
   * @return String 当前动作前置处理的结果
   */
  protected Object before(OperatorContext context, Object frontResult) {

    String beforeOpt = context.getCurOptConfig().getBeforeOpt();
    if (StringUtils.isEmpty(beforeOpt)) {
      beforeOpt = String.format("before%s", this.getClass().getSimpleName());
    }
    if (!SpringUtils.hasBean(beforeOpt)) {
      return build(context, frontResult);
    }
    IBeforeOpterator beforeOperator = SpringUtils.getBean(beforeOpt, IBeforeOpterator.class);
    return beforeOperator.before(context, frontResult);
  }

  /**
   * 当前动作的处理逻辑.
   * 
   * @param context 当前动作执行的上下文信息
   * @param requestResult 当前动作前置处理的结果
   * @return String 当前动作处理的结果
   */
  protected abstract Object running(OperatorContext context, Object requestResult);

  /**
   * 执行一个动作的后置处理逻辑.
   * 
   * @param context 当前动作执行的上下文信息
   * @param frontResult 当前动作执行的结果
   * @return String 当前动作后置处理的结果
   */
  protected Object after(OperatorContext context, Object result) {

    String afterOpt = context.getCurOptConfig().getAfterOpt();
    if (StringUtils.isEmpty(afterOpt)) {
      afterOpt = String.format("after%s", this.getClass().getSimpleName());
    }
    if (!SpringUtils.hasBean(afterOpt)) {
      return build(context, result);
    }


    IAfterOpterator afterOperator = SpringUtils.getBean(afterOpt, IAfterOpterator.class);
    return afterOperator.after(context, result);
  }

  private Object build(OperatorContext context, Object result) {
    return result;
  }

  @Override
  public Object execute(OperatorContext context, Object frontResult) {
    return after(context, running(context, before(context, frontResult)));
  }
}

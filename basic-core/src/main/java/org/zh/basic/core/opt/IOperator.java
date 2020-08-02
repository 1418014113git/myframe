/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.opt;

/**
 * <功能描述/>
 *
 * @author kaven
 * @date 2018年6月13日 下午12:53:59
 * @version 1.0
 */
public interface IOperator {

  /**
   * 执行一个动作.
   * 
   * @param context 当前动作执行的上下文信息
   * @param frontResult 前一个动作执行的结果
   * @return Object 当前动作的结果
   */
  Object execute(OperatorContext context, Object frontResult);

}

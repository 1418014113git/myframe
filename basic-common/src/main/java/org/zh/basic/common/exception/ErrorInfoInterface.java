/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.common.exception;

/**
 * 统一异常处理接口.
 *
 * @author kaven
 * @date 2018年6月12日 下午3:12:37
 * @version 1.0
 */
public interface ErrorInfoInterface {
  Object getCode();

  String getMessage();
}

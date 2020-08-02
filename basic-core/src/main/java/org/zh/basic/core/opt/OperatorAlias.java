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
 * @date 2018年6月14日 上午10:03:59
 * @version 1.0
 */
public class OperatorAlias {

  private Integer id;
  private String alias;
  private String method;
  private String chains;
  private String description;

  public Integer getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getChains() {
    return chains;
  }

  public void setChain(String chains) {
    this.chains = chains;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setChains(String chains) {
    this.chains = chains;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("OperatorAlias [id=");
    builder.append(id);
    builder.append(", alias=");
    builder.append(alias);
    builder.append(", method=");
    builder.append(method);
    builder.append(", chains=");
    builder.append(chains);
    builder.append("]");
    return builder.toString();
  }

}

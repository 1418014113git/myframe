/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.rmdb.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

/**
 * <功能描述/>
 *
 * @author kaven
 * @date 2018年6月13日 上午11:02:09
 * @version 1.0
 */
@Configuration
@ConditionalOnClass({Environment.class, BeanDefinitionRegistry.class})
@AutoConfigureBefore(DataSource.class)
@Import(DynamicDataSourceRegister.class)
public class DynamicDataSourceAutoConfiguration {

}

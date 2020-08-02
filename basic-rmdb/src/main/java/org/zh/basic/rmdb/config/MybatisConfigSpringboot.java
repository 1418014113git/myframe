/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.rmdb.config;

import javax.annotation.PostConstruct;

import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * Mybatis统一配置类.
 *
 * @author kaven
 * @date 2018年6月13日 上午11:53:53
 * @version 1.0
 */
@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
public class MybatisConfigSpringboot {
  @Autowired
  private MybatisProperties mybatisProperties;

  @PostConstruct
  public void initMyBatisConfig() {
    mybatisProperties.setConfiguration(new MybatisConfiguration());
  }
}


/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <功能描述/>
 *
 * @author kaven
 * @date 2018年7月12日 下午12:05:00
 * @version 1.0
 */
@Configuration
public class BasicFilterConfig {
  /**
   * 拦截器注册
   * 
   * @return
   */
  @Bean
  public FilterRegistrationBean<BasicOncePerRequestFilter> myOncePerRequestFilterRegistration() {
    FilterRegistrationBean<BasicOncePerRequestFilter> registration =
        new FilterRegistrationBean<BasicOncePerRequestFilter>();

    registration.setFilter(new BasicOncePerRequestFilter());
    registration.addUrlPatterns("/*");// 拦截路径
    registration.setName("basicOncePerRequestFilter");// 拦截器名称
    registration.setOrder(1);// 顺序

    return registration;
  }
}

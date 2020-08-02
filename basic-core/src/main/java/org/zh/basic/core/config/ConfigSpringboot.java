/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.config;

import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.zh.basic.core.advice.BasicErrorController;
import org.zh.basic.core.advice.RequestMapArgumentResolver;
import org.zh.basic.core.chain.RequestChainFilter;

/**
 * 统一配置类.
 */
@Configuration
public class ConfigSpringboot implements WebMvcConfigurer {

  @Autowired(required = false)
  private List<ErrorViewResolver> errorViewResolvers;
  private final ServerProperties serverProperties;

  public ConfigSpringboot(ServerProperties serverProperties) {
    this.serverProperties = serverProperties;
  }

  @Bean
  public BasicErrorController basicErrorController(ErrorAttributes errorAttributes) {
    return new BasicErrorController(errorAttributes, this.serverProperties.getError(), this.errorViewResolvers);
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    // 增加新的MAP参数解析器
    argumentResolvers.add(new RequestMapArgumentResolver());
  }

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    // 删除MappingJackson2HttpMessageConverter
    // converters.removeIf(httpMessageConverter -> httpMessageConverter instanceof
    // MappingJackson2HttpMessageConverter);
    // 添加GsonHttpMessageConverter
    // converters.add(new GsonHttpMessageConverter());
  }

  @Bean
  public FilterRegistrationBean<Filter> someFilterRegistration() {
    FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
    registration.setFilter(requestChainFilter());
    registration.addUrlPatterns("/*");
    registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
    registration.setName("requestChainFilter");
    return registration;
  }

  @Bean
  public Filter requestChainFilter() {
    return new RequestChainFilter();
  }
}

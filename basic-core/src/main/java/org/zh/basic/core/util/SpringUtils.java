/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring容器帮助类.
 *
 * @author kaven
 * @date 2018年6月12日 下午3:12:37
 * @version 1.0
 */
@Component
public class SpringUtils implements ApplicationContextAware {

  private static ApplicationContext applicationContext;
  private static DefaultListableBeanFactory beanFactory;

  /**
   * 实现ApplicationContextAware接口的回调方法，设置上下文环境.
   * 
   * @param applicationContext spring上下文
   */
  @Autowired
  public void setApplicationContext(ApplicationContext applicationContext) {
    SpringUtils.applicationContext = applicationContext;
    SpringUtils.beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
  }

  private static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  /**
   * 获取spring容器中的bean.
   * 
   * @param beanName 名称
   * @param <T> 返回泛型类型
   * @return T
   */
  @SuppressWarnings("unchecked")
  public static <T> T getBean(String beanName) {
    return (T) getApplicationContext().getBean(beanName);
  }

  /**
   * 获取spring容器中的bean.
   * 
   * @param clazz 类型
   * @param <T> 返回泛型类型
   * @return T
   */
  public static <T> T getBean(Class<T> clazz) {
    return getApplicationContext().getBean(clazz);
  }

  /**
   * 获取spring容器中的bean.
   * 
   * @param beanName 名称
   * @param clazz 类
   * @param <T> 返回泛型类型
   * @return T
   */
  public static <T> T getBean(String beanName, Class<T> clazz) {
    return getApplicationContext().getBean(beanName, clazz);
  }

  /**
   * 判断spring容器中是否存在bean.
   * 
   * @param beanName 名称
   * @return boolean
   */
  public static boolean hasBean(String beanName) {
    return getApplicationContext().containsBean(beanName);
  }

  /**
   * 注册一个bean.
   * 
   * @param beanName 名称
   * @param clazz 类名
   */
  public static void registerBean(String beanName, Class<?> clazz) {
    BeanDefinition beanDefinition = new RootBeanDefinition(clazz);
    beanDefinition.setScope(ConfigurableBeanFactory.SCOPE_SINGLETON);
    beanFactory.registerBeanDefinition(beanName, beanDefinition);
  }

  /**
   * 删除一个bean.
   * 
   * @param beanName 名称
   */
  public static void removeBean(String beanName) {
    if (hasBean(beanName)) {
      beanFactory.removeBeanDefinition(beanName);
    }
  }
}


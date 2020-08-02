/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.rmdb.listener;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import org.zh.basic.core.config.Initialize;

import java.util.Collection;
import java.util.Map;


/**
 * 监听应用启动完毕.
 */
@Component
public class RmdbApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent>, Ordered {
  @Autowired
  @Qualifier("rmdb")
  private Initialize initialize;

  @Autowired
  private SqlSessionTemplate sqlSessionTemplate;

  @Override
  public int getOrder() {
    return 100;
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    //应用环境加载完毕，执行初始化
    initialize.init();
  }
}

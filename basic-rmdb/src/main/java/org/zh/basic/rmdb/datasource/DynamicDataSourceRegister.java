/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.rmdb.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;


import org.zh.basic.rmdb.utils.DataSourceInjectUtils;


/**
 * 动态数据源注册<br/>
 * 启动动态数据源请在启动类中.
 *
 * @author kaven
 * @date 2018年6月13日 上午11:02:09
 * @version 1.0
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

  private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSourceRegister.class);
  // 如配置文件中未指定数据源类型，使用该默认值
  // private static final Object DATASOURCE_TYPE_TOMCAT = "org.apache.tomcat.jdbc.pool.DataSource";
  private static final Object DATASOURCE_TYPE_DEFAULT = "com.zaxxer.hikari.HikariDataSource";
  private static final Object DATASOURCE_TYPE_DRUID = "com.alibaba.druid.pool.DruidDataSource";
  private Object dsType;
  private Integer minIdel;
  private Integer maxPoolSize;
  private Integer initialSize;
  // 数据源
  private DataSource defaultDataSource;
  private Map<String, DataSource> slaveDataSources = new HashMap<>();


  @Override
  public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
    Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
    // 将主数据源添加到更多数据源中
    targetDataSources.put("dataSource", defaultDataSource);
    DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
    // 添加更多数据源
    targetDataSources.putAll(slaveDataSources);
    for (String key : slaveDataSources.keySet()) {
      DynamicDataSourceContextHolder.dataSourceIds.add(key);
    }
    // 创建DynamicDataSource
    GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
    beanDefinition.setBeanClass(DynamicDataSource.class);
    beanDefinition.setSynthetic(true);
    MutablePropertyValues mpv = beanDefinition.getPropertyValues();
    mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
    mpv.addPropertyValue("targetDataSources", targetDataSources);
    registry.registerBeanDefinition("dataSource", beanDefinition);
  }

  /**
   * 创建DataSource
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  private DataSource buildDataSource(Map<String, Object> dsMap) {
    try {
      dsType = dsMap.get("type");
      if (dsType == null) {
        dsType = DATASOURCE_TYPE_DRUID;// 默认DataSource
      }

      Class<? extends DataSource> dataSourceType;
      dataSourceType = (Class<? extends DataSource>) Class.forName((String) dsType);

      String driverClassName =
          dsMap.get("driver-class-name") == null ? null : dsMap.get("driver-class-name").toString();
      String url = dsMap.get("url").toString();
      String username = dsMap.get("username").toString();
      String password = dsMap.get("password").toString();

      DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
          .username(username).password(password).type(dataSourceType);
      DataSource ds = factory.build();
      if (DATASOURCE_TYPE_DEFAULT.equals(ds.getClass().getName())) {
        DataSourceInjectUtils.inject(ds, "setMinimumIdle", minIdel, int.class);
        DataSourceInjectUtils.inject(ds, "setMaximumPoolSize", maxPoolSize, int.class);
      } else if (DATASOURCE_TYPE_DRUID.equals(ds.getClass().getName())) {
        DataSourceInjectUtils.inject(ds, "setMinIdle", minIdel, int.class);
        DataSourceInjectUtils.inject(ds, "setMaxActive", maxPoolSize, int.class);
        DataSourceInjectUtils.inject(ds, "setInitialSize", initialSize, int.class);
      }
      return ds;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 加载多数据源配置
   */
  @Override
  public void setEnvironment(Environment env) {
    initDefaultDataSource(env);
    initSlaveDataSources(env);
  }

  /**
   * 初始化主数据源
   */
  private void initDefaultDataSource(Environment env) {
    // 读取主数据源
    Map<String, Object> dsMap = createDSMap(env, "spring.datasource");
    minIdel = env.getProperty("spring.datasource.min-idle", Integer.class, 0);
    maxPoolSize = env.getProperty("spring.datasource.max-pool-size", Integer.class, 5);
    initialSize = env.getProperty("spring.datasource.initial-size", Integer.class, 0);
    defaultDataSource = buildDataSource(dsMap);
  }

  private Map<String, Object> createDSMap(Environment env, String dsprefix) {
    if (env.getProperty(String.format("%s.url", dsprefix)) == null) {
//      throw new GlobalErrorException(DataSourceErrorEnum.DS_CONFIG_NOFOUND, String.format("%s.url", dsprefix));
    }
    Map<String, Object> dsMap = new HashMap<>();
    dsMap.put("type", env.getProperty(String.format("%s.type", dsprefix)));
    dsMap.put("driver-class-name", env.getProperty(String.format("%s.driver-class-name", dsprefix)));
    dsMap.put("url", env.getProperty(String.format("%s.url", dsprefix)));
    dsMap.put("username", env.getProperty(String.format("%s.username", dsprefix)));
    dsMap.put("password", env.getProperty(String.format("%s.password", dsprefix)));
    return dsMap;
  }


  /**
   * 初始化备数据源
   */
  private void initSlaveDataSources(Environment env) {
    // 读取配置文件获取更多数据源，也可以通过defaultDataSource读取数据库获取更多数据源
    String dsPrefixs = env.getProperty("spring.slave.datasource.names");
    if (dsPrefixs != null) {
      String[] dsNames = dsPrefixs.split(",");
      for (String dsPrefix : dsNames) {// 多个数据源
        Map<String, Object> dsMap = createDSMap(env, String.format("spring.slave.datasource.%s", dsPrefix));
        if (dsMap != null) {
          DataSource ds = buildDataSource(dsMap);
          slaveDataSources.put(dsPrefix, ds);
        }
      }
    }
  }

}

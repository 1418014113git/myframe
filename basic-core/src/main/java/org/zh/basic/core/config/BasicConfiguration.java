

package org.zh.basic.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <功能描述/>
 */
@Configuration
@ConfigurationProperties(prefix = "basic")
public class BasicConfiguration {

  private String headerParamName;

  public String getHeaderParamName() {
    return headerParamName;
  }

  public void setHeaderParamName(String headerParamName) {
    this.headerParamName = headerParamName;
  }

}

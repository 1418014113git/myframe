/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import org.zh.basic.core.common.LocalThreadStorage;

/**
 * <功能描述/>
 *
 * @author kaven
 * @date 2018年7月12日 下午12:05:15
 * @version 1.0
 */
public class BasicOncePerRequestFilter extends OncePerRequestFilter {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (logger.isDebugEnabled()) {
      logger.debug("---start basicOncePerRequestFilter");
    }
    // 清空缓存信息
    LocalThreadStorage.clear();
    filterChain.doFilter(request, response);
    if (logger.isDebugEnabled()) {
      logger.debug("---end basicOncePerRequestFilter");
    }
  }

}

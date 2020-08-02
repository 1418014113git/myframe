/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取客户端真实IP.
 *
 * @author zhanghr
 * @date 2017年9月26日 下午3:32:23
 * @version 1.0
 */
public class IpAddressUtils {
  private static final Logger LOGGER = LoggerFactory.getLogger(IpAddressUtils.class);

  public static String getHostName() {
    try {
      return (InetAddress.getLocalHost()).getHostName();
    } catch (UnknownHostException uhe) {
      String host = uhe.getMessage();
      if (host != null) {
        int colon = host.indexOf(':');
        if (colon > 0) {
          return host.substring(0, colon);
        }
      }
      return "UnknownHost";
    }
  }

  public static String getClientIpAddr(HttpServletRequest request) {
    // 测试代码
    if (LOGGER.isDebugEnabled()) {
      Enumeration<String> enumerationOfHeaderNames = request.getHeaderNames();
      while (enumerationOfHeaderNames.hasMoreElements()) {
        String headerName = enumerationOfHeaderNames.nextElement();
        Enumeration<String> headers = request.getHeaders(headerName);
        while (headers.hasMoreElements()) {
          String headerValue = headers.nextElement();
          LOGGER.debug("{} = {}", headerName, headerValue);
        }
      }
    }
    String ip = request.getHeader("x-forwarded-for");
    if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
      // 多次反向代理后会有多个ip值，第一个ip才是真实ip
      if (ip.indexOf(",") != -1) {
        ip = ip.split(",")[0];
      }
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("X-Real-IP");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("HTTP_X_FORWARDED");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("HTTP_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("HTTP_FORWARDED");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("HTTP_VIA");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("REMOTE_ADDR");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getRemoteAddr();
    }
    if ("0:0:0:0:0:0:0:1".equals(ip)) {
      ip = "127.0.0.1";
    }
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("获取客户端ip: {}", ip);
    }
    return ip;
  }
}

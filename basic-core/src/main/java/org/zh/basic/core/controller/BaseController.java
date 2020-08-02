/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.zh.basic.common.Constant;
import org.zh.basic.common.Result;
import org.zh.basic.core.common.LocalThreadStorage;
import org.zh.basic.core.config.BasicConfiguration;
import org.zh.basic.core.service.IBaseService;
import org.zh.basic.core.service.handler.IQueryHandler;
import org.zh.basic.core.service.handler.IRemoveHandler;
import org.zh.basic.core.service.handler.ISaveHandler;
import org.zh.basic.core.service.handler.IUpdateHandler;
import org.zh.basic.core.util.SpringUtils;


/**
 * 统一控制器处理类.
 */
@RestController
public class BaseController {

  @Autowired
  private BasicConfiguration basicConfig;

  @Autowired
  @Qualifier("baseService")
  private IBaseService baseService;

  /**
   * 新增操作.
   * 
   * @param alias 操作别名
   * @param requestBody 请求体数据
   * @param requestHeader 请求头数据
   * @return Object
   * @throws Exception 直接抛出
   */
  @PutMapping("/{alias}")
  @ResponseBody
  public Object save(@PathVariable String alias, @RequestBody Map<String, Object> requestBody,
      @RequestHeader Map<String, Object> requestHeader) throws Exception {
    // 合并map
    requestBody = mergeMap(requestBody, requestHeader);

    LocalThreadStorage.put(Constant.CONTROLLER_ALIAS, alias.toUpperCase());
    alias = Constant.getHandlerBeanName(alias, Constant.OPERATOR_SAVE);
    if (SpringUtils.hasBean(alias)) {
      ISaveHandler service = SpringUtils.getBean(alias, ISaveHandler.class);
      return Result.ok(service.save(requestBody));
    }
    return Result.ok(baseService.save(requestBody));
  }

  /**
   * 查询集合数据.
   * 
   * @param alias 操作别名
   * @param requestMap 请求参数
   * @param requestHeader 请求头数据
   * @return Object
   * @throws Exception 直接抛出
   */
  @GetMapping("/{alias}")
  @ResponseBody
  public Object list(@PathVariable String alias, @RequestParam Map<String, Object> requestParam,
      @RequestHeader Map<String, Object> requestHeader) throws Exception {
    // 合并map
    requestParam = mergeMap(requestParam, requestHeader);

    LocalThreadStorage.put(Constant.CONTROLLER_ALIAS, alias.toUpperCase());
    alias = Constant.getHandlerBeanName(alias, Constant.OPERATOR_QUERY);
    if (SpringUtils.hasBean(alias)) {
      IQueryHandler service = SpringUtils.getBean(alias, IQueryHandler.class);
      return Result.ok(service.list(requestParam));
    }
    return Result.ok(baseService.list(requestParam));
  }

  /**
   * 查询集合数据.
   * 
   * @param alias 操作别名
   * @param requestMap 请求参数
   * @param requestHeader 请求头数据
   * @param currentPage 当前页
   * @param pageSize 每页条数
   * @return Object
   * @throws Exception 直接抛出
   */
  @GetMapping("/page/{alias}")
  @ResponseBody
  public Object page(@PathVariable String alias, @RequestParam Map<String, Object> requestParam,
      @RequestParam(defaultValue = "1", required = false) Integer pageNum,
      @RequestParam(defaultValue = "10", required = false) Integer pageSize,
      @RequestHeader Map<String, Object> requestHeader) throws Exception {
    // 合并map
    requestParam = mergeMap(requestParam, requestHeader);

    LocalThreadStorage.put(Constant.CONTROLLER_ALIAS, alias.toUpperCase());

    Object result = null;

    alias = Constant.getHandlerBeanName(alias, Constant.OPERATOR_QUERY);
    if (SpringUtils.hasBean(alias)) {
      IQueryHandler service = SpringUtils.getBean(alias, IQueryHandler.class);
      result = service.page(requestParam, pageNum, pageSize);
    } else {
      result = baseService.page(requestParam, pageNum, pageSize);
    }
    return Result.ok(result);
  }

  /**
   * 查询单条数据.
   * 
   * @param alias 操作别名
   * @param id 请求id
   * @param requestHeader 请求头数据
   * @return Object
   * @throws Exception 直接抛出
   */
  @GetMapping("/{alias}/{id}")
  @ResponseBody
  public Object get(@PathVariable String alias, @PathVariable String id,
      @RequestHeader Map<String, String> requestHeader) throws Exception {
    LocalThreadStorage.put(Constant.CONTROLLER_ALIAS, alias.toUpperCase());

    alias = Constant.getHandlerBeanName(alias, Constant.OPERATOR_QUERY);
    if (SpringUtils.hasBean(alias)) {
      IQueryHandler service = SpringUtils.getBean(alias, IQueryHandler.class);
      return Result.ok(service.get(id));
    }
    return Result.ok(baseService.get(id));
  }

  /**
   * 修改单条数据.
   * 
   * @param alias 操作别名
   * @param id 请求id
   * @param requestBody 请求体数据
   * @param requestHeader 请求头数据
   * @return Object
   * @throws Exception 直接抛出
   */
  @PostMapping("/{alias}/{id}")
  @ResponseBody
  public Object update(@PathVariable String alias, @PathVariable String id,
      @RequestBody Map<String, Object> requestBody, @RequestHeader Map<String, Object> requestHeader) throws Exception {
    // 合并map
    requestBody = mergeMap(requestBody, requestHeader);
    LocalThreadStorage.put(Constant.CONTROLLER_ALIAS, alias.toUpperCase());

    alias = Constant.getHandlerBeanName(alias, Constant.OPERATOR_UPDATE);
    if (SpringUtils.hasBean(alias)) {
      IUpdateHandler service = SpringUtils.getBean(alias, IUpdateHandler.class);
      return Result.ok(service.update(id, requestBody));
    }
    return Result.ok(baseService.update(id, requestBody));
  }

  /**
   * 修改批量数据.
   * 
   * @param alias 操作别名
   * @param requestBody 请求体数据
   * @param requestParam 请求参数
   * @param requestHeader 请求头数据
   * @return Object
   * @throws Exception 直接抛出
   */
  // @PostMapping("/{alias}")
  // @ResponseBody
  // public Object update(@PathVariable String alias, @RequestBody Map<String, String> requestBody,
  // @RequestParam(required = false) Map<String, String> requestParam,
  // @RequestHeader Map<String, String> requestHeader) throws Exception {
  // MDC.put(Constant.CONTROLLER_ALIAS, alias);
  // MDC.put(Constant.CONTROLLER_METHOD, Constant.CONTROLLER_METHOD_POST_LIST);
  // if (SpringUtils.hasBean(alias)) {
  // IUpdateHandler service = SpringUtils.getBean(alias, IUpdateHandler.class);
  // return service.update(requestParam, requestBody);
  // }
  // return simpleService.update(requestParam, requestBody);
  // }

  /**
   * 删除单条数据.
   * 
   * @param alias 操作别名
   * @param id 请求id
   * @param requestHeader 请求头数据
   * @return Object
   * @throws Exception 直接抛出
   */
  @DeleteMapping("/{alias}/{id}")
  @ResponseBody
  public Object remove(@PathVariable String alias, @PathVariable String id,
      @RequestHeader Map<String, String> requestHeader) throws Exception {
    LocalThreadStorage.put(Constant.CONTROLLER_ALIAS, alias.toUpperCase());

    alias = Constant.getHandlerBeanName(alias, Constant.OPERATOR_REMOVE);
    if (SpringUtils.hasBean(alias)) {
      IRemoveHandler service = SpringUtils.getBean(alias, IRemoveHandler.class);
      service.remove(id);
    } else {
      baseService.remove(id);
    }
    return Result.ok(id);
  }

  /**
   * 删除批量数据.
   * 
   * @param alias 操作别名
   * @param requestParam 请求参数
   * @param requestHeader 请求头数据
   * @return Object
   * @throws Exception 直接抛出
   */
  @DeleteMapping("/{alias}")
  @ResponseBody
  public Object remove(@PathVariable String alias, @RequestBody Map<String, Object> requestParam,
      @RequestHeader Map<String, Object> requestHeader) throws Exception {
    // 合并map
    requestParam = mergeMap(requestParam, requestHeader);
    LocalThreadStorage.put(Constant.CONTROLLER_ALIAS, alias.toUpperCase());

    alias = Constant.getHandlerBeanName(alias, Constant.OPERATOR_REMOVE);
    if (SpringUtils.hasBean(alias)) {
      IRemoveHandler service = SpringUtils.getBean(alias, IRemoveHandler.class);
      service.remove(requestParam);
    } else {
      baseService.remove(requestParam);
    }
    return Result.ok(null);
  }

  /**
   * 合并 请求的map 和 heaherMap
   * 
   * @param requestMap
   * @param requestHeader
   */
  public Map<String, Object> mergeMap(Map<String, Object> requestMap, Map<String, Object> requestHeader) {
    if (!StringUtils.isEmpty(basicConfig.getHeaderParamName())) {
      String[] paramNames = basicConfig.getHeaderParamName().split(",");
      int paramNameL = paramNames.length;
      if (paramNameL >= 1) {
        for (String paramName : paramNames) {
          if (!StringUtils.isEmpty(paramName)) {
            requestMap.put(paramName, requestHeader.get(paramName));
          }
        }
      }

    }
    return requestMap;
  }

}

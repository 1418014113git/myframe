/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import org.zh.basic.common.Constant;
import org.zh.basic.common.exception.GlobalErrorEnum;
import org.zh.basic.core.common.Assert;
import org.zh.basic.core.common.LocalThreadStorage;
import org.zh.basic.core.opt.OperatorChain;
import org.zh.basic.core.opt.OperatorConfig;
import org.zh.basic.core.opt.OperatorContext;
import org.zh.basic.core.opt.manager.OperatorManager;
import org.zh.basic.core.page.Paging;
import org.zh.basic.core.service.IBaseService;



/**
 * <功能描述/>
 */
@Service("baseService")
public class BaseServiceImpl implements IBaseService {
  /**
   * 查询数据库 获取操作 获取mybates 本地存储mybates
   * 
   */
  @Autowired
  private OperatorManager operatorManager;

  @Override
  public Object save(Map<String, Object> requestBody) throws Exception {
    String alias = LocalThreadStorage.get(Constant.CONTROLLER_ALIAS);

    // 默认主键自增长,设置Constant.CONTROLLER_AUTO_INCREMENT为false时,可以跳过主键自增长
    Boolean auto_increment = LocalThreadStorage.getBoolean(Constant.CONTROLLER_AUTO_INCREMENT);
    if (auto_increment != null && !auto_increment.booleanValue()) {
      return operator(alias, Constant.CONTROLLER_METHOD_INSERT_ONE, null, requestBody, null);
    }
    Object id = getSequence(alias.toUpperCase());
    // 默认所有主键别名为id
    requestBody.put("id", id);
    operator(alias, Constant.CONTROLLER_METHOD_INSERT_ONE, null, requestBody, null);
    return id;
  }

  /**
   * 获取表主键值
   * 
   * @param seqName
   * @return
   */
  @SuppressWarnings("unchecked")
  @Override
  public Object getSequence(String seqName) {
    Map<String, Object> requestParams = new HashMap<String, Object>();
    requestParams.put("seqName", seqName.toUpperCase());
    requestParams.put("id", null);
    // updateSequence(seqName);
    Map<String, Object> sequenceMap = (Map<String, Object>) operator(Constant.ALIAS_SEQUENCE,
        Constant.CONTROLLER_METHOD_GET_ONE, null, null, requestParams);
    Object id = null;
    if (sequenceMap == null) {// 存储过程调用的返回值从请求参数取值
      id = requestParams.get("id");
    } else {// 正常能够返回序列ID
      id = sequenceMap.get("id");
      // 如果是Oracle数据库则id为ID
      if (StringUtils.isEmpty(id)) {
        id = sequenceMap.get("ID");
      }
    }
    return id;
  }

  /**
   * 更新表主键值
   * 
   * @param seqName
   * @return
   */
  // private Object updateSequence(String seqName) {
  // Map<String, Object> requestParams = new HashMap<String, Object>();
  // requestParams.put("seqName", seqName);
  // return operator(Constant.ALIAS_SEQUENCE, Constant.CONTROLLER_METHOD_UPDATE_ONE, null, null,
  // requestParams);
  // }


  @Override
  public Object update(String id, Map<String, Object> requestBody) throws Exception {
    String alias = LocalThreadStorage.get(Constant.CONTROLLER_ALIAS);
    return operator(alias, Constant.CONTROLLER_METHOD_UPDATE_ONE, id, requestBody, null);
  }

  @Override
  public Object update(Map<String, Object> requestParam, Map<String, Object> requestBody) throws Exception {
    String alias = LocalThreadStorage.get(Constant.CONTROLLER_ALIAS);
    return operator(alias, Constant.CONTROLLER_METHOD_UPDATE_LIST, null, requestBody, requestParam);
  }



  @Override
  public void remove(Map<String, Object> requestMap) throws Exception {
    String alias = LocalThreadStorage.get(Constant.CONTROLLER_ALIAS);
    operator(alias, Constant.CONTROLLER_METHOD_DELETE_LIST, null, null, requestMap);
  }

  @Override
  public void remove(String id) throws Exception {
    String alias = LocalThreadStorage.get(Constant.CONTROLLER_ALIAS);
    operator(alias, Constant.CONTROLLER_METHOD_DELETE_ONE, id, null, null);
  }

  @Override
  public Object get(String id) throws Exception {
    String alias = LocalThreadStorage.get(Constant.CONTROLLER_ALIAS);
    return operator(alias, Constant.CONTROLLER_METHOD_GET_ONE, id, null, null);
  }

  @Override
  public Object get(Map<String, Object> requestMap) throws Exception {
    String alias = LocalThreadStorage.get(Constant.CONTROLLER_ALIAS);
    return operator(alias, Constant.CONTROLLER_METHOD_GET_ONE, null, null, requestMap);
  }

  @Override
  public Object list(Map<String, Object> requestMap) throws Exception {
    String alias = LocalThreadStorage.get(Constant.CONTROLLER_ALIAS);
    return operator(alias, Constant.CONTROLLER_METHOD_GET_LIST, null, null, requestMap);
  }

  @Override
  public Object page(Map<String, Object> requestMap, int currentPage, int pageSize) throws Exception {
    String alias = LocalThreadStorage.get(Constant.CONTROLLER_ALIAS);
    return page(alias, requestMap, currentPage, pageSize);
  }

  @Override
  public Object findAll() throws Exception {
    String alias = LocalThreadStorage.get(Constant.CONTROLLER_ALIAS);
    return operator(alias, Constant.CONTROLLER_METHOD_GET_LIST, null, null, null);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  protected Paging page(String alias, Map<String, Object> requestMap, int currentPage, int pageSize) throws Exception {
    LocalThreadStorage.put(Constant.CONTROLLER_PAGE, true);
    LocalThreadStorage.put(Constant.CONTROLLER_PAGE_CURPAGE, currentPage);
    LocalThreadStorage.put(Constant.CONTROLLER_PAGE_PAGESIZE, pageSize);
    List<?> list = (List<?>) operator(alias, Constant.CONTROLLER_METHOD_GET_LIST, null, null, requestMap);
    long total = LocalThreadStorage.getLong(Constant.CONTROLLER_PAGE_TOTALCOUNT);
    return new Paging(pageSize, currentPage, total, list);
  }

  /**
   * 
   * @param alias 操作名
   * @param method 操作类型
   * @param id 主键id
   * @param body
   * @param params
   * @return
   */
  private Object operator(String alias, String method, Object id, Map<String, Object> body,
      Map<String, Object> params) {
    String operatorStr = Constant.getOperatorConfig(alias, method);
    OperatorChain optChain = new OperatorChain(getOptConfig(operatorStr));
    OperatorContext context = new OperatorContext(id, body, params);
    return optChain.execute(context, null);
  }

  private OperatorConfig getOptConfig(String statement) {
    OperatorConfig optConfig = operatorManager.getOptCfg(statement);
    Assert.notNull(optConfig, GlobalErrorEnum.OPERATE_NO_FOUND, statement);
    return optConfig;
  }

}

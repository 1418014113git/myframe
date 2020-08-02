/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.rmdb.config;

import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zh.basic.common.Constant;
import org.zh.basic.common.exception.GlobalErrorEnum;
import org.zh.basic.common.exception.GlobalErrorException;
import org.zh.basic.core.advice.DataSourceErrorEnum;
import org.zh.basic.core.common.Assert;
import org.zh.basic.core.config.Initialize;
import org.zh.basic.core.opt.OperatorAlias;
import org.zh.basic.core.opt.OperatorConfig;
import org.zh.basic.core.opt.manager.OperatorManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 统一处理mybatic Mapper.
 *
 * @author kaven
 * @date 2018年6月13日 上午11:56:23
 * @version 1.0
 */
@Component("rmdb")
public class MybatisMapperHelper implements Initialize {
  private static final Logger LOGGER = LoggerFactory.getLogger(MybatisMapperHelper.class);

  private LanguageDriver languageDriver = new XMLLanguageDriver();

  @Autowired
  private SqlSessionTemplate sessionTemplate;
 @Autowired
 private OperatorManager operatorManager;

  private Configuration configuration;

  public Map<String, OperatorAlias> getOptAlias() {
    return operatorManager.getOptAliasMap();
  }

  public Map<String, OperatorConfig> getOptCfg() {
    return operatorManager.getOptCfgMap();
  }

  /**
   * 初始化系统数据.
   */
  @Override
  public void init() {
    if (configuration == null) {
      configuration = sessionTemplate.getConfiguration();
    }
    // 初始化默认表数操作据
    initDeafultMappedStatement();
    initDefindedMappedStatement();
//    if (LOGGER.isDebugEnabled()) {
//      LOGGER.debug("数据初始化完毕: alias:{},statements: {}", getOptCfg());
//    }
  }

  private void initInsert(String id, String script) {
    createMappedStatement(id, script, SqlCommandType.INSERT, null);
  }

  private void initBatchInsert(String id, String script) {
    createMappedStatement(id, script, SqlCommandType.DELETE, List.class, Map.class, null,
        new ArrayList<ResultMapping>());
  }

  private void initDelete(String id, String script) {
    createMappedStatement(id, script, SqlCommandType.DELETE, null);
  }

  private void initUpdate(String id, String script) {
    createMappedStatement(id, script, SqlCommandType.UPDATE, null);
  }

  private void initSelect(String id, String script, StatementType statementType) {
    createMappedStatement(id, script, SqlCommandType.SELECT, statementType);
  }

  public void createStatement(String alias, String script, String methodType) {
    if (configuration == null) {
      configuration = sessionTemplate.getConfiguration();
    }
    createMappedStatement(alias, script, methodType);
    createAliasAndConfig(alias, methodType, Constant.OPERATOR_TYPE_DB);
  }

  private void createMappedStatement(String statement, String script, String methodType) {
    switch (methodType) {
      case Constant.CONTROLLER_METHOD_GET_ONE:
      case Constant.CONTROLLER_METHOD_GET_LIST:
        initSelect(statement.toUpperCase(), script, null);
        break;
      case Constant.CONTROLLER_METHOD_INSERT_ONE:
        initInsert(statement.toUpperCase(), script);
        break;
      case Constant.CONTROLLER_METHOD_INSERT_LIST:
        initBatchInsert(statement.toUpperCase(), script);
        break;
      case Constant.CONTROLLER_METHOD_UPDATE_ONE:
      case Constant.CONTROLLER_METHOD_UPDATE_LIST:
        initUpdate(statement.toUpperCase(), script);
        break;
      case Constant.CONTROLLER_METHOD_DELETE_ONE:
      case Constant.CONTROLLER_METHOD_DELETE_LIST:
        initDelete(statement.toUpperCase(), script);
        break;
      default:
        throw new GlobalErrorException(GlobalErrorEnum.OPERATE_NO_SUPPORT,
            String.format("optStatementType:%s", methodType));
    }
  }

  private void createMappedStatement(OperatorConfig tempCfg) {
    // 调用存储过程
    if (Constant.OPERATOR_VALUE_TYPE_PROCDURE.equals(tempCfg.getOptValueType())) {
      initSelect(tempCfg.getStatement(), tempCfg.getOptValue(), StatementType.CALLABLE);
    } else {
      createMappedStatement(tempCfg.getStatement(), tempCfg.getOptValue(), tempCfg.getOptStatementType());
    }
  }

  private void createMappedStatement(String id, String script, SqlCommandType type, StatementType statementType) {
    createMappedStatement(id, script, type, Map.class, Map.class, statementType, new ArrayList<ResultMapping>());
  }

  /**
   * 
   * @param id 对应MappedStatement
   * @param script 对应的mybaits定义
   * @param type sql的操作类型
   * @param parameterType 入参类型
   * @param resultType 返回类型
   * @param resultMappings 结果映射
   */
  private void createMappedStatement(String id, String script, SqlCommandType type, Class<?> parameterType,
      Class<?> resultType, StatementType statementType, List<ResultMapping> resultMappings) {
    try {
      StringBuffer sb = new StringBuffer("<script>");
      sb.append(script).append("</script>");
      SqlSource sqlSource =
          languageDriver.createSqlSource(sessionTemplate.getConfiguration(), sb.toString(), parameterType);
      MappedStatement.Builder builder =
          new MappedStatement.Builder(sessionTemplate.getConfiguration(), id.toUpperCase(), sqlSource, type);
      if (resultType != null) {
        ResultMap.Builder resultMapbuidler =
            new ResultMap.Builder(sessionTemplate.getConfiguration(), id.toUpperCase(), resultType, resultMappings);
        List<ResultMap> list = new ArrayList<ResultMap>(1);
        list.add(resultMapbuidler.build());
        builder.resultMaps(list);
      }
      if (statementType != null) {
        builder.statementType(statementType);
      }
      MappedStatement ms = builder.build();
      sessionTemplate.getConfiguration().addMappedStatement(ms);
    } catch (Exception e) {
      LOGGER.error(
          DataSourceErrorEnum.OPERATE_CREATE_ERROR.getMessage() + " createMappedStatement ERROE id:{}, script: {}", id,
          script, e);
      throw new GlobalErrorException(DataSourceErrorEnum.OPERATE_CREATE_ERROR);
    }
  }

  private void initDeafultMappedStatement() {
    createMappedStatement(DefaultStatementScript.DBOPERATOR_GET_LIST, DefaultStatementScript.DBOPERATOR_GET_LIST_SCRIPT,
        SqlCommandType.SELECT, Map.class, OperatorConfig.class, null, createResultMappingOfHoperator());
    createAliasAndConfig("dboperator", Constant.CONTROLLER_METHOD_GET_LIST, Constant.OPERATOR_TYPE_DB);
  }

  private void createAliasAndConfig(String alias, String method, String optType) {
    Assert.notNull(alias);
    Assert.notNull(method);
    Assert.notNull(optType);

    OperatorConfig optCfg = new OperatorConfig();
    optCfg.setStatement(String.format("%s.%s", alias, method).toUpperCase());
    optCfg.setOptType(optType);
    optCfg.setOptStatementType(method);
    getOptCfg().put(optCfg.getStatement(), optCfg);
  }

  private List<ResultMapping> createResultMappingOfHoperator() {
    List<ResultMapping> resultMappings = new ArrayList<ResultMapping>();
    ResultMapping.Builder builder = new ResultMapping.Builder(configuration, "statement", "statement", String.class);
    resultMappings.add(builder.build());
    builder = new ResultMapping.Builder(configuration, "optType", "opt_type", String.class);
    resultMappings.add(builder.build());
    builder = new ResultMapping.Builder(configuration, "optStatementType", "opt_statement_type", String.class);
    resultMappings.add(builder.build());
    builder = new ResultMapping.Builder(configuration, "optTarget", "opt_target", String.class);
    resultMappings.add(builder.build());
    builder = new ResultMapping.Builder(configuration, "optValue", "opt_value", String.class);
    resultMappings.add(builder.build());
    builder = new ResultMapping.Builder(configuration, "optValueType", "opt_value_type", String.class);
    resultMappings.add(builder.build());
    builder = new ResultMapping.Builder(configuration, "optRules", "opt_rules", String.class);
    resultMappings.add(builder.build());
    builder = new ResultMapping.Builder(configuration, "optPk", "opt_pk", String.class);
    resultMappings.add(builder.build());
    builder = new ResultMapping.Builder(configuration, "beforeOpt", "before_opt", String.class);
    resultMappings.add(builder.build());
    builder = new ResultMapping.Builder(configuration, "afterOpt", "after_opt", String.class);
    resultMappings.add(builder.build());
    builder = new ResultMapping.Builder(configuration, "optDesc", "opt_desc", String.class);
    resultMappings.add(builder.build());
    return resultMappings;
  }

  private void initDefindedMappedStatement() {
    Map<String, String> params = new HashMap<>(1);
    params.put("optType", Constant.OPERATOR_TYPE_DB);
    // 加载自定义操作
    List<OperatorConfig> optCfgs = sessionTemplate.selectList(DefaultStatementScript.DBOPERATOR_GET_LIST, params);
    for (OperatorConfig tempCfg : optCfgs) {
      getOptCfg().put(tempCfg.getStatement().toUpperCase(), tempCfg);
      createMappedStatement(tempCfg);
    }
  }
}

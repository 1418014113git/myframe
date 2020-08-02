/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.rmdb.opt.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.zh.basic.common.Constant;
import org.zh.basic.common.exception.GlobalErrorEnum;
import org.zh.basic.common.exception.GlobalErrorException;
import org.zh.basic.core.common.LocalThreadStorage;
import org.zh.basic.core.opt.AbstractOperator;
import org.zh.basic.core.opt.OperatorContext;


/**
 * <功能描述/>
 *
 * @author kaven
 * @date 2018年6月13日 下午1:49:18
 * @version 1.0
 */
@Service("RMDB")
public class RmdbOperator extends AbstractOperator {
  @Autowired
  private SqlSessionTemplate sessionTemplate;

  @Override
  @Transactional
  protected Object running(OperatorContext context, Object requestResult) {
    String statementType = context.getCurOptConfig().getOptStatementType();
    if (StringUtils.isEmpty(statementType)) {
      throw new GlobalErrorException(GlobalErrorEnum.OPERATE_CFG_ERROR, "opt_statment_type");
    }
    Object result = null;
    String statement = context.getCurOptConfig().getStatement().toUpperCase();
    switch (statementType) {
      case Constant.CONTROLLER_METHOD_GET_ONE:
        result = sessionTemplate.selectOne(statement, context.getRequestParams());
        break;
      case Constant.CONTROLLER_METHOD_GET_LIST:
        Boolean pageable = LocalThreadStorage.getBoolean(Constant.CONTROLLER_PAGE);
        if (pageable != null && pageable.booleanValue()) {
          int curPage = LocalThreadStorage.getInt(Constant.CONTROLLER_PAGE_CURPAGE);
          int pageSize = LocalThreadStorage.getInt(Constant.CONTROLLER_PAGE_PAGESIZE);
          PageHelper.startPage(curPage, pageSize);
        }
        result = sessionTemplate.selectList(statement, context.getRequestParams());
        if (result instanceof Page) {
          LocalThreadStorage.put(Constant.CONTROLLER_PAGE_TOTALCOUNT, ((Page<?>) result).getTotal());
        }
        break;
      case Constant.CONTROLLER_METHOD_INSERT_ONE:
        result = sessionTemplate.insert(statement, context.getRequestParams());
        break;
      case Constant.CONTROLLER_METHOD_UPDATE_LIST:
      case Constant.CONTROLLER_METHOD_UPDATE_ONE:
        result = sessionTemplate.update(statement, context.getRequestParams());
        break;
      case Constant.CONTROLLER_METHOD_DELETE_LIST:
      case Constant.CONTROLLER_METHOD_DELETE_ONE:
        result = sessionTemplate.delete(statement, context.getRequestParams());
        break;
      default:
        throw new GlobalErrorException(GlobalErrorEnum.OPERATE_NO_SUPPORT,
            String.format("opt_statment_type:%s", statementType));
    }
    return result;
  }
}

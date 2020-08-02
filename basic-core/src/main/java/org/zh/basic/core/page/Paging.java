/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.core.page;

import java.util.List;

/**
 * 分类Object类
 *
 * @author kaven
 * @date 2018年6月12日 下午6:12:37
 * @version 1.0
 */
public class Paging<T> {
  private int pageSize;
  private int pageNum;
  private long totalCount;
  private List<T> list;

  public Paging(int pageSize, int pageNum, long totalCount, List<T> list) {
    super();
    this.pageSize = pageSize;
    this.pageNum = pageNum;
    this.totalCount = totalCount;
    this.list = list;
  }

  public Paging(int pageSize, int pageNum) {
    this.pageSize = pageSize;
    this.pageNum = pageNum;
  }

  public int getPageSize() {
    return pageSize;
  }

  public int getPageNum() {
    return pageNum;
  }

  public long getTotalCount() {
    return totalCount;
  }

  public List<T> getList() {
    return list;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public void setPageNum(int pageNum) {
    this.pageNum = pageNum;
  }

  public void setTotalCount(long totalCount) {
    this.totalCount = totalCount;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Paging [pageSize=");
    builder.append(pageSize);
    builder.append(", pageNum=");
    builder.append(pageNum);
    builder.append(", totalCount=");
    builder.append(totalCount);
    builder.append(", list=");
    builder.append(list);
    builder.append("]");
    return builder.toString();
  }
}

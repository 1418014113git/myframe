/*
 * Copyright (C) 2018 @内蒙古慧瑞.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.zh.basic.rmdb.config;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;


/**
 * 自定义修改mybatis配置类.
 *
 * @author kaven
 * @date 2018年6月13日 上午11:54:36
 * @version 1.0
 */
public class MybatisConfiguration extends Configuration {
  protected final Map<String, MappedStatement> mappedStatements =
      new StrictMap<MappedStatement>("Mapped Statements collection");

  public void addMappedStatement(MappedStatement ms) {
    mappedStatements.put(ms.getId(), ms);
  }

  public Collection<String> getMappedStatementNames() {
    buildAllStatements();
    return mappedStatements.keySet();
  }

  public Collection<MappedStatement> getMappedStatements() {
    buildAllStatements();
    return mappedStatements.values();
  }

  public MappedStatement getMappedStatement(String id, boolean validateIncompleteStatements) {
    if (validateIncompleteStatements) {
      buildAllStatements();
    }
    try {
      MappedStatement mappedStatement = mappedStatements.get(id);
      return mappedStatement;
    }catch (Exception e){
      return null;
    }
  }

  public boolean hasStatement(String statementName, boolean validateIncompleteStatements) {
    if (validateIncompleteStatements) {
      buildAllStatements();
    }
    return mappedStatements.containsKey(statementName);
  }

  public static class StrictMap<V> extends ConcurrentHashMap<String, V> {
    private static final long serialVersionUID = -4950446264854982944L;
    @SuppressWarnings("unused")
    private final String name;

    public StrictMap(String name, int initialCapacity, float loadFactor) {
      super(initialCapacity, loadFactor);
      this.name = name;
    }

    public StrictMap(String name, int initialCapacity) {
      super(initialCapacity);
      this.name = name;
    }

    public StrictMap(String name) {
      super();
      this.name = name;
    }

    public StrictMap(String name, Map<String, ? extends V> m) {
      super(m);
      this.name = name;
    }

    @SuppressWarnings("unchecked")
    public V put(String key, V value) {
      // if (containsKey(key)) {
      // throw new IllegalArgumentException(name + " already contains value for " + key);
      // }
      if (key.contains(".")) {
        final String shortKey = getShortName(key);
        if (super.get(shortKey) == null) {
          super.put(shortKey, value);
        } else {
          super.put(shortKey, (V) new Ambiguity(shortKey));
        }
      }
      return super.put(key, value);
    }

    public V get(Object key) {
      V value = super.get(key);
      // if (value == null) {
      // throw new IllegalArgumentException(name + " does not contain value for " + key);
      // }
      // if (value instanceof Ambiguity) {
      // throw new IllegalArgumentException(((Ambiguity) value).getSubject() + " is ambiguous in "
      // + name
      // + " (try using the full name including the namespace, or rename one of the entries)");
      // }
      return value;
    }

    private String getShortName(String key) {
      final String[] keyParts = key.split("\\.");
      return keyParts[keyParts.length - 1];
    }

    protected static class Ambiguity {
      private final String subject;

      public Ambiguity(String subject) {
        this.subject = subject;
      }

      public String getSubject() {
        return subject;
      }
    }
  }
}

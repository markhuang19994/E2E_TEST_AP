package db.search;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/12, MarkHuang,new
 * </ul>
 * @since 2018/2/12
 */
public interface Search {
    LinkedHashMap<String, Object> getBy();

    void setBy(LinkedHashMap<String, Object> by);

    void setBy(String column, Object value);

    LinkedHashMap<String, Object[]> getIn();

    void setIn(String column, Object... in);

    String[] getOrderBy();

    void setOrderBy(String... orderBy);

    boolean getOrder();

    void setOrder(boolean order);

    LinkedHashMap<String, Object> getLike();

    void setLike(String column, Object val);

    void setBetween(String column, Object value1, Object value2);

    LinkedHashMap<String, Object[]> getBetween();
}

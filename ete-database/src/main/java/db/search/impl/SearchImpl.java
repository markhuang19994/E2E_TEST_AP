package db.search.impl;

import db.search.Search;

import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/12, MarkHuang,new
 * </ul>
 * @since 2018/2/12
 */
public class SearchImpl implements Search {
    private LinkedHashMap<String, Object> by = new LinkedHashMap<>();
    private LinkedHashMap<String, Object> like = new LinkedHashMap<>();
    private LinkedHashMap<String, Object[]> in = new LinkedHashMap<>();
    private LinkedHashMap<String, Object[]> between = new LinkedHashMap<>();
    private String[] orderBy;
    private boolean order;

    public LinkedHashMap<String, Object> getBy() {
        return by;
    }

    public void setBy(LinkedHashMap<String, Object> by) {
        this.by = by;
    }

    public void setBy(String column, Object value) {
        this.by.put(column, value);
    }

    public LinkedHashMap<String, Object[]> getIn() {
        return in;
    }

    public void setIn(String column, Object... in) {
        this.in.put(column, in);
    }

    public String[] getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String... orderBy) {
        this.orderBy = orderBy;
    }

    public boolean getOrder() {
        return order;
    }

    public void setOrder(boolean order) {
        this.order = order;
    }

    public LinkedHashMap<String, Object> getLike() {
        return like;
    }

    public void setLike(String column, Object val) {
        this.like.put(column, val);
    }

    public LinkedHashMap<String, Object[]> getBetween() {
        return between;
    }

    public void setBetween(String column, Object value1, Object value2) {
        this.between.put(column, new Object[]{value1, value2});
    }

    @Override
    public String toString() {
        return "SearchImpl{" +
                "by=" + by +
                ", like=" + like +
                ", in=" + in +
                ", between=" + between +
                ", orderBy=" + Arrays.toString(orderBy) +
                ", order=" + order +
                '}';
    }
}

package com.db.sql.impl;

import com.db.search.Search;
import com.db.sql.GenSql;

import java.util.*;

/**
 * This class can generate sql by search
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/12, MarkHuang,new
 * </ul>
 * @since 2018/2/12
 */
public class GenSqlImpl implements GenSql {

    private Search search;

    public GenSqlImpl(Search search) {
        this.search = search;
    }

    /**
     * generate sql by search
     * @return Map
     */
    public Map<String, List<Object>> generateSql() {
        Map<String, List<Object>> map = new HashMap<>();
        List<Object> list = new ArrayList<Object>();
        StringBuilder sb = new StringBuilder(500);
        boolean whereFlag = true;
        sb.append("\n").append(" SELECT *").append("\n").append(" FROM ").append("${TableName}");
        LinkedHashMap<String, Object> by = search.getBy();
        for (String key : search.getBy().keySet()) {
            sb.append("\n").append(" AND ").append(key).append(" = ").append(" ?");
            list.add(by.get(key));
        }
        LinkedHashMap<String, Object[]> between = search.getBetween();
        for (String key : between.keySet()) {
            sb.append("\n").append(" AND ").append(key).append(" BETWEEN ").append(" ?")
                    .append(" AND ").append("?");
            list.add(between.get(key)[0]);
            list.add(between.get(key)[1]);
        }
        LinkedHashMap<String, Object[]> ins = search.getIn();
        for (String key : ins.keySet()) {
            sb.append("\n").append(" AND ").append(key).append(" IN (");
            for (Object obj : ins.get(key)) {
                sb.append("? ,");
                list.add(obj);
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append(")");
        }
        LinkedHashMap<String, Object> like = search.getLike();
        for (Object val : like.keySet()) {
            sb.append("\n").append(" AND ").append(val).append(" LIKE ").append(" ? ");
            list.add("%" + like.get(val) + "%");
        }
        String[] orderBys = search.getOrderBy();
        if (orderBys != null) {
            sb.append("\n").append(" ORDER BY ");
            for (String orderBy : orderBys) {
                sb.append("?").append(", ");
                list.add(orderBy);
            }
            sb.delete(sb.length() - 2, sb.length());
            if (search.getOrder()) sb.append(" ASC");
            else sb.append(" DESC");
        }
        map.put(sb.toString().replaceFirst("AND", "WHERE"), list);
        return map;
    }


}

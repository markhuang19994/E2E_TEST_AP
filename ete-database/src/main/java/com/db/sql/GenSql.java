package com.db.sql;

import java.util.List;
import java.util.Map;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/12, MarkHuang,new
 * </ul>
 * @since 2018/2/12
 */
public interface GenSql {
    Map<String, List<Object>> generateSql();
}

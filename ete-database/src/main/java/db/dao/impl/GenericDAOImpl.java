package db.dao.impl;

import db.GenericRowMapper;
import db.dao.GenericDAO;
import db.search.Search;
import db.search.impl.SearchImpl;
import db.sql.impl.GenSqlImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class support sql find generic type List
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/8, MarkHuang,new
 * </ul>
 * @since 2018/2/8
 */
@Component
public class GenericDAOImpl<T> implements GenericDAO<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Find all data in table
     * @param cls model class
     * @return List
     */
    @Cacheable(value="DBCache",keyGenerator = "keyGenerator")
    public List<T> findAll(Class<T> cls) {
        GenericRowMapper<T> genericRowMapper = new GenericRowMapper<T>(cls);
        return jdbcTemplate.query(
                "SELECT * FROM " + cls.getSimpleName(), genericRowMapper);
    }

    /**
     * Find data in table by condition where column equal condition
     * @param column column in table
     * @param value condition value
     * @param cls model class
     * @return List
     */
    @Cacheable(value="DBCache",keyGenerator = "keyGenerator")
    public List<T> findBy(String column, Object value, Class<T> cls) {
        Search search = new SearchImpl();
        search.setBy(column, value);
        return findSearch(search, cls);
    }

    /**
     * Find data by condition where column is in array
     * @param column column in table
     * @param value condition array
     * @param cls model class
     * @return List
     */
    @Cacheable(value="DBCache",keyGenerator = "keyGenerator")
    public List<T> findIn(String column, Object[] value, Class<T> cls) {
        Search search = new SearchImpl();
        search.setBy(column, value);
        return findSearch(search, cls);
    }


    /**
     * Use search class to find data
     * @param search search template
     * @param cls model class
     * @return List
     * @see Search
     */
    @Cacheable(value="DBCache",keyGenerator = "keyGenerator")
    public List<T> findSearch(Search search, Class<T> cls) {
        GenSqlImpl genSql = new GenSqlImpl(search);
        Map<String, List<Object>> sqlMap = genSql.generateSql();
        String sql = "";
        Object[] obj = null;
        for (String sqlInMap : sqlMap.keySet()) {
            sql = sqlInMap;
            obj = sqlMap.get(sqlInMap).toArray();
        }
        return jdbcTemplate.query(sql.replace("${TableName}", cls.getSimpleName()).toUpperCase()
                , new GenericRowMapper<T>(cls), obj);
    }

    /**
     * Find data by custom sql
     * @param sql custom sql
     * @param cls model class
     * @return List
     */
    @Cacheable(value="DBCache",keyGenerator = "keyGenerator")
    public List<T> find(String sql, Class<T> cls) {
        return jdbcTemplate.query(sql, new GenericRowMapper<T>(cls));
    }


    /**
     * Update table data in model
     * @param model model
     * @return row be influences
     */
    public int update(T model) {
        Class<?> cls = model.getClass();
        Map<String, Object> map = genUpdateAndInsertMap(model);
        List<Object> valList = new ArrayList<>();
        StringBuilder updateSql = new StringBuilder(500);
        updateSql.append("UPDATE ").append(model.getClass().getSimpleName().toUpperCase())
                .append(" SET ");
        for (String column : map.keySet()) {
            valList.add(map.get(column));
            updateSql.append(column).append(" = ?, ");
        }
        updateSql.delete(updateSql.length() - 2, updateSql.length());
        return jdbcTemplate.update(updateSql.toString(), valList.toArray());
    }

    /**
     * Insert table data in model
     * @param model model
     * @return row be influences
     */
    public int insert(T model) {
        Class<?> cls = model.getClass();
        Map<String, Object> map = genUpdateAndInsertMap(model);
        List<Object> valList = new ArrayList<>();
        StringBuilder insertSql = new StringBuilder(500);
        StringBuilder valSql = new StringBuilder(500);
        insertSql.append("INSERT INTO ").append(model.getClass().getSimpleName().toUpperCase())
                .append(" (");
        valSql.append("VALUES (");
        for (String column : map.keySet()) {
            valList.add(map.get(column));
            insertSql.append(column).append(", ");
            valSql.append("?, ");
        }
        insertSql.delete(insertSql.length() - 2, insertSql.length()).append(") ");
        valSql.delete(valSql.length() - 2, valSql.length()).append(") ");
        insertSql.append(valSql);
        return jdbcTemplate.update(insertSql.toString(), valList.toArray());
    }

    /**
     * Generate Update and insert sql
     * @param model
     * @return
     */
    private Map<String, Object> genUpdateAndInsertMap(T model) {
        Class<?> cls = model.getClass();
        Map<String, Object> map = new LinkedHashMap<>();
        for (Field field : cls.getDeclaredFields()) {
            String name = field.getName();
            Method method = null;
            try {
                method = cls.getDeclaredMethod(
                        "get" + name.toUpperCase().charAt(0) + name.substring(1));
                method.setAccessible(true);
                Object invoke = method.invoke(model);
                if (invoke != null) map.put(field.getName(), invoke);
            } catch (NoSuchMethodException | IllegalAccessException
                    | InvocationTargetException e) {
                LOGGER.debug(e.toString());
            }
        }
        return map;
    }

}

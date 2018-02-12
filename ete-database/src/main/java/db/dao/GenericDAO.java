package db.dao;

import java.util.List;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/12, MarkHuang,new
 * </ul>
 * @since 2018/2/12
 */
public interface GenericDAO<T> {
    List<T> findAll(Class<T> cls);

    int update(T model);

    int insert(T model);
}

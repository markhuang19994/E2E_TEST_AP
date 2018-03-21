package com.db.dao;

import java.util.List;

/**
 * @author AndyChen
 * @version <ul>
 *          <li>2018/3/20 AndyChen,new
 *          </ul>
 * @since 2018/3/20
 */
public interface GenericJpaDAO<T> {
    List<T> getAll(Class<T> cls);

    T getById(Object id, Class<T> cls);

    void insert(T model);

    void update(T model);
}

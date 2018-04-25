package com.db.dao.impl;

import com.db.dao.GenericJpaDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author AndyChen
 * @version <ul>
 *          <li>2018/3/20 AndyChen,new
 *          </ul>
 * @since 2018/3/20
 */
@Transactional
@Repository
public class GenericJpaDAOImpl<T> implements GenericJpaDAO<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericJpaDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getAll(Class<T> cls) {
        String tableName = cls.getAnnotation(Table.class).name();
        Query nativeQuery = entityManager.createNativeQuery("SELECT * FROM " + tableName, cls);
        return nativeQuery.getResultList();
    }

    @Override
    public T getById(Object id, Class<T> cls) {
        return entityManager.find(cls, id);
    }

    @Override
    public void insert(Object model) {
        entityManager.persist(model);
    }

    @Override
    public void update(Object model) {
//        String queryId = this.getModelId((T) model);
//
//        T dbModel = entityManager.find((Class<T>) model.getClass(), queryId);
//        if(dbModel == null){
//            entityManager.persist(model);
//        } else {
//            this.setUpdateData(dbModel, (T) model);
//
//        }
        entityManager.merge(model);
    }

    private String getModelId(T model){
        for(Field field: model.getClass().getDeclaredFields()){
            if(field.getAnnotation(Id.class)!= null){
                String methodName = "get" + String.valueOf(field.getName().charAt(0)).toUpperCase() + field.getName().substring(1);
                Method method = null;
                try {
                    method = model.getClass().getDeclaredMethod(methodName);
                    return (String) method.invoke(model);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    LOGGER.warn("",e);
                }
            }
        }
        return "";
    }

    /**
     * 將Update的model資料轉移到DB查出來的model
     * @param modelFromDb
     * @param updateModel
     */
    private void setUpdateData(T modelFromDb, T updateModel){
        for(Field field: updateModel.getClass().getDeclaredFields()){
            if(field.getAnnotation(Id.class) == null && field.getAnnotation(Column.class)!= null){
                String getMethodName = "get" + String.valueOf(field.getName().charAt(0)).toUpperCase() + field.getName().substring(1);
                String setMethodName = "set" + String.valueOf(field.getName().charAt(0)).toUpperCase() + field.getName().substring(1);
                Method getMethod = null;
                Method setMethod = null;
                try {
                    getMethod = updateModel.getClass().getDeclaredMethod(getMethodName);
                    setMethod = updateModel.getClass().getDeclaredMethod(setMethodName, String.class);
                    String value = (String) getMethod.invoke(updateModel);
                    setMethod.invoke(modelFromDb, value);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    LOGGER.warn("",e);
                }
            }
        }
    }

}

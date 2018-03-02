package com.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class can generate row map for all com.model
 *
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/11, MarkHuang,new
 * </ul>
 * @since 2018/2/11
 */
public class GenericRowMapper<T> implements RowMapper<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericRowMapper.class);

    private Class<T> cls;

    public GenericRowMapper(Class<T> cls) {
        this.cls = cls;
    }

    /**
     * Fill all result in com.model instance by java reflect, if  field in com.model type not <br>
     * String, Integer, sqlDate, Boolean, BigDecimal, we use Object as it's type
     *
     * @param rs sql result set
     * @param i  row number
     * @return T com.model type
     * @throws SQLException
     */
    @Override
    public T mapRow(ResultSet rs, int i) throws SQLException {
        T model = null;
        try {
            model = cls.newInstance();
            for (Field field : cls.getDeclaredFields()) {
                String name = field.getName();
                String fieldType = field.getType().getName();
                Method method = null;

                String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                if (fieldType.contains("String")) {
                    method = model.getClass().getDeclaredMethod(methodName, String.class);
                    method.setAccessible(true);
                    method.invoke(model, rs.getString(name));
                } else if (fieldType.contains("Integer")) {
                    method = model.getClass().getDeclaredMethod(methodName, Integer.class);
                    method.setAccessible(true);
                    method.invoke(model, rs.getInt(name));
                } else if (fieldType.contains("Date")) {
                    method = model.getClass().getDeclaredMethod(methodName, Date.class);
                    method.setAccessible(true);
                    method.invoke(model, rs.getDate(name));
                } else if (fieldType.contains("BigDecimal")) {
                    method = model.getClass().getDeclaredMethod(methodName, BigDecimal.class);
                    method.setAccessible(true);
                    method.invoke(model, rs.getBigDecimal(name));
                } else if (fieldType.contains("Boolean")) {
                    method = model.getClass().getDeclaredMethod(methodName, Boolean.class);
                    method.setAccessible(true);
                    method.invoke(model, rs.getBoolean(name));
                }  else {
                    method = model.getClass().getDeclaredMethod(methodName, Object.class);
                    method.setAccessible(true);
                    method.invoke(model, rs.getObject(name));
                }
            }
        } catch (InstantiationException | IllegalAccessException
                | NoSuchMethodException | InvocationTargetException e) {
            LOGGER.debug(e.toString());
        }
        return model;
    }

    /**
     * Convert camel name to uppercase slash name empName-->EMP_NAME
     *
     * @param name camel name
     * @return uppercase slash name
     */
    private String toSlashName(String name) {
        StringBuilder sb = new StringBuilder();
        for (char c : name.toCharArray()) {
            if ((int) c < 97) sb.append("_");
            sb.append(c);
        }
        return sb.toString().toUpperCase();
    }
}



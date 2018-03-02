package com.data;

import com.db.dao.impl.GenericDAOImpl;
import com.model.UserTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/8, MarkHuang,new
 * </ul>
 * @since 2018/2/8
 */
@Component
public class UserData {
    private final GenericDAOImpl<UserTable> genericDAOImpl;

    @Autowired
    public UserData(GenericDAOImpl<UserTable> genericDAOImpl) {
        this.genericDAOImpl = genericDAOImpl;
    }

    public List getData2() {
        return genericDAOImpl.findAll(UserTable.class);
    }

    public void update(UserTable userTable) {
        genericDAOImpl.update(userTable);
    }

    public void insert(UserTable userTable) {
        genericDAOImpl.insert(userTable);
    }

    public List find() {
        return genericDAOImpl.findBy("id", 1, UserTable.class);
    }
    public List findAll() {
        return genericDAOImpl.findAll(UserTable.class);
    }
}

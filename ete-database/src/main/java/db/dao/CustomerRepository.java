package db.dao;

import com.sun.xml.bind.v2.TODO;
import model.UserTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
public class CustomerRepository<T> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<UserTable> findAll() {

        List<UserTable> result = jdbcTemplate.query(
                "SELECT id, name, age, email, password FROM userTable",
                (rs, rowNum) -> new UserTable(rs.getString("id"),
                        rs.getString("name"), rs.getInt("age")
                        , rs.getString("email"), rs.getString("password"))
        );

        return result;
    }

    //TODO get filed by generic type
    @Deprecated
    public List<T> findAll2(String sql) {

//        List<T> result = jdbcTemplate.query(
//                "SELECT id, name, age, email, password FROM userTable",
//                (rs, rowNum) -> new UserTable(rs.getString("id"),
//                        rs.getString("name"), rs.getInt("age")
//                        , rs.getString("email"), rs.getString("password"))
//        );

        return null;
    }

}

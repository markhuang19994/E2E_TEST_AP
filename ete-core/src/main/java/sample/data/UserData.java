package sample.data;

import db.dao.CustomerRepository;
import model.UserTable;
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
    @Autowired
    private CustomerRepository customerRepository;
    public List<UserTable> getData() {
        return customerRepository.findAll();
    }
}

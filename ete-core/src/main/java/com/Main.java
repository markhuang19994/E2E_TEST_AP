package com;

import com.data.UserData;
import com.db.dao.impl.GenericDAOImpl;
import com.model.UserTable;
import com.springconfig.ApplicationContextProvider;
import com.test.SelDemo;
import com.test.SelDemo2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/7, MarkHuang,new
 * </ul>
 * @since 2018/2/7
 */
@SpringBootApplication
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Main.class, args);
        LOGGER.debug("Ok this com is already complete@@");
//        LOGGER.debug("User Email = " + ApplicationContextProvider.getBean(UserData.class)
//                .getData2().get(0).toString());

//        HashMap<String, String> stringStringHashMap = new HashMap<>();
//        stringStringHashMap.put("msg", "Html Mail");
//        ApplicationContextProvider.getBean(EmailServiceImpl.class).sendHtmlMessage(
//                "mail/report_mail"
//                , stringStringHashMap
//                , "markhuang1994@gmail.com"
//                , "MyTestMail2"
//        );
//        UserTable userTable = new UserTable();
//        userTable.setId("65f499a8f5fd4d84b79a1cb190592643");
//        userTable.setAge(23);
//        userTable.setEmail("Markhaung1994@gmail.com");
//        userTable.setName("Mark");
//        userTable.setPassword("p@ssw0rd");
//        UserData bean = ApplicationContextProvider.getBean(UserData.class);
//        long l = System.currentTimeMillis();
//        int list = ApplicationContextProvider.getBean(GenericDAOImpl.class).update(userTable);
//        System.out.println(System.currentTimeMillis() - l+"ms");
//
//        SelDemo2 selDemo2 = ApplicationContextProvider.getBean("selDemo2", SelDemo2.class);
//        selDemo2.test001();
    }

}

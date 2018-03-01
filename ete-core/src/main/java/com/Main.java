package com;

import com.springconfig.ApplicationContextProvider;
import com.test.SelDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

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
//        userTable.setAge(21);
//        userTable.setEmail("Markhaung1994@gmail.com");
//        userTable.setName("Mark");
//        userTable.setPassword("p@ssw0rd");
//        UserData bean = ApplicationContextProvider.getBean(UserData.class);
//        long l = System.currentTimeMillis();
//        List list = bean.find();
//        System.out.println(System.currentTimeMillis() - l+"ms");

        SelDemo selDemo = ApplicationContextProvider.getBean("selDemo", SelDemo.class);
        selDemo.test001();
    }

}

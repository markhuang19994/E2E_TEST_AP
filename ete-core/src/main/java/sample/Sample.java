package sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sample.data.UserData;
import sample.springconfig.ApplicationContextProvider;

import java.io.IOException;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/7, MarkHuang,new
 * </ul>
 * @since 2018/2/7
 */
@SpringBootApplication
public class Sample {
    private static final Logger LOGGER = LoggerFactory.getLogger(Sample.class);
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Sample.class, args);
//        Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome " +
//                "http://localhost:8088/ete01/show"});
        LOGGER.debug("Ok this sample is already complete@@");
        LOGGER.debug("User Email = "+ ApplicationContextProvider.getBean(UserData.class)
        .getData().get(0).toString());





    }

}

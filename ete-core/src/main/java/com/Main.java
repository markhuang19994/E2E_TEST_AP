package com;

import com.db.repository.PageDataRepository;
import com.model.PageData;
import com.springconfig.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;

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
        LOGGER.debug("Oh the awesome project is already started!");
    }

}

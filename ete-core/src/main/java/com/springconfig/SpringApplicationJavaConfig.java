package com.springconfig;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/7, MarkHuang,new
 * </ul>
 * @since 2018/2/7
 */
@Configuration
@EnableCaching
@ComponentScan("db")
@ComponentScan("model")
public class SpringApplicationJavaConfig {
}

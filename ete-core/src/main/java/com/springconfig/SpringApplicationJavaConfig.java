package com.springconfig;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/7, MarkHuang,new
 * </ul>
 * @since 2018/2/7
 */
@Configuration
@EnableCaching
@EnableAspectJAutoProxy
@ComponentScan("db")
@ComponentScan("driver")
@ComponentScan("model")
@PropertySource(value = "",ignoreResourceNotFound = true)
public class SpringApplicationJavaConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringApplicationJavaConfig.class);

    /**
     * Generate custom key className+methodName+allParameters toString so
     * secure every key is unique parameter var3 should override toString method
     * @return KeyGenerator
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return (Object var1, Method var2, Object... var3) -> {
                StringBuilder sb = new StringBuilder();
                sb.append(var1.getClass().getName());
                sb.append(var2.getName());
                for (Object param : var3) {
                    sb.append(param.toString());
                }
                return sb.toString();
        };
    }

    @Value("${web.driver.chrome.option:''}")
    String driverOption;

    @Value("${web.driver:'chrome'}")
    String driver;

    @Value("${web.driver.path:" +
            "ete-core/src/main/resources/webdriver/chromedriver.exe}")
    String driverPath;

    @Value("${web.driver.window.width:1920}")
    int windowWidth;

    @Value("${web.driver.window.height:1080}")
    int windowHeight;

    @Value("${web.driver.window.position.x:0}")
    int positionX;

    @Value("${web.driver.window.position.y:0}")
    int positionY;

    @Value("${web.driver.implicitly.wait:10}")
    int driverWait;

    @Lazy
    @Bean
    public WebDriver webDriver(){
        System.setProperty("webdriver.chrome.driver", driverPath);
        WebDriver webDriver;
        ChromeOptions chromeOptions = new ChromeOptions();
        String[] options = driverOption.split("\\|");
        for (String option : options) {
            chromeOptions.addArguments(option);
        }
        switch (driver.toLowerCase()){
            case "chrome" :
                webDriver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                webDriver = new FirefoxDriver();
                break;
            case "ie":
                webDriver = new InternetExplorerDriver();
                break;
            default:
                webDriver = new ChromeDriver(chromeOptions);
        }
        webDriver.manage().window().setSize(new Dimension(windowWidth,windowHeight));
        webDriver.manage().window().setPosition(new Point(positionX,positionY));
        webDriver.manage().timeouts().implicitlyWait(driverWait, TimeUnit.SECONDS);
        LOGGER.debug("web driver is loaded: "+webDriver.getClass().getSimpleName());
        return webDriver;
    }

    @Lazy
    @Bean
    @Qualifier("Script")
    public JavascriptExecutor javascriptExecutor(WebDriver driver){
        JavascriptExecutor js = null;
        if (driver instanceof JavascriptExecutor) {
            js = (JavascriptExecutor) driver;
        }
        return js;
    }


}

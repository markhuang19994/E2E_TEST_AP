package com.driver;

import com.google.common.base.Predicate;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/27, MarkHuang,new
 * </ul>
 * @since 2018/2/27
 */
public class WebDriverUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverUtil.class);

    /**
     * Load page until document ready and sleep 1 sec when complete
     * @param driver webDriver
     * @param url page url
     */
    public static void loadPage(WebDriver driver, String url) {
        new WebDriverWait(driver, 20).until(ExpectedConditions.urlToBe(url));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        new WebDriverWait(driver, 20).until((Predicate<WebDriver>) (WebDriver driver2) -> {
            return ((JavascriptExecutor) driver2) != null
                    && ((JavascriptExecutor) driver2).executeScript("return document.readyState").equals("complete");
        });
        sleep(1000);
    }

    /**
     * Screen shot current screen
     * @param driver webDriver
     * @param pngName png file name
     */
    public static void screenShot(WebDriver driver, String pngName) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File screenshotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotFile, new File(System.getProperty("user.dir")
                    + File.separator + "screen_shot" + File.separator + pngName + ".png"));
        } catch (IOException e) {
            LOGGER.debug(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Analyze current web console log and print in Logger
     * @param driver webDriver
     */
    public static void analyzeLog(WebDriver driver) {
        StringBuilder sb = new StringBuilder();
        driver.manage().logs().get(LogType.BROWSER)
                .forEach(logEntry -> sb.append(logEntry.toString() + "\n"));
        LOGGER.debug("browser console log start >>> \n");
        LOGGER.debug("\n" + sb.toString());
        LOGGER.debug("browser console log end <<< \n");
    }

    /**
     * Sleep few sec
     * @param time time/sec
     */
    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

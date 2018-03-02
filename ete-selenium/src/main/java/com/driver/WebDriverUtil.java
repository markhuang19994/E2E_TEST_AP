package com.driver;

import com.google.common.base.Predicate;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/27, MarkHuang,new
 * </ul>
 * @since 2018/2/27
 */
public class WebDriverUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverUtil.class);

    public static void loadPage(WebDriver driver, String url) {
        new WebDriverWait(driver, 20).until(ExpectedConditions.urlToBe(url));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        new WebDriverWait(driver, 20).until((Predicate<WebDriver>) (WebDriver driver2) -> {
            return ((JavascriptExecutor) driver2) != null
                    && ((JavascriptExecutor) driver2).executeScript("return document.readyState").equals("complete");
        });
        sleep(1000);
    }

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

    public static void analyzeLog(WebDriver driver) {
        StringBuilder sb = new StringBuilder();
        driver.manage().logs().get(LogType.BROWSER)
                .forEach(logEntry -> sb.append(logEntry.toString() + "\n"));
        LOGGER.debug("\n" + sb.toString());
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

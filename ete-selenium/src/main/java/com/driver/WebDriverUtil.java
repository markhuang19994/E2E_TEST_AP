package com.driver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import java.awt.image.BufferedImage;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/27, MarkHuang,new
 * </ul>
 * @since 2018/2/27
 */
public class WebDriverUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverUtil.class);

    private WebDriverUtil() {

    }

    /**
     * Load page until document ready and sleep 1 sec when complete
     *
     * @param driver webDriver
     * @param url    page url
     */
    public static void loadPage(WebDriver driver, String url) {
        new WebDriverWait(driver, 20).until(ExpectedConditions.urlToBe(url));
        new WebDriverWait(driver, 20).until((WebDriver driver2) -> driver2 != null
                && ((JavascriptExecutor) driver2).executeScript("return document.readyState").equals("complete"));
        sleep(1000);
    }


    /**
     * Screen shot current screen
     *
     * @param driver webDriver
     */
    public static BufferedImage screenShot(WebDriver driver) {
        Screenshot fpScreenshot = new AShot().shootingStrategy(
                ShootingStrategies.viewportRetina(100, 0, 0, 2)).takeScreenshot(driver);
        return fpScreenshot.getImage();
    }

    /**
     * Analyze current web console log and print in Logger
     *
     * @param driver webDriver
     */
    public static void analyzeLog(WebDriver driver) {
        StringBuilder sb = new StringBuilder();
        driver.manage().logs().get(LogType.BROWSER)
                .forEach(logEntry -> sb.append(logEntry.toString()).append("\n"));
        sb.append("browser console log start >>> \n");
        sb.append("\n").append(sb.toString());
        sb.append("browser console log end <<< \n");
        LOGGER.debug(sb.toString());
    }

    public static void executeScript(WebDriver driver, String script) {
        try {
            ((JavascriptExecutor) driver).executeScript(script);
        } catch (Exception e) {
            LOGGER.warn("", e);
        }
    }

    /**
     * Sleep few sec
     *
     * @param time time/sec
     */
    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            LOGGER.warn("", e);
            Thread.currentThread().interrupt();
        }
    }
}

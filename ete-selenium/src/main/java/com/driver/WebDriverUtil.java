package com.driver;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

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
     * @param driver  webDriver
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
        LOGGER.debug("browser console log start >>> \n");
        LOGGER.debug("\n" + sb.toString());
        LOGGER.debug("browser console log end <<< \n");
    }

    public static void executeScript(WebDriver driver, String script) {
        try {
            ((JavascriptExecutor) driver).executeScript(script);
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    /**
     * for JUnit test
     *
     * @return
     */
    public static WebDriver getWebDriver() {

        String driverPath = "src/test/resources/webdriver/chromedriver";
        String driverOption = "--disable-gpu";
        String driver = "chrome";
        int windowWidth = 3840;
        int windowHeight = 2160;
        int positionX = 5;
        int positionY = 0;
        int driverWait = 10;

        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            System.setProperty("webdriver.chrome.driver", driverPath);
        } else {
            System.setProperty("webdriver.chrome.com.driver", driverPath + ".exe");
        }
        WebDriver webDriver;
        ChromeOptions chromeOptions = new ChromeOptions();
        for (String option : driverOption.split("\\|")) {
            chromeOptions.addArguments(option);
        }
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.INFO);
        caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        caps.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        switch (driver.toLowerCase()) {
            case "chrome":
                webDriver = new ChromeDriver(caps);
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
        webDriver.manage().window().setSize(new Dimension(windowWidth, windowHeight));
        webDriver.manage().window().setPosition(new Point(positionX, positionY));
        webDriver.manage().timeouts().implicitlyWait(driverWait, TimeUnit.SECONDS);
        return webDriver;
    }


}

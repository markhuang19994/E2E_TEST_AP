package com.test;

import driver.WebDriverUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.apache.tomcat.util.net.SSLHostConfigCertificate.Type.EC;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/26, MarkHuang,new
 * </ul>
 * @since 2018/2/26
 */
@Lazy
@Service
public class SelDemo {
    private final
    WebDriver driver;

    private final
    JavascriptExecutor js;

    @Autowired
    public SelDemo(WebDriver driver, @Qualifier("Script") JavascriptExecutor js) {
        this.driver = driver;
        this.js = js;
    }

    public void test001() {
        indexPage();
        step1();
        step2();
        step3();
        step4();
        step5();
        step6();
    }

    public void indexPage() {
        driver.get("http://localhost:8089/extfunc02/page/index");
        WebDriverUtil.loadPage(driver, "http://localhost:8089/extfunc02/page/index");
        WebElement accountId = driver.findElement(By.id("ino"));
        WebElement cardId = driver.findElement(By.id("cno"));
        accountId.clear();
        accountId.sendKeys("A117463321");
        cardId.clear();
        cardId.sendKeys("4563180400000002");
        WebElement indexPageSubmit = driver.findElement(By.id("p"));
        indexPageSubmit.click();
    }

    public void step1() {
        WebDriverUtil.loadPage(driver, "http://localhost:8089/extfunc02/page/step1?_ar=1");
        WebElement otpCheckBox = driver.findElement(By.id("agr"));
        WebElement inputOtp = driver.findElement(By.id("otp"));
        WebElement sendbtn = driver.findElement(By.id("sendbtn"));
        js.executeScript("arguments[0].checked=true", otpCheckBox);
        js.executeScript("$('#agr').click()");
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable((By.id("sendbtn"))));
        inputOtp.sendKeys("111111");
        js.executeScript("$('#sendbtn').click()");
    }

    public void step2() {
        WebDriverUtil.loadPage(driver, "http://localhost:8089/extfunc02/page/step2?_ar=1");
        js.executeScript("$('div [data-target=\"freeoption\"]').click()");
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated((By.cssSelector("#ftenor"))));
        WebDriverUtil.screenShot(driver, "Step2Timeout");
        WebDriverUtil.sleep(2000);
        js.executeScript("$('#ftenor').val(36)");
        js.executeScript("$('#agrBox').click()");
        js.executeScript("$('#sendbtn').click()");
    }

    public void step3() {
        WebDriverUtil.loadPage(driver, "http://localhost:8089/extfunc02/page/step3?_ar=1");
        js.executeScript("$('#tuition').click()");
        js.executeScript("$('#jobNC').click()");
        WebDriverUtil.sleep(1000);
        js.executeScript("$('#sendbtn').click()");
    }

    public void step4() {
        WebDriverUtil.loadPage(driver, "http://localhost:8089/extfunc02/page/step3-1?_ar=1");
        js.executeScript("$('#agrBox').click()");
        WebDriverUtil.sleep(1000);
        js.executeScript("$('#sendbtn').click()");
    }

    public void step5() {
        WebDriverUtil.loadPage(driver, "http://localhost:8089/extfunc02/page/step4?_ar=1");
        js.executeScript("$('input[type=\"checkbox\"]').click()");
        js.executeScript("$('#check_year option').eq(1).prop('selected',true)");
        js.executeScript("$('#check_month option').eq(1).prop('selected',true)");
        js.executeScript("$('#check_day option').eq(1).prop('selected',true)");
        WebDriverUtil.sleep(1000);
        js.executeScript("$('.mfp-close').click()");
        js.executeScript("$('#firmbtn').click()");
        WebDriverUtil.sleep(1000);
        js.executeScript("$('#sendbtn').click()");
    }

    public void step6() {
        WebDriverUtil.loadPage(driver, "http://localhost:8089/extfunc02/page/step5?_ar=1");
        js.executeScript("$('input[type=\"checkbox\"]').click()");
        WebElement inputOtp = driver.findElement(By.id("otp"));
        inputOtp.sendKeys("111111");
        WebDriverUtil.sleep(1000);
        js.executeScript("$('#sendbtn').click()");
        WebDriverUtil.sleep(1000);
        js.executeScript("$('#firmbtn').click()");

    }

}

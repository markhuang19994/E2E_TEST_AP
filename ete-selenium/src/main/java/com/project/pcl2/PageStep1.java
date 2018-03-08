package com.project.pcl2;

import com.driver.WebDriverUtil;
import com.google.gson.JsonObject;
import com.model.PageData;
import com.service.PageTestService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author AndyChen
 * @version <ul>
 * <li>2018/3/07, AndyChen,new
 * </ul>
 * @since 2018/3/07
 */
public class PageStep1 extends PageTestService {

    public PageStep1(WebDriver driver, PageTestService nextService, PageData pageData){
        super(driver, nextService, pageData);
    }

    public PageStep1(WebDriver driver, PageData pageData){
        super(driver, pageData);
    }

    @Override
    protected void setDataToPage() {
        JsonObject jsonObject = new JsonObject();

        WebElement accountId = driver.findElement(By.id("ino"));
        WebElement cardId = driver.findElement(By.id("cno"));
        accountId.clear();
        accountId.sendKeys("A100000378");
        cardId.clear();
        cardId.sendKeys("4563180400000001");
        WebElement indexPageSubmit = driver.findElement(By.id("p"));
        WebDriverUtil.analyzeLog(driver);
        indexPageSubmit.click();

    }
}

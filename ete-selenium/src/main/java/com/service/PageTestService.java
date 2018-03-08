package com.service;

import com.driver.WebDriverUtil;
import com.model.JsonData;
import com.model.PageData;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;


/**
 * @author AndyChen
 * @version <ul>
 * <li>2018/3/07, AndyChen,new
 * </ul>
 * @since 2018/3/07
 */
public abstract class PageTestService {

    protected WebDriver driver;

    protected JavascriptExecutor js;

    private PageTestService nextService;

    protected String jsonStr;

    protected PageData pageData;

    protected List<JsonData> jsonDatas;

    protected boolean isUseCommonSetData = false;


    public PageTestService(WebDriver driver, PageTestService nextService, PageData pageData) {
        this.nextService = nextService;
        this.setConstructor(driver, pageData);
    }

    public PageTestService(WebDriver driver, PageData pageData){
        this.setConstructor(driver, pageData);
    }

    private void setConstructor(WebDriver driver, PageData pageData){
        this.driver = driver;
        this.js = (JavascriptExecutor)driver;
        this.pageData = pageData;
        this.jsonDatas = pageData.getJsonDatas();
    }

    protected void setUseCommonSetData(boolean isUseCommonSetData){
        this.isUseCommonSetData = isUseCommonSetData;
    }

    public void next(){
        this.nextService.testPage();
    }

    public void testPage(){
        this.loadPage();
        this.setDataToPage(isUseCommonSetData);
        WebDriverUtil.analyzeLog(driver);
        if(nextService != null)
            this.next();
    }

    private void loadPage(){
        driver.get(pageData.getPageUrl());
        WebDriverUtil.loadPage(driver, pageData.getPageUrl());
    }

    /**
     * common function for set data to page, only one setDataToPage will be call
     * @param isUseCommonSetData
     */
    protected void setDataToPage(boolean isUseCommonSetData){
        if(isUseCommonSetData){
            for(JsonData data : jsonDatas){
                String inputId = data.getId();
                String value = data.getValue();
                JsonData.DataType dataType = data.getDataType();
                String beforeScript = data.getBeforeScript();
                if(!"".equals(beforeScript)){
                    js.executeScript(beforeScript);
                }
                switch (dataType){
                    case TEXT:
                        WebElement textEle = driver.findElement(By.id(inputId));
                        textEle.sendKeys(value);
                        break;
                    case RADIO:
                        WebElement radioEle = driver.findElement(By.id(inputId));
                        radioEle.click();
                        break;
                    case SELECT:
                        WebElement selectEle = driver.findElement(By.id(inputId));
                        //TODO impl
                        break;
                }
            }
        } else {
            this.setDataToPage();
        }
    }

    /**
     * customize special function for set data to page
     */
    abstract protected void setDataToPage();




}

package com.service;

import com.driver.WebDriverUtil;
import com.model.JsonData;
import com.model.PageData;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * @author AndyChen
 * @version <ul>
 * <li>2018/3/07, AndyChen,new
 * </ul>
 * @since 2018/3/07
 */
public abstract class PageTestService {

    private static final Logger logger = LoggerFactory.getLogger(PageTestService.class);

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

    public PageTestService(WebDriver driver, PageData pageData) {
        this.setConstructor(driver, pageData);
    }

    private void setConstructor(WebDriver driver, PageData pageData) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.pageData = pageData;
        this.jsonDatas = pageData.getJsonDatas();
    }

    protected void setUseCommonSetData(boolean isUseCommonSetData) {
        this.isUseCommonSetData = isUseCommonSetData;
    }

    private void next(String urlPrefix) {
        this.nextService.testPage(urlPrefix);
    }

    public void testPage(boolean isIndex, String urlPrefix) {
        if (isIndex)
            driver.get(urlPrefix + pageData.getPageUrl());
        this.testPage(urlPrefix);
    }

    public void testPage(String urlPrefix) {
        this.loadPage(urlPrefix);
        this.setDataToPageUsePageOwnWay(isUseCommonSetData);
        WebDriverUtil.analyzeLog(driver);
        if (nextService != null)
            this.next(urlPrefix);
    }

    private void loadPage(String urlPrefix) {
        WebDriverUtil.loadPage(driver, urlPrefix + pageData.getPageUrl());
    }

    /**
     * common function for set data to page, only one setDataToPageUsePageOwnWay will be call
     *
     * @param isUseCommonSetData
     */
    protected void setDataToPageUsePageOwnWay(boolean isUseCommonSetData) {
        if (isUseCommonSetData) {
            for (JsonData data : jsonDatas) {
                this.setDataToPageUsePageOwnWay(data, 200);
            }
        } else {
            this.setDataToPageUsePageOwnWay();
        }
        this.goNextBth(js);
    }

    protected void executeScript(String script) {
        WebDriverUtil.executeScript(this.driver, script);
    }

    protected void setDataToPageUsePageOwnWay(JsonData data) {
        this.setDataToPageUsePageOwnWay(data, 0);
    }

    protected void setDataToPageUsePageOwnWay(JsonData data, long waitTimeToNext) {
        String inputId = data.getId();
        String value = data.getValue() != null ? data.getValue().trim() : "";
        String dataType = data.getDataType() != null ? data.getDataType().trim() : "";
        String beforeScript = data.getBeforeScript() != null ? data.getBeforeScript().trim() : ";";

        //不論是否有給id,script都會執行
        js.executeScript(beforeScript);

        if (inputId == null) {
            logger.warn("found a no id data, skip...");
            return;
        }

        try {
            switch (dataType) {
                case JsonData.TEXT:
                    WebElement textEle = driver.findElement(By.id(inputId));
                    js.executeScript("$('#" + inputId + "').val('')");
                    textEle.sendKeys(value);
                    break;
                case JsonData.RADIO:
                    WebElement radioEle = driver.findElement(By.id(inputId));
                    radioEle.click();
//                    Thread.sleep(200);
                    break;
                case JsonData.SELECT:
                    WebElement selectEle = driver.findElement(By.id(inputId));
                    List<WebElement> options = selectEle.findElements(By.tagName("option"));
                    for (WebElement optEle : options) {
                        if (value.equals(optEle.getText().trim())) {
                            optEle.click();
                            break;
                        }
                    }
                    Thread.sleep(300);
                    break;
                case JsonData.CHECKBOX:
                    WebElement checkboxEle = driver.findElement(By.id(inputId));
                    checkboxEle.click();
                    break;
                default:
                    logger.warn("for " + inputId + ", there is no valid data type.");
            }
        } catch (NoSuchElementException e) {
            logger.warn("[setDataToPageUsePageOwnWay] could not find element by id: " + inputId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (waitTimeToNext > 0) {
            try {
                Thread.sleep(waitTimeToNext);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void goNextBth(JavascriptExecutor js) {
        js.executeScript("$(\"[iisiTest='next']\").click()");
    }


    /**
     * customize special function for set data to page
     */
    abstract protected void setDataToPageUsePageOwnWay();


}

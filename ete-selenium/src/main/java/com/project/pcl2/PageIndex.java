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
public class PageIndex extends PageTestService {

    public PageIndex(WebDriver driver, PageTestService nextService, PageData pageData){
        super(driver, nextService, pageData);
        super.setUseCommonSetData(true);
    }

    public PageIndex(WebDriver driver, PageData pageData){
        super(driver, pageData);
        super.setUseCommonSetData(true);
    }

    @Override
    protected void setDataToPageUsePageOwnWay() {

    }
}

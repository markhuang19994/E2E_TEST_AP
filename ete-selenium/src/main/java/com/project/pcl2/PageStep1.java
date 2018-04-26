package com.project.pcl2;

import com.model.JsonData;
import com.model.PageData;
import com.service.PageTestService;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * @author AndyChen
 * @version <ul>
 * <li>2018/3/07, AndyChen,new
 * </ul>
 * @since 2018/3/07
 */
public class PageStep1 extends PageTestService {

    public PageStep1(WebDriver driver, PageTestService nextService, PageData pageData) {
        super(driver, nextService, pageData);
    }

    public PageStep1(WebDriver driver, PageData pageData) {
        super(driver, pageData);
    }

    @Override
    protected void setDataToPageUsePageOwnWay() {
        executeScript("$('.blockUI').remove()");

        for (JsonData data : jsonDatas) {
            this.setDataToPageUsePageOwnWay(data, 200);
        }
    }

    @Override
    protected void goNextBth(JavascriptExecutor js) {
        js.executeScript("$('#sendbtn').click()");
    }
}

package com.project.pcl2;

import com.model.JsonData;
import com.model.PageData;
import com.service.PageTestService;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * @author AndyChen
 * @version <ul>
 *          <li>2018/3/07, AndyChen,new
 *          </ul>
 * @since 2018/3/07
 */
public class PageStep1 extends PageTestService {

    public PageStep1(WebDriver driver, PageTestService nextService, PageData pageData) {
        super(driver, nextService, pageData);
//        super.setUseCommonSetData(true);
    }

    public PageStep1(WebDriver driver, PageData pageData) {
        super(driver, pageData);
//        super.setUseCommonSetData(true);
    }

    @Override
    protected void setDataToPageUsePageOwnWay() {

        List<WebElement> blockUi = driver.findElements(By.className("blockUI"));
        new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfAllElements(blockUi));

        for (JsonData data : jsonDatas) {
            this.setDataToPageUsePageOwnWay(data, 200);
        }
    }

    @Override
    protected void goNextBth(JavascriptExecutor js) {
        js.executeScript("$('#sendbtn').click()");
    }
}

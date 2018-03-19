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

/**
 * @author AndyChen
 * @version <ul>
 *          <li>2018/3/07, AndyChen,new
 *          </ul>
 * @since 2018/3/07
 */
public class PageStep3 extends PageTestService {

    public PageStep3(WebDriver driver, PageTestService nextService, PageData pageData) {
        super(driver, nextService, pageData);
    }

    public PageStep3(WebDriver driver, PageData pageData) {
        super(driver, pageData);
    }

    @Override
    protected void setDataToPageUsePageOwnWay() {

        for (JsonData data : jsonDatas) {

            //wait account radio btn clickable
            String otherAccountRadioId = "otherAccount";
            if(otherAccountRadioId.equals(data.getId())){
                WebElement radioEle = driver.findElement(By.id(otherAccountRadioId));
                new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(radioEle));
            }

            this.setDataToPageUsePageOwnWay(data, 200);
        }
    }

    @Override
    protected void goNextBth(JavascriptExecutor js) {
        js.executeScript("$('#sendbtn').click()");
    }
}

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
//        super.setUseCommonSetData(true);
    }

    public PageStep1(WebDriver driver, PageData pageData) {
        super(driver, pageData);
//        super.setUseCommonSetData(true);
    }

    @Override
    protected void setDataToPageUsePageOwnWay() {
//         等blockUi全部跑完會遇到要等2X秒的情況,通常blockUi沒跑完也能操作,所以先註解掉
//        List<WebElement> blockUi = driver.findElements(By.className("blockUI"));
//        if (blockUi != null) {
//            new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfAllElements(blockUi));
//        }
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

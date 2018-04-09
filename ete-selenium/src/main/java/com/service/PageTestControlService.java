package com.service;

import com.model.PageData;
import com.model.TestCase;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author AndyChen
 * @version <ul>
 * <li>2018/3/08, AndyChen,new
 * </ul>
 * @since 2018/3/08
 */
@Service
public class PageTestControlService {

    public void startTest(WebDriver driver, TestCase testCase) throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        try {
            List<PageData> pageDatas = testCase.getPageDatas();
            ArrayList<PageTestService> testServices = this.initAllClasses(driver, testCase, pageDatas);
            //TODO change logic
            testServices.get(0).testPage(true, "http://localhost:8090");
//        testServices.forEach(PageTestService::testPage);
        } finally {
//            driver.quit();
        }
    }

    private ArrayList<PageTestService> initAllClasses(WebDriver driver, TestCase testCase, List<PageData> pageDatas) throws
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Class<? extends PageTestService>[] classess = testCase.getPageServiceClasses();
        ArrayList<PageTestService> testServices = new ArrayList<>();
        PageTestService[] temp = new PageTestService[pageDatas.size()];

        for (int i = pageDatas.size() - 1; i >= 0; i--) {
            if (i == pageDatas.size() - 1) {
                temp[i] = this.initClasses(driver, pageDatas.get(i), classess[i]);
            } else {
                PageTestService preTestService = temp[i + 1];
                temp[i] = this.initClasses(driver, preTestService, pageDatas.get(i), classess[i]);
            }
        }
        testServices.addAll(Arrays.asList(temp));
        return testServices;
    }

    private PageTestService initClasses(WebDriver driver, PageData pageData, Class<? extends PageTestService> initClass) throws
            NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class[] oParam = new Class[2];
        oParam[0] = WebDriver.class;
        oParam[1] = PageData.class;
        Constructor constructor = initClass.getConstructor(oParam);
        Object[] paramObjs = new Object[2];
        paramObjs[0] = driver;
        paramObjs[1] = pageData;

        //透過Constructor產生物件
        return (PageTestService) constructor.newInstance(paramObjs);
    }

    private PageTestService initClasses(WebDriver driver, PageTestService nextService, PageData pageData,
                                        Class<? extends PageTestService> initClass) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        Class[] oParam = new Class[3];
        oParam[0] = WebDriver.class;
        oParam[1] = PageTestService.class;
        oParam[2] = PageData.class;
        Constructor constructor = initClass.getConstructor(oParam);

        Object[] paramObjs = new Object[3];
        paramObjs[0] = driver;
        paramObjs[1] = nextService;
        paramObjs[2] = pageData;

        //透過Constructor產生物件
        return (PageTestService) constructor.newInstance(paramObjs);
    }


}

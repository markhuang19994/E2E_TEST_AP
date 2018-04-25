package com.service.impl;

import com.model.TestCase;
import com.service.BrowserControlService;
import com.service.PageTestControlService;
import com.springconfig.ApplicationContextProvider;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/4/9, MarkHuang,new
 * </ul>
 * @since 2018/4/9
 */
@Service
@Scope(value = "prototype")
public class BrowserControlServiceImpl implements BrowserControlService {
    private final PageTestControlService pageTestControlService;

    @Autowired
    public BrowserControlServiceImpl(PageTestControlService pageTestControlService) {
        this.pageTestControlService = pageTestControlService;
    }

    @Override
    public void startTestProcedureWithSelenium(TestCase testCase, String hostUrl) {
        WebDriver driver = ApplicationContextProvider.getBean("webDriver", WebDriver.class);
        try {
            pageTestControlService.startTest(driver, hostUrl, testCase);
        } catch (NoSuchMethodException | InstantiationException
                | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startTestProcedure(TestCase testCase, String virtualType, String hostUrl) {
        if (SELENIUM.equals(virtualType)) {
            startTestProcedureWithSelenium(testCase, hostUrl);
        } else {
            //Todo puppeteer
        }
    }
}

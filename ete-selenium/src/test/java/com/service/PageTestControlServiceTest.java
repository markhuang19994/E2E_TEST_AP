package com.service;

import com.driver.WebDriverUtil;
import com.model.PageData;
import com.model.TestCase;
import com.project.pcl2.PageStep1;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by VALLA on 2018/3/15.
 */
public class PageTestControlServiceTest {

    WebDriver webDriver;

    TestCase testCase;

    @Before
    public void setUp() throws Exception {
        webDriver = WebDriverUtil.getWebDriver();
        testCase = new TestCase();

        Class[] pageServiceClasses = {PageStep1.class};
        testCase.setPageServiceClasses(pageServiceClasses);

        String jsonStr = "[{\n" +
                "        \"id\": \"ino\",\n" +
                "        \"value\": \"M123456789\",\n" +
                "        \"dataType\": \"RADIO\",\n" +
                "        \"order\": \"1\",\n" +
                "        \"beforeScript\" : \"\"\n" +
                "    }, {\n" +
                "        \"id\": \"cno\",\n" +
                "        \"value\": \"4563180400000001\",\n" +
                "        \"dataType\": \"RADIO\",\n" +
                "        \"order\": \"2\",\n" +
                "        \"beforeScript\" : \"\"\n" +
                "    },{\n" +
                "        \"id\": \"test\",\n" +
                "        \"value\": \"4563180400000001\",\n" +
                "        \"dataType\": \"RADIO\",\n" +
                "        \"order\": \"3\",\n" +
                "        \"beforeScript\" : \"\"\n" +
                "    }]\n";
        System.out.println(jsonStr);
        PageData pageData = new PageData();
        pageData.setPageUrl("http://localhost:8081/extfunc02/page/index");
        pageData.setDataJsonStr(jsonStr);

        ArrayList<PageData> pageDatas = new ArrayList<>();
        pageDatas.add(pageData);

        testCase.setPageDatas(pageDatas);

    }

    @Test
    public void startTest() throws Exception {
        PageTestControlService pageTestControlService = new PageTestControlService();
        pageTestControlService.startTest(webDriver,testCase);
    }

}
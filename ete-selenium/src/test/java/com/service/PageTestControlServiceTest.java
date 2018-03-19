package com.service;

import com.driver.WebDriverUtil;
import com.model.PageData;
import com.model.TestCase;
import com.project.pcl2.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
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

        Class[] pageServiceClasses = {PageIndex.class, PageStep1.class, PageStep2.class, PageStep3.class,
                PageStep3_1.class, PageStep4.class, PageStep5.class};
        testCase.setPageServiceClasses(pageServiceClasses);


        ArrayList<PageData> pageDatas = this.getPageDatas();

        testCase.setPageDatas(pageDatas);

    }

    private ArrayList<PageData> getPageDatas() throws IOException {
        ArrayList<PageData> pageDatas = new ArrayList<>();

        //index page
        String indexJsonStr = "[{\n" +
                "        \"id\": \"ino\",\n" +
                "        \"value\": \"A100000378\",\n" +
                "        \"dataType\": \"text\",\n" +
                "        \"beforeScript\" : \"\"\n" +
                "    }, {\n" +
                "        \"id\": \"cno\",\n" +
                "        \"value\": \"4563180400000001\",\n" +
                "        \"dataType\": \"text\",\n" +
                "        \"beforeScript\" : \"\"\n" +
                "    }]\n";
        System.out.println(indexJsonStr);
        PageData pageData = new PageData();
        pageData.setPageUrl("http://localhost:8081/extfunc02/page/index");
        pageData.setDataJsonStr(indexJsonStr);
        pageDatas.add(pageData);

        //step1 page
        String page1JsonStr = "[{\n" +
                "        \"id\": \"agr\",\n" +
                "        \"value\": \"\",\n" +
                "        \"dataType\": \"radio\",\n" +
                "        \"beforeScript\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"otp\",\n" +
                "        \"value\": \"111111\",\n" +
                "        \"dataType\": \"text\",\n" +
                "        \"beforeScript\": \"\"\n" +
                "    }\n" +
                "]";
        System.out.println(page1JsonStr);
        PageData step1PageData = new PageData();
        step1PageData.setPageUrl("http://localhost:8081/extfunc02/page/step1?_ar=1");
        step1PageData.setDataJsonStr(page1JsonStr);
        pageDatas.add(step1PageData);

        //step2 page
        String page2JsonStr = "[{\n" +
                "        \"id\": \"agrBox\",\n" +
                "        \"value\": \"\",\n" +
                "        \"dataType\": \"checkbox\",\n" +
                "        \"beforeScript\": \"\"\n" +
                "    }\n" +
                "]";
        System.out.println(page2JsonStr);
        PageData step2PageData = new PageData();
        step2PageData.setPageUrl("http://localhost:8081/extfunc02/page/step2?_ar=1");
        step2PageData.setDataJsonStr(page2JsonStr);
        pageDatas.add(step2PageData);

        //step3 page
        String page3JsonStr = "[{\n" +
                "        \"id\": \"otherAccount\",\n" +
                "        \"value\": \"\",\n" +
                "        \"dataType\": \"radio\",\n" +
                "        \"beforeScript\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"otherAccountTitle\",\n" +
                "        \"value\": \"021-花旗（台灣）銀行\",\n" +
                "        \"dataType\": \"select\",\n" +
                "        \"beforeScript\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"otherBranchCode\",\n" +
                "        \"value\": \"0030-高雄分行\",\n" +
                "        \"dataType\": \"select\",\n" +
                "        \"beforeScript\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"otherAccountNumber\",\n" +
                "        \"value\": \"2545465484\",\n" +
                "        \"dataType\": \"text\",\n" +
                "        \"beforeScript\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"paytax\",\n" +
                "        \"value\": \"\",\n" +
                "        \"dataType\": \"radio\",\n" +
                "        \"beforeScript\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"jobNC\",\n" +
                "        \"value\": \"\",\n" +
                "        \"dataType\": \"radio\",\n" +
                "        \"beforeScript\": \"\"\n" +
                "    }\n" +
                "]";
        System.out.println(page3JsonStr);
        PageData step3PageData = new PageData();
        step3PageData.setPageUrl("http://localhost:8081/extfunc02/page/step3?_ar=1");
        step3PageData.setDataJsonStr(page3JsonStr);
        pageDatas.add(step3PageData);

        //step3_1 page
        String page3_1JsonStr = "[{\n" +
                "    \"id\": \"agrBox\",\n" +
                "    \"value\": \"\",\n" +
                "    \"dataType\": \"checkbox\",\n" +
                "    \"beforeScript\": \"\"\n" +
                "}\n" +
                "]";
        System.out.println(page3_1JsonStr);
        PageData step3_1PageData = new PageData();
        step3_1PageData.setPageUrl("http://localhost:8081/extfunc02/page/step3-1?_ar=1");
        step3_1PageData.setDataJsonStr(page3_1JsonStr);
        pageDatas.add(step3_1PageData);

        //step4 page
        String page4JsonStr = "[\n" +
                "{\n" +
                "    \"id\": \"check_year\",\n" +
                "    \"value\": \"106\",\n" +
                "    \"dataType\": \"select\",\n" +
                "    \"beforeScript\": \"$(':checkbox').click()\"\n" +
                "},\n" +
                "{\n" +
                "    \"id\": \"check_month\",\n" +
                "    \"value\": \"1\",\n" +
                "    \"dataType\": \"select\",\n" +
                "    \"beforeScript\": \"\"\n" +
                "},\n" +
                "{\n" +
                "    \"id\": \"check_day\",\n" +
                "    \"value\": \"1\",\n" +
                "    \"dataType\": \"select\",\n" +
                "    \"beforeScript\": \"\"\n" +
                "}\n" +
                "]";
        System.out.println(page4JsonStr);
        PageData step4PageData = new PageData();
        step4PageData.setPageUrl("http://localhost:8081/extfunc02/page/step4?_ar=1");
        step4PageData.setDataJsonStr(page4JsonStr);
        pageDatas.add(step4PageData);

        //step5 page
        String page5JsonStr = "[\n" +
                "{\n" +
                "    \"id\": \"otp\",\n" +
                "    \"value\": \"111111\",\n" +
                "    \"dataType\": \"text\",\n" +
                "    \"beforeScript\": \"\"\n" +
                "}\n" +
                "]";
        System.out.println(page5JsonStr);
        PageData step5PageData = new PageData();
        step5PageData.setPageUrl("http://localhost:8081/extfunc02/page/step5?_ar=1");
        step5PageData.setDataJsonStr(page5JsonStr);
        pageDatas.add(step5PageData);


        return pageDatas;
    }

    @Test
    public void startTest() throws Exception {
        PageTestControlService pageTestControlService = new PageTestControlService();
        pageTestControlService.startTest(webDriver,testCase);
    }

}
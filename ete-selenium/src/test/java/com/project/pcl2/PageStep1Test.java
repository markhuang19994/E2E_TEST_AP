package com.project.pcl2;

import com.driver.WebDriverUtil;
import com.model.PageData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

/**
 * Created by AndyChen on 2018/3/8.
 */
public class PageStep1Test {

    PageStep1 step1;

    WebDriver driver;

    PageData pageData;



    @Before
    public void setUp() throws Exception {

        driver = WebDriverUtil.getWebDriver();
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
//        String jsonStr = "{\n" +
//                "    \"datas\": [{\n" +
//                "        \"id\": \"ino\",\n" +
//                "        \"value\": \"M123456789\",\n" +
//                "        \"dataType\": \"text\",\n" +
//                "        \"order\": \"1\",\n" +
//                "        \"beforeScript\" : \"\"\n" +
//                "    }, {\n" +
//                "        \"id\": \"cno\",\n" +
//                "        \"value\": \"4563180400000001\",\n" +
//                "        \"dataType\": \"text\",\n" +
//                "        \"order\": \"2\",\n" +
//                "        \"beforeScript\" : \"\"\n" +
//                "    },{\n" +
//                "        \"id\": \"test\",\n" +
//                "        \"value\": \"4563180400000001\",\n" +
//                "        \"dataType\": \"text\",\n" +
//                "        \"order\": \"3\",\n" +
//                "        \"beforeScript\" : \"\"\n" +
//                "    }]\n" +
//                "}";
        pageData = new PageData();
        pageData.setPageUrl("http://localhost:8081/extfunc02/page/index");
        pageData.setDataJsonStr(jsonStr);

        step1 = new PageStep1(driver, pageData);
        step1.testPage();

    }

    @After
    public void tearDown(){
        // nothing to clenaup
    }

    @Test
    public void set_delete_day_then_clean_file() throws Exception {
    }




}
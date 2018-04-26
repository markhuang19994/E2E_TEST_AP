package com.testconfig.factory;

import com.model.PageData;
import com.model.Project;
import com.model.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/4/25, MarkHuang,new
 * </ul>
 * @since 2018/4/25
 */
public class DbDummyDataFactory {

    private DbDummyDataFactory() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(DbDummyDataFactory.class);
    public static final String TEST_CASE_NAME = "THE TEST CASE FOR TEST";
    public static final String PROJECT_NAME = "THE PROJECT FOR TEST";

    public static PageData getDummyPageData() {
        PageData pageData = new PageData();
        try {
            pageData.setDataJsonStr("[{\"id\":\"agr\",\"value\":\"\",\"dataType\":\"radio\",\"beforeScript\":\"\"}," +
                    "{\"id\":\"otp\",\"value\":\"111111\",\"dataType\":\"text\",\"beforeScript\":\"\"}]");
        } catch (IOException e) {
            LOGGER.warn("", e);
        }
        pageData.setPageUrl("/step1?_ar=1");
        pageData.setTestCaseName(TEST_CASE_NAME);
        return pageData;
    }

    public static TestCase getDummyTestCase() {
        TestCase testCase = new TestCase();
        testCase.setPageDatas(Collections.emptyList());
        testCase.setTestCaseName(TEST_CASE_NAME);
        testCase.setPageServiceClasses(null);
        testCase.setProjectName(PROJECT_NAME);
        return testCase;
    }

    public static Project getDummyProject() {
        Project project = new Project();
        project.setProjectName(PROJECT_NAME);
        project.setTestClassNames("asd");
        project.setUrlCollection("qwe");
        project.setTestCases(Collections.emptyList());
        return project;
    }
}

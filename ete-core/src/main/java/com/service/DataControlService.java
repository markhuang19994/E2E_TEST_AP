package com.service;

import com.model.PageData;
import com.model.Project;
import com.model.TestCase;

import java.util.List;

/**
 * @author AndyChen
 * @version <ul>
 *          <li>2018/3/21 AndyChen,new
 *          </ul>
 * @since 2018/3/21
 */
public interface DataControlService {

    Project getProject(String projectName);

    List<String> getProjectName();

    TestCase getTestCase(String testCaseName);

    void saveTestCase(TestCase testCase);

    void deletePageData(String testCaseName);

    TestCase assembleTestCase(TestCase testCase, List<PageData> pageDatas);

    List<TestCase> loadAllTestCaseFromProject(String projectName);

}

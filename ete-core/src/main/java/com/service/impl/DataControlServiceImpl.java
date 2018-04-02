package com.service.impl;


import com.db.repository.PageDataRepository;
import com.db.repository.ProjectRepository;
import com.db.repository.TestCaseRepository;
import com.model.PageData;
import com.model.Project;
import com.model.TestCase;
import com.service.DataControlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/3/26, MarkHuang,new
 * </ul>
 * @since 2018/3/26
 */
@Service
public class DataControlServiceImpl implements DataControlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataControlServiceImpl.class);

    private final
    ProjectRepository projectRepository;

    private final
    TestCaseRepository testCaseRepository;

    private final
    PageDataRepository pageDataRepository;

    @Autowired
    public DataControlServiceImpl(ProjectRepository projectRepository, TestCaseRepository testCaseRepository, PageDataRepository pageDataRepository) {
        this.projectRepository = projectRepository;
        this.testCaseRepository = testCaseRepository;
        this.pageDataRepository = pageDataRepository;
    }

    @Override
    public Project getProject(String projectName) {
        return projectRepository.findOne(projectName);
    }

    @Override
    public List<String> getProjectName() {
        return projectRepository.getProjectName();
    }

    @Override
    public TestCase getTestCase(String testCaseName) {
        return testCaseRepository.findOne(testCaseName);
    }

    @Override
    public void saveTestCase(TestCase testCase) {
        List<PageData> pageDatas = testCase.getPageDatas();
        testCaseRepository.save(testCase);
        pageDataRepository.save(pageDatas);
    }

    @Override
    public void deletePageData(String testCaseName) {
        int i = pageDataRepository.deleteAllByTestCaseName(testCaseName);
        LOGGER.debug("刪除" + testCaseName + "的pageData" + i + "筆");
    }

    @Override
    public TestCase assembleTestCase(TestCase testCase, List<PageData> pageDatas) {
        return null;
    }

    @Override
    public List<TestCase> loadAllTestCaseFromProject(String projectName) {
        return testCaseRepository.findAllByProjectName(projectName);
    }
}

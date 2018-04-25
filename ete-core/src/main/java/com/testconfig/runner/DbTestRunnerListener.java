package com.testconfig.runner;

import com.db.repository.PageDataRepository;
import com.db.repository.ProjectRepository;
import com.db.repository.TestCaseRepository;
import com.springconfig.ApplicationContextProvider;
import com.testconfig.factory.DbDummyDataFactory;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import org.springframework.stereotype.Component;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/4/24, MarkHuang,new
 * </ul>
 * @since 2018/4/24
 */
@Component
public class DbTestRunnerListener extends RunListener {

    @Override
    public void testRunStarted(Description description) throws Exception {
        super.testRunStarted(description);
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        ApplicationContextProvider.getBean("pageDataRepository", PageDataRepository.class)
                .deleteAllByTestCaseName(DbDummyDataFactory.TEST_CASE_NAME);

        ApplicationContextProvider.getBean("testCaseRepository", TestCaseRepository.class)
                .deleteAllByProjectName(DbDummyDataFactory.PROJECT_NAME);

        ApplicationContextProvider.getBean("projectRepository", ProjectRepository.class)
                .deleteAllByProjectName(DbDummyDataFactory.PROJECT_NAME);
    }
}

package core;

import com.Main;
import com.db.repository.PageDataRepository;
import com.db.repository.ProjectRepository;
import com.db.repository.TestCaseRepository;
import com.model.PageData;
import com.model.Project;
import com.model.TestCase;
import com.service.DataControlService;
import com.testconfig.runner.DbTestRunner;
import com.testconfig.factory.DbDummyDataFactory;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/11, MarkHuang,new
 * </ul>
 * @since 2018/2/11
 */
@RunWith(DbTestRunner.class)
@SpringBootTest(classes = Main.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceTest {

    @Autowired
    DataControlService dataControlServiceImpl;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TestCaseRepository testCaseRepository;

    @Autowired
    PageDataRepository pageDataRepository;

    private PageData pageData = DbDummyDataFactory.getDummyPageData();
    private TestCase testCase = DbDummyDataFactory.getDummyTestCase();
    private Project project = DbDummyDataFactory.getDummyProject();

    @Test
    public void test7_DataControlService() {
        String testCaseName = DbDummyDataFactory.TEST_CASE_NAME;
        String projectName = DbDummyDataFactory.PROJECT_NAME;

        projectRepository.save(project);
        testCase.setPageDatas(Collections.singletonList(pageData));
        dataControlServiceImpl.saveTestCase(testCase);

        dataControlServiceImpl.deletePageData(testCaseName);
        testCase.setPageDatas(Collections.emptyList());

        TestCase dbTestCase = dataControlServiceImpl.getTestCase(testCaseName);
        Assert.assertEquals(testCase, dbTestCase);

        List<String> projectNames = dataControlServiceImpl.getProjectName();
        Assert.assertTrue(projectNames.contains(projectName));

        testCaseRepository.deleteAllByProjectName(projectName);
        Project dbProject = dataControlServiceImpl.getProject(projectName);
        Assert.assertEquals(project, dbProject);
    }

}
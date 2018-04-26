package database;

import com.Main;
import com.db.repository.PageDataRepository;
import com.db.repository.ProjectRepository;
import com.db.repository.TestCaseRepository;
import com.model.PageData;
import com.model.Project;
import com.model.TestCase;
import com.service.DataControlService;
import com.springconfig.ApplicationContextProvider;
import com.testconfig.runner.DbTestRunner;
import com.testconfig.factory.DbDummyDataFactory;
import net.sf.ehcache.Cache;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
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
public class RepositoryTest {

    @Autowired
    DataControlService dataControlServiceImpl;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TestCaseRepository testCaseRepository;

    @Autowired
    PageDataRepository pageDataRepository;

    @Autowired
    KeyGenerator keyGenerator;

    private String testCaseName = DbDummyDataFactory.TEST_CASE_NAME;
    private String projectName = DbDummyDataFactory.PROJECT_NAME;
    private PageData pageData = DbDummyDataFactory.getDummyPageData();
    private TestCase testCase = DbDummyDataFactory.getDummyTestCase();
    private Project project = DbDummyDataFactory.getDummyProject();
    private Map<String, Object> cacheKeyMap = new HashMap<>();
    private Cache cache;

    @Before
    public void setup() throws IOException {
        CacheManager cacheManager = ApplicationContextProvider.getBean("cacheManager", CacheManager.class);
        cache = (Cache) cacheManager.getCache("DBCache").getNativeCache();
    }

    private Method getMethod(String methodName, Object target, Class... var) {
        Method method = null;
        try {
            method = target.getClass().getMethod(methodName, var);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

    @Test
    public void test1_Project() throws IOException {
        Project save = projectRepository.save(project);
        Assert.assertEquals(project, save);

        Project one = projectRepository.findOne(project.getProjectName());
        Assert.assertEquals(project, one);

        Object key = keyGenerator
                .generate(projectRepository, getMethod("findOne", projectRepository, Serializable.class), projectName);
        cacheKeyMap.put("PROJECT", key);
        Assert.assertTrue(cache.get(key).getValue() instanceof Project);
        Assert.assertEquals(project, cache.get(key).getValue());

        List<String> dbProjectName = projectRepository.getProjectName();
        Assert.assertTrue(dbProjectName.contains(projectName));
    }

    @Test
    public void test2_TestCase() {
        TestCase save = testCaseRepository.save(testCase);
        Assert.assertEquals(testCase, save);

        List<TestCase> allByProjectName = testCaseRepository.findAllByProjectName(projectName);
        Assert.assertEquals(1, allByProjectName.size());
        Assert.assertEquals(testCase, allByProjectName.get(0));

        Object key = keyGenerator
                .generate(testCaseRepository, getMethod("findAllByProjectName", testCaseRepository, String.class), projectName);
        cacheKeyMap.put("TEST_CASE", key);
        List value = ((ArrayList) cache.get(key).getValue());
        Object o = value.get(0);
        Assert.assertEquals(1, value.size());
        Assert.assertTrue(o instanceof TestCase);
        Assert.assertEquals(testCase, o);
    }

    @Test
    public void test3_PageData() {
        PageData save = pageDataRepository.save(pageData);
        Assert.assertEquals(pageData, save);

        List<PageData> pageDataByTestCaseName = pageDataRepository.findPageDataByTestCaseName(testCaseName);
        Assert.assertEquals(1, pageDataByTestCaseName.size());
        Assert.assertEquals(pageData, pageDataByTestCaseName.get(0));

        Object key = keyGenerator
                .generate(pageDataRepository, getMethod("findPageDataByTestCaseName", pageDataRepository, String.class), testCaseName);
        cacheKeyMap.put("PAGE_DATA", key);
        List value = ((ArrayList) cache.get(key).getValue());
        Object o = value.get(0);
        Assert.assertEquals(1, value.size());
        Assert.assertTrue(o instanceof PageData);
        Assert.assertEquals(pageData, o);
    }

    @Test
    public void test4_PageDataDelete() {
        int i = pageDataRepository.deleteAllByTestCaseName(testCaseName);
        Assert.assertEquals(1, i);
        Assert.assertNull(cache.get(cacheKeyMap.get("PAGE_DATA")));
        List<PageData> pageDataByTestCaseName = pageDataRepository.findPageDataByTestCaseName(testCaseName);
        Assert.assertEquals(pageDataByTestCaseName, Collections.emptyList());
    }

    @Test
    public void test5_TestCaseDelete() {
        int i = testCaseRepository.deleteAllByProjectName(projectName);
        Assert.assertEquals(1, i);
        Assert.assertNull(cache.get(cacheKeyMap.get("TEST_CASE")));
        List<TestCase> allByProjectNameEmpty = testCaseRepository.findAllByProjectName(projectName);
        Assert.assertEquals(allByProjectNameEmpty, Collections.emptyList());
    }

    @Test
    public void test6_ProjectDelete() {
        int i = projectRepository.deleteAllByProjectName(projectName);
        Assert.assertEquals(1, i);
        Assert.assertNull(cache.get(cacheKeyMap.get("PROJECT")));
        Project oneNull = projectRepository.findOne(project.getProjectName());
        Assert.assertNull(oneNull);
    }
}
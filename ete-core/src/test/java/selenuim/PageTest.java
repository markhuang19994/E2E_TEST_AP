package selenuim;

import com.Main;
import com.db.repository.TestCaseRepository;
import com.model.PageData;
import com.model.TestCase;
import com.project.pcl2.*;
import com.service.BrowserControlService;
import com.service.PageTestControlService;
import com.service.PageTestService;
import com.springconfig.ApplicationContextProvider;
import com.testconfig.util.TestUtil;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/4/25, MarkHuang,new
 * </ul>
 * @since 2018/4/25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PageTest {

    @Autowired
    BrowserControlService browserControlService;

    @Autowired
    private TestCaseRepository testCaseRepository;

    private TestCase testCase;
    private List<PageData> pageDatas;
    private WebDriver driver;
    private String urlPrefix;

    @Before
    public void setup() {
        testCase = testCaseRepository.findOne("PCL-Index-page-test01");
        Assume.assumeNotNull(testCase);
        Assume.assumeTrue(TestUtil.checkHost("172.16.1.13", 9443));
        WebDriver webDriver = ApplicationContextProvider.getBean("webDriver", WebDriver.class);
        try {
            webDriver.get("https://172.16.1.13:9443/extfunc02/page/index");
            webDriver.findElement(By.id("ino"));
        } catch (Exception e) {
            Assume.assumeNoException(e);
        } finally {
            webDriver.quit();
        }
        pageDatas = testCase.getPageDatas();
        urlPrefix = "http://localhost:8090/extfunc02/page";
        driver = ApplicationContextProvider.getBean("webDriver", WebDriver.class);
    }

    @After
    public void down() {
        driver.quit();
    }

    @Test
    public void test0_BrowserControlService() {
        browserControlService.startTestProcedureWithSelenium(testCase, "http://localhost:8090/extfunc02/page");
    }

    @Test
    public void test1_Step1Test() {
        PageTestService step1 = new PageStep1(driver, pageDatas.get(0));
        step1.testPage(true, urlPrefix);
    }

    @Test
    public void test2_Step2Test() {
        PageTestService step2 = new PageStep2(driver, pageDatas.get(1));
        step2.testPage(urlPrefix);
    }

    public void test3_Step3Test() {
        PageTestService step3 = new PageStep3(driver, pageDatas.get(2));
        step3.testPage(urlPrefix);
    }

    public void test4_Step3_1Test() {
        PageTestService step3_1 = new PageStep3_1(driver, pageDatas.get(3));
        step3_1.testPage(urlPrefix);
    }

    public void test4_Step4Test() {
        PageTestService step4 = new PageStep4(driver, pageDatas.get(4));
        step4.testPage(urlPrefix);
    }

    public void test5_Step5Test() {
        PageTestService step5 = new PageStep5(driver, pageDatas.get(5));
        step5.testPage(urlPrefix);
    }

}

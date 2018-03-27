package com;

import com.db.repository.PageDataRepository;
import com.model.PageData;
import com.springconfig.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;

import java.io.IOException;
import java.util.List;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/7, MarkHuang,new
 * </ul>
 * @since 2018/2/7
 */
@SpringBootApplication
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Main.class, args);
        LOGGER.debug("Oh the awesome project is already started!");
//        LOGGER.debug("User Email = " + ApplicationContextProvider.getBean(UserData.class)
//                .getData2().get(0).toString());

//        HashMap<String, String> stringStringHashMap = new HashMap<>();
//        stringStringHashMap.put("msg", "Html Mail");
//        ApplicationContextProvider.getBean(EmailServiceImpl.class).sendHtmlMessage(
//                "mail/report_mail"
//                , stringStringHashMap
//                , "markhuang1994@gmail.com"
//                , "MyTestMail2"
//        );
//        SelDemo2 selDemo2 = ApplicationContextProvider.getBean("selDemo2", SelDemo2.class);
//        selDemo2.test001();

//        PageDataRepository pageDataRepository = ApplicationContextProvider
//                .getBean("pageDataRepository", PageDataRepository.class);
//        TestCaseRepository testCaseRepository = ApplicationContextProvider
//                .getBean(TestCaseRepository.class);
//        ProjectRepository projectRepository = ApplicationContextProvider
//                .getBean(ProjectRepository.class);
//        PageData pageData = new PageData();
//        pageData.setPageUrl("www.leagueOfLegends.com");
//        pageData.setDataJsonStr("[{\"id\":\"e21e\",\"value\":\"qdasd\",\"dataType\":\"select\",\"beforeScript\":\"aczczxc\"}]");
//        pageData.setPageServiceClass("JsonData.class");
//        pageData.setTestCaseName("PCLTest5");
//        ArrayList<PageData> pageData1 = new ArrayList<>();
//        pageData1.add(pageData);
//        TestCase testCase = new TestCase();
//        testCase.setTestCaseName("PCLTest5");
//        testCase.setProjectName("anni");
//        ArrayList<TestCase> testCases = new ArrayList<>();
//        testCases.add(testCase);
//        Project project = new Project();
//        project.setProjectName("anni");
//        project.setTestClassNames("OhYaa");
//        project.setUrlCollection("asdasd");
//        projectRepository.save(project);
//        testCaseRepository.save(testCase);
//        pageDataRepository.save(pageData);


        PageDataRepository pageDataRepository = ApplicationContextProvider
                .getBean("pageDataRepository", PageDataRepository.class);
        List<PageData> all = pageDataRepository.findAll();
        List<PageData> all1 = pageDataRepository.findAll();
        PageData pageData = new PageData();
        pageData.setPageUrl("www.leagueOfLegends2.com");
        pageData.setDataJsonStr("[{\"id\":\"e21e\",\"value\":\"qdasd\",\"dataType\":\"select\",\"beforeScript\":\"aczczxc\"}]");
//        pageData.setPageServiceClass("JsonData.class");
        pageData.setTestCaseName("PCLTest5");
        pageDataRepository.save(pageData);
        CacheManager cacheManager = ApplicationContextProvider
                .getBean("cacheManager", CacheManager.class);

        List<PageData> all2 = pageDataRepository.findAll();
        List<PageData> all3 = pageDataRepository.findAll();
        all.forEach(System.out::print);

//        TestCaseRepository testCase = ApplicationContextProvider
//                .getBean("testCaseRepository", TestCaseRepository.class);
    }

}

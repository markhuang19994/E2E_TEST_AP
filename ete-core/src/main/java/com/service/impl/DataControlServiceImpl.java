package com.service.impl;

import com.db.repository.PageDataRepository;
import com.db.repository.TestCaseRepository;
import com.model.PageData;
import com.model.Project;
import com.model.TestCase;
import com.service.DataControlService;
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

    private final
    TestCaseRepository testCaseRepository;

    private final
    PageDataRepository pageDataRepository;

    @Autowired
    public DataControlServiceImpl(TestCaseRepository testCaseRepository, PageDataRepository pageDataRepository) {
        this.testCaseRepository = testCaseRepository;
        this.pageDataRepository = pageDataRepository;
    }

    @Override
    public Project getProject(String projectName) {
        return null;
    }

    @Override
    public TestCase getTestCase(String testCaseName) {
        return null;
    }

    @Override
    public void saveTestCase(TestCase testCase) {
        //由於model有添加Eager註解,所以testCase的pageDatas必須清除,然後分開儲存
        List<PageData> pageDatas = testCase.getPageDatas();
        try {
            testCase.setPageDatas(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        testCaseRepository.save(testCase);
        pageDataRepository.save(pageDatas);
    }

    @Override
    public void deletePageData(String testCaseName) {
        int i =pageDataRepository.deletePageDataByTestCaseName(testCaseName);
        System.err.println("本次刪除資料數: "+i);
    }

    @Override
    public TestCase assembleTestCase(TestCase testCase, List<PageData> pageDatas) {
        return null;
    }

    @Override
    public List<TestCase> loadAllTestCaseFromProject(String projectName) {
        return null;
    }
}

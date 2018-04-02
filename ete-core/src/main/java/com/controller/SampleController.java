package com.controller;

import com.db.repository.TestCaseRepository;
import com.model.PageData;
import com.model.TestCase;
import com.service.BrowserControlSerevice;
import com.service.impl.DataControlServiceImpl;
import com.springconfig.ApplicationContextProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/7, MarkHuang,new
 * </ul>
 * @since 2018/2/7
 */
@Controller
public class SampleController {
    //    @Autowired
    private BrowserControlSerevice browserCtrlService;

    private final DataControlServiceImpl dataControlServiceImpl;

    @Autowired
    public SampleController(DataControlServiceImpl dataControlServiceImpl) {
        this.dataControlServiceImpl = dataControlServiceImpl;
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String sampleMethod() {
        return "Hello E2E";
    }

    @RequestMapping("/data")
    public String show(Model model, String projectName) {
        model.addAttribute("metaTitle", "E2E Index");
        model.addAttribute("projectName", projectName);
        model.addAttribute("script", new String[]{"DataParser", "popup"});
        model.addAttribute("css", new String[]{"popup"});
        return "html/test_data";
    }

    @GetMapping("/project_name")
    @ResponseBody
    public List<String> getJsonResultViaAjax() {
        return dataControlServiceImpl.getProjectName();
    }


    @PutMapping("/testCaseData")
    @ResponseBody
    public boolean putDataToDb(@RequestBody TestCase testCase) {
        //Todo delete是否需要新增一個 D欄位保留一次反悔的機會
        dataControlServiceImpl.deletePageData(testCase.getTestCaseName());
        dataControlServiceImpl.saveTestCase(testCase);
        return true;
    }

    @PostMapping("/testCaseData")
    @ResponseBody
    public boolean startTesting(@RequestBody List<PageData> pageData, @RequestBody TestCase testCase) {
        boolean savingResult = this.putDataToDb(testCase);
        if (!savingResult) return false;
        String virtualType = BrowserControlSerevice.SELENIUM;
        browserCtrlService.startTestProcedure(testCase, virtualType);
        return true;
    }

    @GetMapping("/testCaseData")
    @ResponseBody
    public boolean startTesting(@RequestBody String testCaseName) {
        TestCase testCase = dataControlServiceImpl.getTestCase(testCaseName);
        String virtualType = BrowserControlSerevice.SELENIUM;
        browserCtrlService.startTestProcedure(testCase, virtualType);
        return true;
    }

    @GetMapping("/allTestCaseData")
    @ResponseBody
    public ResponseEntity<?> getAllTestCaseData(String projectName) {
        List<TestCase> testCaseList = dataControlServiceImpl.loadAllTestCaseFromProject(projectName);
        return ResponseEntity.ok(testCaseList);
    }
}

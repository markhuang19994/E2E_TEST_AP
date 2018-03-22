package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.JsonData;
import com.model.PageData;
import com.model.Project;
import com.model.TestCase;
import com.service.BrowserControlSerevice;
import com.service.DataControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
    private DataControlService dataCtrlService;

//    @Autowired
    private BrowserControlSerevice browserCtrlService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sampleMethod() {
        return "Hello E2E";
    }

    @RequestMapping("/data")
    public String show(Model model, String mid) {
        model.addAttribute("metaTitle", "E2E Index");
        model.addAttribute("script", new String[]{"DataParser"});
        return "html/test_data";
    }

    @GetMapping("/json_test_data")
    @ResponseBody
    public ResponseEntity<?> sendJsonResultViaAjax() {

        JsonData jsonData = new JsonData();

        List<JsonData> jsonDataList = new ArrayList<>();
        jsonData.setId("item01");
        jsonData.setValue("val01");
        jsonData.setDataType(JsonData.RADIO);
        jsonData.setBeforeScript("console.log(123)");
        jsonDataList.add(jsonData);
        jsonData = new JsonData();
        jsonData.setId("item02");
        jsonData.setValue("val02");
        jsonData.setDataType(JsonData.SELECT);
        jsonData.setBeforeScript("console.log(456)");
        jsonDataList.add(jsonData);
        jsonDataList.add(jsonData);
        jsonDataList.add(jsonData);

        String s = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            s = objectMapper.writeValueAsString(jsonDataList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        AtomicReference<List<PageData>> pageData = new AtomicReference<>(new ArrayList<>());
        PageData pageData1 = new PageData();
        try {
            pageData1.setDataJsonStr(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pageData1.setPageUrl("www.yahoo.com");
        pageData.get().add(pageData1);
        for (int i = 0; i < 5; i++) {
            pageData.get().add(pageData1);
        }

        return ResponseEntity.ok(pageData.get());
    }

    @PostMapping("/json_test_data")
    @ResponseBody
    public String getJsonResultViaAjax(@RequestBody List<PageData> pageData) {
        System.err.println(pageData);
        return "ok";
    }


    @PutMapping("/testCaseData")
    @ResponseBody
    public boolean putDataToDb(@RequestBody List<PageData> pageData,@RequestBody TestCase testCase) {
        dataCtrlService.assembleTestCase(testCase, pageData);
        dataCtrlService.saveTestCase(testCase);
        return true;
    }

    @PostMapping("/testCaseData")
    @ResponseBody
    public boolean startTesting(@RequestBody List<PageData> pageData,@RequestBody TestCase testCase) {
        boolean savingResult = this.putDataToDb(pageData, testCase);
        if(savingResult == false)
            return false;

        String virtualType = BrowserControlSerevice.SELENIUM;
        browserCtrlService.startTestProcedure(testCase, virtualType);
        return true;
    }


    @GetMapping("/testCaseData")
    @ResponseBody
    public boolean startTesting(@RequestBody String testCaseName) {
        TestCase testCase = dataCtrlService.getTestCase(testCaseName);
        String virtualType = BrowserControlSerevice.SELENIUM;
        browserCtrlService.startTestProcedure(testCase, virtualType);
        return true;
    }

    @GetMapping("/allTestCaseData")
    @ResponseBody
    public  ResponseEntity<?> getAllTestCaseData(@RequestBody String projectName) {

        //TODO 直接傳all testCase to front-end?
        List<TestCase> testCaseList = dataCtrlService.loadAllTestCaseFromProject(projectName);

        AtomicReference<List<TestCase>> testData = new AtomicReference<>(new ArrayList<>());

        testData.get().addAll(testCaseList);
        
        return ResponseEntity.ok(testData.get());
    }











}

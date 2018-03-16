package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.JsonData;
import com.model.PageData;
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
}

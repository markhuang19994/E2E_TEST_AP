package com.controller;

import com.model.JsonData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @RequestMapping("/json_test_data")
    @ResponseBody
    public ResponseEntity<?> getSearchResultViaAjax() {

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

        return ResponseEntity.ok(jsonDataList);

    }
}

package com.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

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

    @RequestMapping("/common/footer")
    public String show(Model model) {
        return "fragments/footer";
    }

    @Value("${my.name:'Mark'}")
    private String name;

    public String getName() {
        return this.name;
    }
}

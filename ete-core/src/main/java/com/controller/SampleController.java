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

    @RequestMapping("/show")
    public String show(Model model, String mid) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("XSELL");
        strings.add("PCL");
        strings.add("UPL");
        model.addAttribute("msg", "Congratulation E2E project is running!");
        model.addAttribute("options", strings);
        return "sample_html/sample_show";
    }
    @Value("${my.name}")
    private String name;
    public String getName(){
        return this.name;
    }
}

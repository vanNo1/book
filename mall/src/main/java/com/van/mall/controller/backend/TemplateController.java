package com.van.mall.controller.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Van
 * @date 2020/3/12 - 10:48
 */
@Controller
public class TemplateController {
    @RequestMapping("/index")
    public String toIndex(){
        return "file.html";
    }
}

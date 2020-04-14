package com.examples.parserdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class IndexPageController {
    @Value("${spring.application.name}")
    private String appName;

    @RequestMapping({"", "/"})
    public String redirectToApp(Model model, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        if (!requestURL.contains(appName)) {
            log.info("Redirect to /parse from {}", requestURL);
            return "redirect:/" + appName;
        }
        return "index";
    }

    @RequestMapping({"/parser"})
    public String getIndexPage(Model model) {
        return "index";
    }
}

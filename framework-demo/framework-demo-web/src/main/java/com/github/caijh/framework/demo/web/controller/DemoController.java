package com.github.caijh.framework.demo.web.controller;

import javax.inject.Inject;

import com.github.caijh.framework.demo.web.service.DemoWebService;
import com.github.caijh.framework.web.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "")
public class DemoController extends BaseController {

    @Inject
    private DemoWebService demoWebService;

    @GetMapping(value = "/test")
    @ResponseBody
    public String test() {
        return this.demoWebService.hello(null);
    }

    @GetMapping(value = "/async/test")
    @ResponseBody
    public void aysnc() {
        this.demoWebService.asyncHello("world");
    }


}

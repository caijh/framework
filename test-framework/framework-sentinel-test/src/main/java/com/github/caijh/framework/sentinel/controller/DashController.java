package com.github.caijh.framework.sentinel.controller;

import com.github.caijh.framework.web.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashController extends BaseController {

    @GetMapping("/dash")
    public String dash() {
        return "Hello dash";
    }

}

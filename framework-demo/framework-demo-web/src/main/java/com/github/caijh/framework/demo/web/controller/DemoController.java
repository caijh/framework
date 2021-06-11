package com.github.caijh.framework.demo.web.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.List;
import javax.inject.Inject;

import com.github.caijh.framework.data.redis.support.UserSign;
import com.github.caijh.framework.data.redis.support.UserSignService;
import com.github.caijh.framework.demo.web.service.DemoWebService;
import com.github.caijh.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "")
public class DemoController extends BaseController {

    @Inject
    private DemoWebService demoWebService;

    @Autowired
    private UserSignService userSignService;

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

    @GetMapping(value = "/u/{uid}/sign")
    public void sign(@PathVariable Long uid) {
        UserSign<Long> userSign = new UserSign<>();
        userSign.setUserId(uid);
        userSign.setDate(LocalDate.now(ZoneId.systemDefault()));
        this.userSignService.sign(userSign);
    }

    @GetMapping(value = "/u/{uid}/sign/list")
    public List<UserSign<Long>> signList(@PathVariable Long uid) {
        return this.userSignService.list(uid, YearMonth.now(ZoneId.systemDefault()));
    }


}

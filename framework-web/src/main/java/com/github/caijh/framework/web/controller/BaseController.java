package com.github.caijh.framework.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.caijh.framework.core.constants.DateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public abstract class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //使用spring自带的CustomDateEditor
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormat.YYYY_MM_DD_HH_MM_SS);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}

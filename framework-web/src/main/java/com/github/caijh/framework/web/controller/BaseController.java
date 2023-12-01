package com.github.caijh.framework.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.caijh.framework.core.constant.DateFormat;
import com.github.caijh.framework.core.util.LoggerUtils;
import org.slf4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 基础控制器类.
 */
public abstract class BaseController {

    protected Logger logger = LoggerUtils.getLogger(this.getClass());

    /**
     * 初始化Binder
     *
     * @param binder 编码器
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 使用spring自带的CustomDateEditor
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormat.YYYY_MM_DD_HH_MM_SS);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}

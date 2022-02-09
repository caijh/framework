package com.github.caijh.framework.wechat.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.experimental.Accessors;

@XmlRootElement(name = "xml")
@Data
@Accessors(chain = true)
public class ResponseWechat {

    public static final String SUCCESS = "SUCCESS";

    public static final String FAIL = "FAIL";

    @XmlElement(name = "return_code")
    private String returnCode;

    @XmlElement(name = "return_msg")
    private String returnMsg;

}


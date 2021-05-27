package com.github.caijh.framework.wechat.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.experimental.Accessors;

@XmlRootElement(name = "xml")
@Data
@Accessors(chain = true)
public class ResponseWechat {

    @XmlElement(name = "return_code")
    public String returnCode;

    @XmlElement(name = "return_msg")
    private String returnMsg;

}


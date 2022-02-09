package com.github.caijh.framework.wechat.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WxPayRespBody {

    /**
     * SUCCESS/FAIL此字段是通信标识，非交易标识，交易是否成功需要查看trade_state来判断
     */
    @JsonProperty(value = "return_code")
    @JSONField(name = "return_code")
    @XmlElement(name = "return_code")
    private String returnCode;

    /**
     * 当return_code为FAIL时返回信息为错误原因 ，例如 签名失败 参数格式校验错误
     */
    @JsonProperty(value = "return_msg")
    @JSONField(name = "return_msg")
    @XmlElement(name = "return_msg")
    private String returnMsg;

    /**
     * SUCCESS/FAIL 业务结果
     */
    @JsonProperty(value = "result_code")
    @JSONField(name = "result_code")
    @XmlElement(name = "result_code")
    private String resultCode;

    /**
     * 详细参见错误列表
     */
    @JsonProperty(value = "err_code")
    @JSONField(name = "err_code")
    @XmlElement(name = "err_code")
    private String errCode;

    /**
     * 错误返回的信息描述
     */
    @JsonProperty(value = "err_code_des")
    @JSONField(name = "err_code_des")
    @XmlElement(name = "err_code_des")
    private String errCodeDes;

    public boolean isSuccess() {
        return "SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode);
    }

    @XmlTransient
    public String getReturnCode() {
        return returnCode;
    }

    @XmlTransient
    public String getReturnMsg() {
        return returnMsg;
    }

    @XmlTransient
    public String getResultCode() {
        return resultCode;
    }

    @XmlTransient
    public String getErrCode() {
        return errCode;
    }

    @XmlTransient
    public String getErrCodeDes() {
        return errCodeDes;
    }

}

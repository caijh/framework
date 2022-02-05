package com.github.caijh.framework.wechat.model;

import javax.xml.bind.annotation.XmlElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WxPayOrderCreateRespBody extends WxPayRespBody {

    /**
     * 调用接口提交的公众账号ID
     */
    @JsonProperty(value = "appid")
    @JSONField(name = "appid")
    @XmlElement(name = "appid")
    private String appId;

    /**
     * 调用接口提交的商户号
     */
    @JsonProperty(value = "mch_id")
    @JSONField(name = "mch_id")
    @XmlElement(name = "mch_id")
    private String mchId;

    /**
     * 微信支付分配的终端设备号
     */
    @JsonProperty(value = "device_info")
    @JSONField(name = "device_info")
    @XmlElement(name = "device_info")
    private String deviceInfo;

    /**
     * 微信返回的随机字符串
     */
    @JsonProperty(value = "nonce_str")
    @JSONField(name = "nonce_str")
    @XmlElement(name = "nonce_str")
    private String nonceStr;

    /**
     * 微信返回的签名
     */
    private String sign;

    /**
     * 调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP，,H5支付固定传MWEB
     */
    @JsonProperty(value = "trade_type")
    @JSONField(name = "trade_type")
    @XmlElement(name = "trade_type")
    private String tradeType;

    /**
     * 微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时,针对H5支付此参数无特殊用途
     */
    @JsonProperty(value = "prepay_id")
    @JSONField(name = "prepay_id")
    @XmlElement(name = "prepay_id")
    private String prepayId;

    /**
     * mweb_url为拉起微信支付收银台的中间页面，可通过访问该url来拉起微信客户端，完成支付,mweb_url的有效期为5分钟
     */
    @JsonProperty(value = "mweb_url")
    @JSONField(name = "mweb_url")
    @XmlElement(name = "mweb_url")
    private String webUrl;

    /**
     * 该字段用于上报支付的场景信息,针对H5支付有以下三种场景,请根据对应场景上报,H5支付不建议在APP端使用，针对场景1，2请接入APP支付，不然可能会出现兼容性问题
     */
    @JsonProperty(value = "scene_info")
    @JSONField(name = "scene_info")
    @XmlElement(name = "scene_info")
    private String sceneInfo;

    private String extra;

    private Object transJsConfig;

}

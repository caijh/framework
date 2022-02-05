package com.github.caijh.framework.wechat.model;

import javax.xml.bind.annotation.XmlElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.caijh.framework.wechat.enums.WechatTradeType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WxPayOrderCreateReqBody {

    /**
     * 微信分配的小程序ID
     */
    @JSONField(name = "appid")
    @JsonProperty("appid")
    @XmlElement(name = "appid")
    private String appId;

    /**
     * 微信支付分配的商户号
     */
    @JSONField(name = "mch_id")
    @JsonProperty("mch_id")
    @XmlElement(name = "mch_id")
    private String mchId;

    /**
     * 可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
     */
    @JSONField(name = "device_info")
    @JsonProperty("device_info")
    @XmlElement(name = "device_info")
    private String deviceInfo = "WEB";

    /**
     * 随机字符串，长度要求在32位以内。推荐随机数生成算法
     */
    @JSONField(name = "nonce_str")
    @JsonProperty("nonce_str")
    @XmlElement(name = "nonce_str")
    private String nonceStr;

    private String sign;

    @JSONField(name = "sign_type")
    @JsonProperty("sign_type")
    @XmlElement(name = "sign_type")
    private String signType;

    /**
     * 商品简单描述，该字段请按照规范传递，具体请见参数规定
     */
    private String body;

    /**
     * 商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传，详见“单品优惠参数说明”
     */
    private String detail;

    /**
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
     */
    private String attach;

    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一。
     */
    @JSONField(name = "out_trade_no")
    @JsonProperty("out_trade_no")
    @XmlElement(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 标价币种
     */
    @JSONField(name = "fee_type")
    @JsonProperty("fee_type")
    @XmlElement(name = "fee_type")
    private String feeType = "CNY";

    /**
     * 订单总金额，单位为分
     */
    @JSONField(name = "total_fee")
    @JsonProperty("total_fee")
    @XmlElement(name = "total_fee")
    private int totalFee;

    /**
     * 支持IPV4和IPV6两种格式的IP地址。调用微信支付API的机器IP
     */
    @JSONField(name = "spbill_create_ip")
    @JsonProperty("spbill_create_ip")
    @XmlElement(name = "spbill_create_ip")
    private String spbillCreateIp;

    /**
     * 订单生成时间，格式为yyyyMMddHHmmss
     */
    @JSONField(name = "time_start")
    @JsonProperty("time_start")
    @XmlElement(name = "time_start")
    private String timeStart;

    /**
     * 订单失效时间，格式为yyyyMMddHHmmss
     */
    @JSONField(name = "time_expire")
    @JsonProperty("time_expire")
    @XmlElement(name = "time_expire")
    private String timeExpire;

    /**
     * 订单优惠标记，使用代金券或立减优惠功能时需要的参数，说明详见代金券或立减优惠
     */
    @JSONField(name = "goods_tag")
    @JsonProperty("goods_tag")
    @XmlElement(name = "goods_tag")
    private String goodsTag;

    /**
     * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。公网域名必须为https，如果是走专线接入，使用专线NAT IP或者私有回调域名可使用http。
     */
    @JSONField(name = "notify_url")
    @JsonProperty("notify_url")
    @XmlElement(name = "notify_url")
    private String notifyUrl;

    /**
     * 交易类型，小程序取值如下：JSAPI
     */
    @JSONField(name = "trade_type")
    @JsonProperty("trade_type")
    @XmlElement(name = "trade_type")
    private String tradeType = WechatTradeType.MWEB.name();

    /**
     * 商品ID,trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义
     */
    @JSONField(name = "product_id")
    @JsonProperty("product_id")
    @XmlElement(name = "product_id")
    private String productId;

    /**
     * 指定支付方式
     */
    @JSONField(name = "limit_pay")
    @JsonProperty("limit_pay")
    @XmlElement(name = "limit_pay")
    private String limitPay;

    /**
     * 用户标识
     */
    private String openid;

    /**
     * 电子发票入口开放标识
     */
    private String receipt = "N";

    /**
     * 场景信息
     */
    @JSONField(name = "scene_info")
    @JsonProperty("scene_info")
    @XmlElement(name = "scene_info")
    private String sceneInfo;

}

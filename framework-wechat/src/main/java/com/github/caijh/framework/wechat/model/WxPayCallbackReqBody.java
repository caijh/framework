package com.github.caijh.framework.wechat.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信支付结果通知
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "xml")
public class WxPayCallbackReqBody extends WxPayRespBody {

    @XmlElement(name = "bank_type")
    String bankType;

    @XmlElement(name = "total_fee")
    private int totalFee;

    @XmlElement(name = "settlement_total_fee")
    private int settlementTotalFee;

    @XmlElement(name = "fee_type")
    private String feeType;

    @XmlElement(name = "cash_fee")
    private int cashFee;

    @XmlElement(name = "cash_fee_type")
    private String cashFeeType;

    @XmlElement(name = "coupon_fee")
    private int couponFee;

    @XmlElement(name = "coupon_count")
    private int couponCount;

    @JsonProperty(value = "appid")
    @JSONField(name = "appid")
    private String appId;

    @JsonProperty(value = "mch_id")
    @JSONField(name = "mch_id")
    private String mchId;

    @JsonProperty(value = "device_info")
    @JSONField(name = "device_info")
    private String deviceInfo;

    @JsonProperty(value = "nonce_str")
    @JSONField(name = "nonce_str")
    private String nonceStr;

    private String sign;

    @JsonProperty(value = "sign_type")
    @JSONField(name = "sign_type")
    @XmlElement(name = "sign_type")
    private String signType;

    private String openid;

    @JsonProperty(value = "is_subscribe")
    @JSONField(name = "is_subscribe")
    @XmlElement(name = "is_subscribe")
    private boolean isSubscribe;

    @XmlElement(name = "trade_type")
    private String tradeType;

    @JsonProperty(value = "transaction_id")
    @JSONField(name = "transaction_id")
    @XmlElement(name = "transaction_id")
    private String transactionId;

    @JsonProperty(value = "out_trade_no")
    @JSONField(name = "out_trade_no")
    @XmlElement(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 商家数据包，原样返回
     */
    private String attach;

    /**
     * 支付完成时间
     */
    @JsonProperty(value = "time_end")
    @JSONField(name = "time_end")
    @XmlElement(name = "time_end")
    private String timeEnd;

    @XmlTransient
    public String getBankType() {
        return bankType;
    }

    @XmlTransient
    public int getTotalFee() {
        return totalFee;
    }

    @XmlTransient
    public int getSettlementTotalFee() {
        return settlementTotalFee;
    }

    @XmlTransient
    public String getFeeType() {
        return feeType;
    }

    @XmlTransient
    public int getCashFee() {
        return cashFee;
    }

    @XmlTransient
    public String getCashFeeType() {
        return cashFeeType;
    }

    @XmlTransient
    public int getCouponFee() {
        return couponFee;
    }

    @XmlTransient
    public int getCouponCount() {
        return couponCount;
    }

    @XmlTransient
    public String getAppId() {
        return appId;
    }

    @XmlTransient
    public String getMchId() {
        return mchId;
    }

    @XmlTransient
    public String getDeviceInfo() {
        return deviceInfo;
    }

    @XmlTransient
    public String getNonceStr() {
        return nonceStr;
    }

    @XmlTransient
    public String getSign() {
        return sign;
    }

    @XmlTransient
    public String getSignType() {
        return signType;
    }

    @XmlTransient
    public String getOpenid() {
        return openid;
    }

    @XmlTransient
    public boolean isSubscribe() {
        return isSubscribe;
    }

    @XmlTransient
    public String getTradeType() {
        return tradeType;
    }

    @XmlTransient
    public String getTransactionId() {
        return transactionId;
    }

    @XmlTransient
    public String getOutTradeNo() {
        return outTradeNo;
    }

    @XmlTransient
    public String getAttach() {
        return attach;
    }

    @XmlTransient
    public String getTimeEnd() {
        return timeEnd;
    }

}

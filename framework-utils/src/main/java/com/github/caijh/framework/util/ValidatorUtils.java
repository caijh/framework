package com.github.caijh.framework.util;

import java.util.regex.Pattern;

public class ValidatorUtils {

    public static final String REGEX_NUMBER = "-?\\d+(\\.\\d+)?";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5]*$"; // chinese

    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";
    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^(1\\d)\\d{9}$";

    public static final String REGEX_TEL = "^\\d{3,4}-?\\d{7,9}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z\\dA-Z]+[-|.]?)+[a-z\\dA-Z]@([a-z\\dA-Z]+(-[a-z\\dA-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "^((2(5[0-5]|[0-4]\\d))|1\\d{2}|[1-9]?\\d)(\\.((2(5[0-5]|[0-4]\\d))|1\\d{2}|[1-9]?\\d)){3}$";

    private ValidatorUtils() {

    }

    public static boolean isNumeric(String number) {
        return Pattern.matches(ValidatorUtils.REGEX_NUMBER, number);
    }

    /**
     * 校验用户名
     *
     * @param username 用户名
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(ValidatorUtils.REGEX_USERNAME, username);
    }

    /**
     * 校验手机号
     *
     * @param mobile 手机
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(ValidatorUtils.REGEX_MOBILE, mobile);
    }

    /**
     * 校验是否座机.
     *
     * @param tel tel
     * @return true, if string is tel.
     */
    public static boolean isTel(String tel) {
        return Pattern.matches(ValidatorUtils.REGEX_TEL, tel);
    }

    /**
     * 校验邮箱
     *
     * @param email 邮箱
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(ValidatorUtils.REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese 汉字
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(ValidatorUtils.REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证.
     *
     * @param idCard 身份证
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIdCard(String idCard) {
        return Pattern.matches(ValidatorUtils.REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(ValidatorUtils.REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr ip地址
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIpAddr(String ipAddr) {
        return Pattern.matches(ValidatorUtils.REGEX_IP_ADDR, ipAddr);
    }

}

package com.github.caijh.framework.wechat.constants

class WechatConstants {
    companion object {
        const val API_URL = "https://api.weixin.qq.com"
        const val API_TOKEN_URI = "/cgi-bin/token?grant_type=client_credential"
        const val API_JS_API_TICKET = "/cgi-bin/ticket/getticket"
        const val API_JS_API_SDK_LIST =
            "openAddress,updateTimelineShareData,updateAppMessageShareData,onMenuShareTimeline,onMenuShareAppMessage,onMenuShareQQ,onMenuShareWeibo,onMenuShareQZone,startRecord,stopRecord,onVoiceRecordEnd,playVoice,pauseVoice,stopVoice,onVoicePlayEnd,uploadVoice,downloadVoice,chooseImage,previewImage,uploadImage,downloadImage,translateVoice,getNetworkType,openLocation,getLocation,hideOptionMenu,showOptionMenu,hideMenuItems,showMenuItems,hideAllNonBaseMenuItem,showAllNonBaseMenuItem,closeWindow,scanQRCode,chooseWXPay,openProductSpecificView,addCard,chooseCard,openCard"


        const val API_PUBLIC_AUTHORIZE = "/sns/oauth2/access_token"
        const val API_PUBLIC_MEDIA_UPLOAD = "/cgi-bin/material/add_material"
        const val API_MINI_AUTHORIZE = "/sns/jscode2session"
        const val API_USERINFO = "/sns/userinfo"

        /**
         * 小程序行业信息
         */
        const val API_INDUSTRY_INFO = "/wxaapi/newtmpl/getcategory"

        private const val PAY_BASE_API_URL = "https://api.mch.weixin.qq.com"

        const val PAY_UNIFIEDORDER = "$PAY_BASE_API_URL/pay/unifiedorder"

    }
}

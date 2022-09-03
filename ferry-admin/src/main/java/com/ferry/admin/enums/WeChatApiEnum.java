package com.ferry.admin.enums;

/**
 * @Author: 摆渡人
 * @Date: 2022/8/23
 * @Description
 */
public enum WeChatApiEnum {
    WE_CHAT_GET_TEMPORARY_QRCODE("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s", "微信公众号-ticket获取二维码"),

    WE_CHAT_GET_ACCESS_TOKEN("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", "微信公众号获取AccessToken"),

    WE_CHAT_GET_CALL_BACK_IP("https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=%s", "获取微信回调ip"),

    WE_CHAT_GET_TEMPORARY_TICKET("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s", "微信公众号-获取临时二维码ticket"),

    WE_CHAT_GET_USER_INFO("https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN", "微信公众号-获取用户基本信息"),

    WE_CHAT_PUSH_TEMPLATE("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s", "微信公众号-推送模板");


    private String url;
    private String desc;

    WeChatApiEnum(String url, String desc) {
        this.url = url;
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
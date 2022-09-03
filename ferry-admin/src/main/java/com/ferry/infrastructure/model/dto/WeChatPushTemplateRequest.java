package com.ferry.infrastructure.model.dto;

import lombok.Data;

/**
 * 
 * @Author: 摆渡人
 * @Date: 2022/8/23
 * @Description 
 */
@Data
public class WeChatPushTemplateRequest {

    private String access_token;
    /**
     * 微信用户openId
     */
    private String touser;

    /**
     * 模板id
     */
    private String template_id;

    /**
     * 跳转url 优先级低于小程序跳转
     */
    private String url;

    /**
     * 消息id
     */
    private String client_msg_id;

    /**
     * 小程序配置
     */
    private MiniProgram miniprogram;

    /**
     * 模板id
     */
    private TemplateData data;


    /**
     * 模板内容
     */
    @Data
    public static class TemplateData {

        private KeyWord first;

        private KeyWord keyword1;

        private KeyWord keyword2;

        private KeyWord keyword3;

        private KeyWord keyword4;

        private KeyWord keyword5;

        private KeyWord remark;
    }

    @Data
    public static class KeyWord {

        private String value;

        private String color;
    }

    @Data
    public static class MiniProgram {

        private String appid;

        private String pagepath;
    }

}

package com.ferry.infrastructure.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 
 * @Author: 摆渡人
 * @Date: 2022/8/23
 * @Description 
 */
@Data
public class WeChatUserInfoResult {

    /**
     * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息
     */
    private Integer subscribe;

    /**
     * 用户的标识，对当前公众号唯一
     */
    private String openid;

    /**
     * 用户的语言，简体中文为zh_CN
     */
    private String language;

    /**
     * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     */
    private Long subscribe_time;

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。(push需要)
     */
    private String unionid;

    /**
     * 公众号运营者对粉丝的备注
     */
    private String remark;

    /**
     * wechat ps: 用户所在的分组ID（兼容旧的用户分组接口）
     */
    private Integer groupid;

    /**
     * 用户被打上的标签 ID 列表
     */
    private List<Integer> tagid_list;

    /**
     * 返回用户关注的渠道来源
     * ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，
     * ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENE_PROFILE_LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，
     * ADD_SCENE_PAID 支付后关注，ADD_SCENE_WECHAT_ADVERTISEMENT 微信广告，ADD_SCENE_REPRINT 他人转载,
     * ADD_SCENE_LIVESTREAM 视频号直播，ADD_SCENE_CHANNELS 视频号 , ADD_SCENE_OTHERS 其他
     */
    private String subscribe_scene;

    /**
     * 自定义带参二维码参数
     */
    private Integer qr_scene;

    /**
     * 自定义带参二维码参数
     */
    private String qr_scene_str;
}
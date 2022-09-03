package com.ferry.infrastructure.gateway;

import com.alibaba.fastjson.JSON;
import com.ferry.admin.enums.WeChatApiEnum;
import com.ferry.infrastructure.model.dto.*;
import com.ferry.infrastructure.utils.RestTemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 
 * @Author: 摆渡人
 * @Date: 2022/8/23
 * @Description 
 */
@Component
@Slf4j
public class WeChatAdapter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private RestTemplateUtils restTemplateUtils;

    @Value("${wechat.officialAccount.appid}")
    private String appId;

    @Value("${wechat.officialAccount.appSecret}")
    private String appSecret;

    private static final String OFFICIAL_ACCOUNT_ACCESS_TOKEN = "ss";
    /**
     * 获取临时二维码
     * @param request
     * @return
     */
    public CreateQRCodeResult getWeChatAttentionQRCode(CreateQRCodeRequest request) {
        String token = String.valueOf(redisTemplate.opsForValue().get(OFFICIAL_ACCOUNT_ACCESS_TOKEN));
        try {
            CreateQRCodeResult result = restTemplateUtils.postForObject(buildWeChatUrl(WeChatApiEnum.WE_CHAT_GET_TEMPORARY_TICKET.getUrl(), token), request, CreateQRCodeResult.class);
            log.info("获取临时二维码result:{}", JSON.toJSONString(result, true));
            return result;
        } catch (Exception e) {
            log.error("获取二维码异常:", e);
            throw e;
        }
    }

    /**
     * 获取微信用户信息
     * @param openId
     * @return
     */
    public WeChatUserInfoResult queryWeChatUserInfo(String openId) {
        String token = String.valueOf(redisTemplate.opsForValue().get(OFFICIAL_ACCOUNT_ACCESS_TOKEN));
        try {
            WeChatUserInfoResult result = restTemplateUtils.getForObject(buildWeChatUrl(WeChatApiEnum.WE_CHAT_GET_USER_INFO.getUrl(), token, openId), WeChatUserInfoResult.class);
            log.info("微信用户信息result:{}", JSON.toJSONString(result, true));
            return result;
        } catch (Exception e) {
            log.error("获取用户信息异常:", e);
            throw e;
        }
    }

    /**
     * 推送微信模板
     * @param request
     * @return
     */
    public WeChatPushResult pushWeChatTemplate(WeChatPushTemplateRequest request) {
        try {
            String token = Objects.isNull(request.getAccess_token()) ? String.valueOf(redisTemplate.opsForValue().get(OFFICIAL_ACCOUNT_ACCESS_TOKEN)) : request.getAccess_token();
            WeChatPushResult result = restTemplateUtils.postForObject(buildWeChatUrl(WeChatApiEnum.WE_CHAT_PUSH_TEMPLATE.getUrl(), token), request, WeChatPushResult.class);
            return result;
        } catch (Exception e) {
            log.error("获取用户信息异常:", e);
            throw e;
        }
    }

    /**
     * 获取token
     * @param
     * @return
     */
    public WeChatAcTokenResult getWeChatAcToken() {
        try {
            WeChatAcTokenResult result = restTemplateUtils.getForObject(buildWeChatUrl(WeChatApiEnum.WE_CHAT_GET_ACCESS_TOKEN.getUrl(), appId, appSecret), WeChatAcTokenResult.class);
            log.info("获取临时二维码result:{}", JSON.toJSONString(result, true));
            return result;
        } catch (Exception e) {
            log.error("获取二维码异常:", e);
            throw e;
        }
    }

    public String buildWeChatUrl(String url, String... token) {
        return String.format(url, token);
    }
}

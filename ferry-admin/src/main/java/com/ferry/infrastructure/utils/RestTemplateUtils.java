package com.ferry.infrastructure.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 
 * @Author: 摆渡人
 * @Date: 2022/8/23
 * @Description
 */
@Slf4j
@Component
public class RestTemplateUtils {

    @Resource
    private RestTemplate restTemplate;

    public <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables) {
        T response;
        try {
            response = restTemplate.postForObject(url, request, responseType, uriVariables);
        } catch (Exception e) {
            log.error("post接口失败, 异常信息: ", e);
            throw e;
        }
        log.info("post接口返回, response={}", JSONObject.toJSONString(response));
        return response;
    }

    public <T> T getForObject(String url, Class<T> responseType, Object... uriVariables) {
        log.info("get接口调用, request url={}", url);
        T response;
        try {
            response = restTemplate.getForObject(url, responseType, uriVariables);
        } catch (Exception e) {
            log.error("get接口失败, 异常信息: ", e);
            throw e;
        }
        log.info("get接口返回, response={}", JSONObject.toJSONString(response));
        return response;
    }
}

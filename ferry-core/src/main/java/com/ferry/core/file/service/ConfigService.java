package com.ferry.core.file.service;

/**
 * @Author: 摆渡人
 * @Date: 2021/5/8
 */

import com.ferry.core.file.entity.Config;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 系统配置
 */
public interface ConfigService {

    /**
     * 获取系统配置
     *
     * @return
     */
    Map <String, Object> getConfigs();

    /**
     * 添加/修改系统配置
     *
     * @param configs 所有的配置项
     */
    void saveConfig(Map <String, String> configs);

    /**
     * 添加/修改文件的配置项
     *
     * @param key  key
     * @param file 微信收款码
     */
    void saveFile(String key, MultipartFile file);

    /**
     * 添加/修改单个
     *
     * @param key   key
     * @param value value
     */
    void saveConfig(String key, String value);

    /**
     * 获取单个配置
     *
     * @param key key
     */
    Config getByKey(String key);

    /**
     * 获取网站详情
     */
    Map<String, Object> getSiteInfo();

    /**
     * 返回 Spider
     */
    String getSpiderConfig();

    /**
     * 获取随机的用户头像
     */
    List <String> getRandomUserAvatar();

}
package com.ferry.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.ferry.admin.service.SysConfigService;
import com.ferry.admin.util.BaiduPushUtil;
import com.ferry.admin.util.UrlBuildUtil;
import com.ferry.blog.dto.ReptileRequest;
import com.ferry.blog.service.ReptileService;
import com.ferry.core.file.emums.ConfigKeyEnum;
import com.ferry.core.file.holder.SpringContextHolder;
import com.ferry.core.file.service.ConfigService;
import com.ferry.core.http.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhyd.hunter.config.HunterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: 摆渡人
 * @Date: 2022/10/29
 */
@Api(tags = "爬虫接口")
@RestController
@RequestMapping(value = "reptile")
public class ReptileController {


    @Resource
    private ReptileService reptileService;
    @PostMapping("/bringBackList")
    @ResponseBody
    @ApiOperation(value = "抓取列表文章")
    public void bringBackList(@RequestBody ReptileRequest request, HttpServletResponse response) throws IOException, InterruptedException {
        reptileService.bringBackList(request, response.getWriter());
    }

    @PostMapping("/single")
    @ApiOperation(value = "抓取单个文章")
    public void single(@RequestBody ReptileRequest request, HttpServletResponse response) throws IOException, InterruptedException {
        reptileService.crawlSingle(request, response.getWriter());
    }

    @PostMapping(value = "/pushToBaidu/{id}")
    @ApiOperation(value = "推送文章百度站长平台")
    public Result pushToBaidu(@PathVariable String id) {
        if (null == id) {
            return Result.error("请至少选择一条记录");
        }
        ConfigService configService = SpringContextHolder.getBean(ConfigService.class);
        Map<String, Object> config = configService.getConfigs();
        String siteUrl = (String) config.get(ConfigKeyEnum.SITE_URL.getKey());
        StringBuilder params = new StringBuilder();
        params.append(siteUrl).append("/blog/item/").append(id).append("\n");
        // urls: 推送, update: 更新, del: 删除
        String url = UrlBuildUtil.getBaiduPushUrl("urls", (String) config.get(ConfigKeyEnum.SITE_URL.getKey()), (String) config.get(ConfigKeyEnum.BAIDU_PUSH_TOKEN.getKey()));
        String result = BaiduPushUtil.doPush(url, params.toString(), (String) config.get(ConfigKeyEnum.BAIDU_PUSH_COOKIE.getKey()));
        JSONObject resultJson = JSONObject.parseObject(result);
        if (resultJson.containsKey("error")) {
            return Result.error();
        }
        return Result.ok();
    }
}

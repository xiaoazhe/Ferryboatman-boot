package com.ferry.blog.controller;

import com.ferry.blog.dto.ReptileRequest;
import com.ferry.blog.service.ReptileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhyd.hunter.config.HunterConfig;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        reptileService.crawlSingle(request.getTypeId(), request.getUrl(), request.isConvertImg(), response.getWriter());
    }
}

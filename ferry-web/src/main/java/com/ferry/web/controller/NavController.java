package com.ferry.web.controller;

import com.ferry.core.http.Result;
import com.ferry.web.service.NavService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 摆渡人
 * @Date: 2021/6/3
 */

@CrossOrigin
@Api(tags = "导航模块")
@RestController
@RequestMapping("nav")
public class NavController {

    @Autowired
    private NavService navService;

    @ApiOperation(value = "查询全部")
    @PostMapping(value = "/findAll")
    public Result findAll() {
        return Result.ok(navService.findAllNav());
    }

}

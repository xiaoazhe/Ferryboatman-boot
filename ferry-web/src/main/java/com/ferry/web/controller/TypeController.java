package com.ferry.web.controller;

import com.ferry.core.file.emums.CommonStatusEnum;
import com.ferry.core.http.Result;
import com.ferry.core.page.PageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ferry.web.service.TypeService;

/**
 * @Author: 摆渡人
 * @Date: 2021/6/3
 */

@CrossOrigin
@Api(tags = "分类模块")
@RestController
@RequestMapping("type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @ApiOperation(value = "查询全部")
    @PostMapping(value = "/findAll")
    public Result findAll() {
        return Result.ok(typeService.findAll());
    }

    @ApiOperation(value = "根据类型查询博客")
    @PostMapping(value = "/findBlogByTypeId")
    public Result findBlogByTypeId(@RequestBody PageRequest pageRequest) {
        String typeId = String.valueOf(pageRequest.getEnabled());
        if (typeId == null || "-1" .equals(typeId)) {
            throw new RuntimeException(CommonStatusEnum.NO_FIND);
        }
        return Result.ok(typeService.findBlogPage(pageRequest, typeId));
    }

}

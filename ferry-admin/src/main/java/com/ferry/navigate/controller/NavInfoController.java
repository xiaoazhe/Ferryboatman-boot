package com.ferry.navigate.controller;

import com.ferry.core.http.Result;
import com.ferry.navigate.request.QueryPageRequest;
import com.ferry.server.navigate.entity.NavInfo;
import com.ferry.navigate.service.NavInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (NavInfo)表控制层
 *
 * @author makejava
 * @since 2022-09-30 23:47:42
 */
@Api(tags = "导航信息")
@RestController
@RequestMapping("navInfo")
public class NavInfoController {
    /**
     * 服务对象
     */
    @Resource
    private NavInfoService navInfoService;

    /**
     * 分页查询
     *
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询")
    @PostMapping(value="/findPage")
    public Result queryByPage(@RequestBody QueryPageRequest pageRequest) {
        return Result.ok(this.navInfoService.queryByPage(pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "根据id获取导航")
    @GetMapping(value = "/get/{id}")
    public Result queryById(@PathVariable("id") Integer id) {
        return Result.ok(this.navInfoService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param navInfo 实体
     * @return 新增结果
     */
    @ApiOperation(value = "添加导航")
    @PostMapping(value = "/save")
    public Result add(@RequestBody NavInfo navInfo) {
        return Result.ok(this.navInfoService.insert(navInfo));
    }

    /**
     * 编辑数据
     *
     * @param navInfo 实体
     * @return 编辑结果
     */
    @ApiOperation(value = "编辑导航")
    @PutMapping(value = "/update")
    public Result edit(@RequestBody NavInfo navInfo) {
        return Result.ok(this.navInfoService.update(navInfo));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @ApiOperation(value = "编辑导航")
    @PostMapping(value = "/delete")
    public Result deleteById(@RequestBody List<NavInfo> navInfoList) {
        return Result.ok(this.navInfoService.deleteById(navInfoList.stream().map(NavInfo::getId).collect(Collectors.toList())));
    }

}


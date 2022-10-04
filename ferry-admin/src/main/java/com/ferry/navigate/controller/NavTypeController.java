package com.ferry.navigate.controller;

import com.ferry.core.http.Result;
import com.ferry.navigate.request.QueryPageRequest;
import com.ferry.server.navigate.entity.NavType;
import com.ferry.navigate.service.NavTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (NavType)表控制层
 *
 * @author makejava
 * @since 2022-09-30 23:46:59
 */
@Api(tags = "导航分类")
@RestController
@RequestMapping("navType")
public class NavTypeController {
    /**
     * 服务对象
     */
    @Resource
    private NavTypeService navTypeService;

    /**
     * 分页查询
     *
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询")
    @PostMapping(value="/findPage")
    public Result queryByPage(@RequestBody QueryPageRequest pageRequest) {
        return Result.ok(this.navTypeService.queryByPage(pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "根据id获取导航分类")
    @GetMapping(value = "/get/{id}")
    public Result queryById(@PathVariable("id") Integer id) {
        return Result.ok(this.navTypeService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param navType 实体
     * @return 新增结果
     */
    @ApiOperation(value = "添加导航分类")
    @PostMapping(value = "/save")
    public Result add(@RequestBody NavType navType) {
        return Result.okMsg(this.navTypeService.insert(navType));
    }

    /**
     * 编辑数据
     *
     * @param navType 实体
     * @return 编辑结果
     */
    @ApiOperation(value = "编辑导航分类")
    @PutMapping(value = "/update")
    public Result edit(@RequestBody NavType navType) {
        return Result.ok(this.navTypeService.update(navType));
    }

    /**
     * 删除数据
     *
     * @return 删除是否成功
     */
    @ApiOperation(value = "删除导航分类")
    @PostMapping(value = "/delete")
    public Result deleteById(@RequestBody List<NavType> navTypeList) {
        return Result.ok(this.navTypeService.deleteByIds(navTypeList.stream().map(NavType::getId).collect(Collectors.toList())));
    }

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @ApiOperation(value = "查询列表")
    @GetMapping(value="/queryList")
    public Result queryList() {
        return Result.ok(this.navTypeService.queryList());
    }
}


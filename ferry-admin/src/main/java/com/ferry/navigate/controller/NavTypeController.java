package com.ferry.navigate.controller;

import com.ferry.server.navigate.entity.NavType;
import com.ferry.navigate.service.NavTypeService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
     * @param navType 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<NavType>> queryByPage(NavType navType, PageRequest pageRequest) {
        return ResponseEntity.ok(this.navTypeService.queryByPage(navType, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<NavType> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.navTypeService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param navType 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<NavType> add(NavType navType) {
        return ResponseEntity.ok(this.navTypeService.insert(navType));
    }

    /**
     * 编辑数据
     *
     * @param navType 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<NavType> edit(NavType navType) {
        return ResponseEntity.ok(this.navTypeService.update(navType));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.navTypeService.deleteById(id));
    }

}


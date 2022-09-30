package com.ferry.navigate.controller;

import com.ferry.server.navigate.entity.NavInfo;
import com.ferry.navigate.service.NavInfoService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
     * @param navInfo 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<NavInfo>> queryByPage(NavInfo navInfo, PageRequest pageRequest) {
        return ResponseEntity.ok(this.navInfoService.queryByPage(navInfo, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<NavInfo> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.navInfoService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param navInfo 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<NavInfo> add(NavInfo navInfo) {
        return ResponseEntity.ok(this.navInfoService.insert(navInfo));
    }

    /**
     * 编辑数据
     *
     * @param navInfo 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<NavInfo> edit(NavInfo navInfo) {
        return ResponseEntity.ok(this.navInfoService.update(navInfo));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.navInfoService.deleteById(id));
    }

}


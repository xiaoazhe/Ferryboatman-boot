package com.ferry.blog.controller;

import com.ferry.blog.service.BlogService;
import com.ferry.core.http.Result;
import com.ferry.core.page.PageRequest;
import com.ferry.server.blog.entity.BlBlog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: 摆渡人
 * @Date: 2021/5/7
 */
@Api(tags = "博客模块")
@RestController
@RequestMapping("blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @ApiOperation(value = "分页查询")
    @PreAuthorize("hasAuthority('sys:blog:view')")
    @PostMapping(value="/findPage")
    public Result findPage(@RequestBody PageRequest pageRequest) {
        return Result.ok(blogService.findPage(pageRequest));
    }

    @ApiOperation(value = "添加博客")
    @PreAuthorize("hasAuthority('sys:blog:add') AND hasAuthority('sys:blog:edit')")
    @PostMapping(value="/save")
    public Result save(@RequestBody BlBlog blog) {
        return Result.ok(blogService.saveBlog(blog));
    }

    /**
     * 批量删除
     *
     * @param blogs
     * @return
     */
    @ApiOperation(value = "删除")
    @PreAuthorize("hasAuthority('sys:blog:delete')")
    @PostMapping(value="/delete")
    public Result delete(@RequestBody List <BlBlog> blogs) {
        return Result.ok(blogService.removeTypes(blogs));
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除")
    @PreAuthorize("hasAuthority('sys:blog:delete')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        return Result.ok(blogService.deleteById(id));
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询")
    @PreAuthorize("hasAuthority('sys:blog:view')")
    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    public Result selectById(@PathVariable String id) {
        return Result.ok(blogService.selectById(id));
    }

    /**
     * 修改发布状态
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "修改发布状态")
    @PreAuthorize("hasAuthority('sys:blog:view')")
    @RequestMapping(value = "/publishById/{id}", method = RequestMethod.GET)
    public Result publishById(@PathVariable String id) {
        return Result.ok(blogService.publishById(id));
    }
}

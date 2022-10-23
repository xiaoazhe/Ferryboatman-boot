package com.ferry.web.controller;

import com.ferry.core.file.emums.CommonStatusEnum;
import com.ferry.core.file.emums.FieldStatusEnum;
import com.ferry.core.file.emums.StateEnums;
import com.ferry.core.file.util.StringUtils;
import com.ferry.core.http.Result;
import com.ferry.core.page.PageRequest;
import com.ferry.server.blog.entity.BlBlog;
import com.ferry.web.model.BaseRequest;
import com.ferry.web.service.BlogService;
import com.ferry.web.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@Api(tags = "博客模块")
@RequestMapping("blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;
    @ApiOperation(value = "分页查询")
    @PostMapping(value="/findPage")
    public Result findPage(@RequestBody PageRequest pageRequest) {
        return Result.ok(blogService.findPage(pageRequest));
    }

    @ApiOperation(value = "个人查询")
    @PostMapping(value="/findUserPage/{userId}")
    public Result findUserPage(@PathVariable String userId, @RequestBody PageRequest pageRequest) {
        if (StringUtils.isNotBlank(userId) && !StringUtils.equals("undefined", userId)) {
            return Result.ok(blogService.findUserPage(userId, pageRequest));
        }
        try {
            String token = request.getHeader(FieldStatusEnum.HEARD).substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            userId = claims.getId();
        } catch (Exception e) {
            return Result.error();
        }
        return Result.ok(blogService.findUserPage(userId, pageRequest));
    }

    @ApiOperation(value = "id查询")
    @PostMapping("/getBlogById")
    public Result blBlog(@RequestBody BaseRequest request) {
        if (StringUtils.isBlank(request.getId())) {
            throw new RuntimeException(CommonStatusEnum.ID_NOT_NULL);
        }
        Result result = blogService.selectById(request.getId());
        return result;
    }

    @ApiOperation(value = "热门查询")
    @PostMapping("/hotBlog")
    public Result hotBlog() {
        Result result = blogService.hotBlog();
        return result;
    }

    @ApiOperation(value = "用户添加博客")
    @PostMapping("/saveBlog")
    public Result saveBlog(@RequestBody BlBlog blBlog) {
        if (blBlog == null) {
            throw new RuntimeException(CommonStatusEnum.ERR);
        }
        return blogService.saveBlog(blBlog);
    }

    @ApiOperation(value = "删除")
    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable String id) {
        blogService.deleteById(id);
        return Result.ok(StateEnums.DELETED.getMsg());
    }

    /**
     *音乐列表
     *
     * @return
     */
    @ApiOperation(value = "前台查询")
    @RequestMapping(value = "/getMusicList", method = RequestMethod.GET)
    public Result getList() {
        return Result.ok(blogService.getMusicList());
    }
}

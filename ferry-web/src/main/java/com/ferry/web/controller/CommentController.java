package com.ferry.web.controller;


import com.ferry.core.http.Result;
import com.ferry.core.page.PageRequest;
import com.ferry.server.blog.entity.BlComment;
import com.ferry.web.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: 摆渡人
 * @Date: 2021/6/6
 */
@CrossOrigin
@Api(tags = "评论")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "添加评论")
    @PostMapping(value = "save")
    public Result saveComment(@RequestBody BlComment comment) {
        return commentService.add(comment);
    }

    @ApiOperation(value = "获取评论和回复")
    @PostMapping(value = "getCommentAndReply")
    public Result getCommentAndReply(@RequestBody PageRequest pageRequest) {
        return commentService.getCommentAndReply(pageRequest);
    }

}

package com.ferry.web.controller;


import com.ferry.core.file.emums.StateEnums;
import com.ferry.core.http.Result;
import com.ferry.core.page.PageRequest;
import com.ferry.server.blog.entity.BlProblem;
import com.ferry.server.blog.entity.BlReply;
import com.ferry.web.service.ProblemService;
import com.ferry.web.service.ReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 摆渡人
 * @Date: 2021/6/9
 */
@CrossOrigin
@Api(tags = "回复")
@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ProblemService problemService;

    @ApiOperation(value = "个人回答")
    @PostMapping(value = "/getIndividualReply")
    public Result getIndividualReply(@RequestBody PageRequest pageRequest){
        return Result.ok(replyService.getIndividualReply(pageRequest));
    }

    @ApiOperation(value = "获取最新回复")
    @GetMapping(value = "/newlist")
    public Result newlist(){
        return Result.ok(replyService.newlist());
    }

    @ApiOperation(value = "获取最新回复")
    @PostMapping(value = "/getByProId")
    public Result getByProId(@RequestParam String proId, @RequestBody PageRequest pageRequest){
        return Result.ok(replyService.getByProId(proId, pageRequest));
    }

    @ApiOperation(value = "添加回复")
    @PostMapping(value = "/save")
    public Result save(@RequestBody BlReply reply){
        return Result.ok(replyService.add(reply));
    }

    @ApiOperation(value = "删除")
    @GetMapping(value = "/delectComment/{id}")
    public Result delectComment(@PathVariable String id){
        BlReply reply = replyService.getById(id);
        if (reply == null) {
            return Result.error(StateEnums.REQUEST_ERROR.getMsg());
        }
        BlProblem problem = problemService.getById(reply.getProblemid());
        problem.setReply(problem.getReply() - 1);
        problemService.updateById(problem);
        return Result.ok(replyService.removeById(id));
    }
}

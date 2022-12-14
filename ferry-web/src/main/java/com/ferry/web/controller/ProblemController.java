package com.ferry.web.controller;

import com.ferry.core.file.emums.CommonStatusEnum;
import com.ferry.core.file.emums.StateEnums;
import com.ferry.core.file.util.StringUtils;
import com.ferry.core.http.Result;
import com.ferry.core.page.PageRequest;
import com.ferry.server.blog.entity.BlProblem;
import com.ferry.web.service.ProblemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: 摆渡人
 * @Date: 2021/6/6
 */
@CrossOrigin
@Api(tags = "问题")
@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @ApiOperation(value = "个人提问")
    @PostMapping(value = "/getIndividualPro")
    public Result getIndividualPro(@RequestBody PageRequest pageRequest){
        return Result.ok(problemService.getIndividualPro(pageRequest));
    }

    @ApiOperation(value = "获取最新列表")
    @PostMapping(value = "/newlist/{label}")
    public Result newlist(@PathVariable Integer label, @RequestBody PageRequest pageRequest){
        return Result.ok(problemService.newlist(label, pageRequest));
    }

    @ApiOperation(value = "热门列表")
    @PostMapping(value = "/hotlist/{label}")
    public Result hotlist(@PathVariable Integer label, @RequestBody PageRequest pageRequest){
        return Result.ok(problemService.hotlist(label, pageRequest));
    }

    @ApiOperation(value = "没有回答列表")
    @PostMapping(value = "/waitlist/{label}")
    public Result waitlist(@PathVariable Integer label, @RequestBody PageRequest pageRequest){
        return Result.ok(problemService.waitlist(label, pageRequest));
    }

    @ApiOperation(value = "根据id查询")
    @GetMapping(value="/getById")
    public Result findById(@RequestParam String id){
        return Result.ok(problemService.getProById(id));
    }

    @ApiOperation(value = "根据id查询类似")
    @GetMapping(value="/getSimilarById")
    public Result getSimilarById(@RequestParam String id){
        return Result.ok(problemService.getSimilarById(id));
    }

    @ApiOperation(value = "添加")
    @PostMapping(value="/save")
    public Result savePro(@RequestBody BlProblem problem){
        if (problem.getContent() == null || problem.getLabelList().size() == 0 || problem.getTitle() == null) {
            return Result.error(CommonStatusEnum.ERR);
        }
        return Result.ok(problemService.savePro(problem));
    }

    @ApiOperation(value = "删除")
    @DeleteMapping(value="/deleteById")
    public Result delete(@PathVariable String id){
        return Result.ok(problemService.deleteById(id));
    }

    @ApiOperation(value = "点赞")
    @GetMapping(value="/setGood/{id}")
    public Result setGood(@PathVariable String id){
        return Result.ok(problemService.setGood(id));
    }

    @ApiOperation(value = "添加收藏")
    @GetMapping(value="/setCollect/{id}/{statusId}")
    public Result setCollect(@PathVariable String id, @PathVariable Integer statusId){
        if (StringUtils.isBlank(id) || statusId == null) {
            return Result.error(StateEnums.REQUEST_ERROR.getMsg());
        }
        return Result.ok(problemService.setCollect(id, statusId));
    }

    @ApiOperation(value = "查看收藏")
    @PostMapping(value="/collect/getCollect/{statusId}")
    public Result getCollect(@PathVariable Integer statusId
            , @RequestBody PageRequest pageRequest){
        if (statusId == null) {
            return Result.error(StateEnums.REQUEST_ERROR.getMsg());
        }
        return Result.ok(problemService.getCollect(statusId, pageRequest));
    }

    @ApiOperation(value = "取消收藏")
    @GetMapping(value="/collect/deleteCollect/{id}/{statusId}")
    public Result deleteCollect(@PathVariable String id, @PathVariable Integer statusId){
        if (StringUtils.isBlank(id) || statusId == null) {
            return Result.error(StateEnums.REQUEST_ERROR.getMsg());
        }
        return Result.ok(problemService.deleteCollect(id, statusId));
    }

    @ApiOperation(value = "清空记录")
    @GetMapping(value="/collect/delete")
    public Result delete(){
        return Result.ok(problemService.delete());
    }
}

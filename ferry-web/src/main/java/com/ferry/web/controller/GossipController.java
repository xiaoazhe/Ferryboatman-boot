package com.ferry.web.controller;

import com.ferry.core.file.emums.FieldStatusEnum;
import com.ferry.core.file.emums.StateEnums;
import com.ferry.core.file.util.StringUtils;
import com.ferry.core.http.Result;
import com.ferry.core.page.PageRequest;
import com.ferry.server.blog.entity.Gossip;
import com.ferry.web.service.GossipService;
import com.ferry.web.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 摆渡人
 * @Date: 2021/6/12
 */
@CrossOrigin
@RestController
@RequestMapping("/gossip")
public class GossipController {


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return Result.ok();
    }

    @RequestMapping(value = "/findAllByPre/{gossipId}", method = RequestMethod.GET)
    public Result findAllByPre(@PathVariable String gossipId){
        if (StringUtils.isBlank(gossipId)){
            return Result.error(StateEnums.REQUEST_ERROR.getMsg());
        }
        return Result.ok();
    }

    @RequestMapping(value = "/{gossipId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String gossipId){
        return Result.ok();
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save(@RequestBody Gossip gossip){
        return Result.ok();
    }

    @RequestMapping(value = "/{gossipId}", method = RequestMethod.PUT)
    public Result update(@PathVariable String gossipId, @RequestBody Gossip gossip){
        gossip.set_id(gossipId);
        return Result.ok();
    }

    @RequestMapping(value = "/{gossipId}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String gossipId){
        return Result.ok();
    }

    @RequestMapping(value = "/comment/{parentid}/{page}/{size}", method = RequestMethod.GET)
    public Result comment(@PathVariable String parentid, @PathVariable int page, @PathVariable int size){
        return Result.ok();
    }

    @RequestMapping(value = "/pageByUser/{page}/{size}", method = RequestMethod.GET)
    public Result pageByUser(@PathVariable int page, @PathVariable int size){
        return Result.ok();
    }

    @RequestMapping(value = "/thumbup/{gossipId}", method = RequestMethod.PUT)
    public Result addthumbup(@PathVariable String gossipId){
        return Result.ok();
    }
}

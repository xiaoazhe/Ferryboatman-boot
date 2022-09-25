package com.ferry.web.controller;

import com.ferry.core.file.emums.FieldStatusEnum;
import com.ferry.core.file.emums.StateEnums;
import com.ferry.core.file.object.PageResult;
import com.ferry.core.file.util.StringUtils;
import com.ferry.core.http.Result;
import com.ferry.server.blog.entity.Gossip;
import com.ferry.web.service.GossipService;
import com.ferry.web.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class GossipWebController {

    @Autowired
    private GossipService gossipService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return Result.ok(gossipService.findAll());
    }

    @RequestMapping(value = "/findAllByPre/{gossipId}", method = RequestMethod.GET)
    public Result findAllByPre(@PathVariable String gossipId){
        if (StringUtils.isBlank(gossipId)){
            return Result.error(StateEnums.REQUEST_ERROR.getMsg());
        }
        return Result.ok(gossipService.findAllByPre(gossipId));
    }

    @RequestMapping(value = "/{gossipId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String gossipId){
        return Result.ok( gossipService.findById(gossipId));
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save(@RequestBody Gossip gossip){
        try {
            String token = request.getHeader(FieldStatusEnum.HEARD).substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            String userId = claims.getId();
            gossip.setUserid(userId);
        } catch (Exception e) {
            return Result.error(StateEnums.REQUEST_ERROR.getMsg());
        }
        if (gossip.getParentid() == null) {
            gossip.setParentid("0");
        }
        if (StringUtils.isBlank(gossip.getParentid())) {
            gossip.setParentid("0");
        }
        gossipService.save(gossip);
        return Result.ok(StateEnums.SAVEBLOG_SUC.getMsg());
    }

    @RequestMapping(value = "/{gossipId}", method = RequestMethod.PUT)
    public Result update(@PathVariable String gossipId, @RequestBody Gossip gossip){
        gossip.set_id(gossipId);
        gossipService.update(gossip);
        return Result.ok(StateEnums.SAVEBLOG_SUC.getMsg());
    }

    @RequestMapping(value = "/{gossipId}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String gossipId){
        gossipService.deleteById(gossipId);
        return Result.ok(StateEnums.DELETED.getMsg());
    }

    @RequestMapping(value = "/comment/{parentid}/{page}/{size}", method = RequestMethod.GET)
    public Result comment(@PathVariable String parentid, @PathVariable int page, @PathVariable int size){
        Page<Gossip> pageData = gossipService.pageQuery(parentid, page, size);
        return Result.ok(new PageResult<Gossip>(pageData.getTotalElements(), pageData.getContent()));
    }

    @RequestMapping(value = "/pageByUser/{page}/{size}", method = RequestMethod.GET)
    public Result pageByUser(@PathVariable int page, @PathVariable int size){
        String userId = null;
        try {
            String token = request.getHeader(FieldStatusEnum.HEARD).substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            userId = claims.getId();
        } catch (Exception e) {
            return Result.error(StateEnums.REQUEST_ERROR.getMsg());
        }
        Page <Gossip> pageData = gossipService.pageByUser(userId, page, size);
        return Result.ok(new PageResult <Gossip>(pageData.getTotalElements(), pageData.getContent()));
    }

    @RequestMapping(value = "/thumbsUp/{gossipId}", method = RequestMethod.PUT)
    public Result addThumbsUp(@PathVariable String gossipId){
        String userId = null;
        try {
            String token = request.getHeader(FieldStatusEnum.HEARD).substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            userId = claims.getId();
        } catch (Exception e) {
            return Result.error(StateEnums.REQUEST_ERROR.getMsg());
        }
        //先判断该用户是否已经点赞了。
        if(redisTemplate.opsForValue().get("gossip_"+userId+"_"+gossipId)!=null){
            return Result.error(StateEnums.ADDLIKE_ERR.getMsg());
        }

        gossipService.addThumbsUp(gossipId);
        redisTemplate.opsForValue().set("gossip_"+userId+"_"+gossipId, 1);
        return Result.ok(StateEnums.ADDLIKE_SUC.getMsg());

    }
}

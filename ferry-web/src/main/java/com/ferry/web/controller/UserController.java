package com.ferry.web.controller;

import com.ferry.core.file.emums.CommonStatusEnum;
import com.ferry.core.file.emums.StateEnums;
import com.ferry.core.file.util.StringUtils;
import com.ferry.core.http.Result;
import com.ferry.core.page.PageRequest;
import com.ferry.core.http.Result;
import com.ferry.core.page.PageRequest;
import com.ferry.server.blog.entity.BlUser;
import com.ferry.web.service.SysUserService;
import com.ferry.web.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 摆渡人
 * @Date: 2021/6/2
 */

@Api(tags = "前台系统")
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SysUserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/login")
    public Result login(@RequestBody BlUser user){
        user = userService.login(user.getMobile(), user.getPassword());
        if(user == null){
            return Result.error("请先注册！");
        }
        String token = jwtUtil.createJWT(String.valueOf(user.getId()), user.getMobile(), "user");
        Map <String, Object> map = new HashMap <>();
        map.put("token", token);
        map.put("roles", "user");
        map.put("name", user.getNickname());
        map.put("avatar", user.getAvatar());
        return Result.ok(map);
    }

    /**
     * 注册
     * @return
     */
    @PostMapping(value = "/register")
    public Result register(@RequestBody BlUser user){
        //得到缓存中的验证码
//        String checkcodeRedis = (String) redisTemplate.opsForValue().get("checkcode_" + user.getMobile());
//        if(checkcodeRedis.isEmpty()){
//            return new Result(false, StatusCode.ERROR, "请先获取手机验证码");
//        }
//        if(!checkcodeRedis.equals(code)){
//            return new Result(false, StatusCode.ERROR, "请输入正确的验证码");
//        }
        return userService.add(user);
    }
}

package com.ferry.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ferry.core.file.emums.FieldStatusEnum;
import com.ferry.core.file.emums.StateEnums;
import com.ferry.core.file.util.IdWorker;
import com.ferry.core.http.Result;
import com.ferry.core.page.PageRequest;
import com.ferry.core.page.PageResult;
import com.ferry.server.blog.entity.BlProblem;
import com.ferry.server.blog.entity.BlReply;
import com.ferry.server.blog.mapper.BlProblemMapper;
import com.ferry.server.blog.mapper.BlReplyMapper;
import com.ferry.web.service.ReplyService;
import com.ferry.web.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 摆渡人
 * @Date: 2021/6/9
 */
@Service
public class ReplyServiceImpl extends ServiceImpl <BlReplyMapper, BlReply> implements ReplyService {


    @Autowired
    private BlReplyMapper replyMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private BlProblemMapper problemMapper;

    @Override
    public PageResult getIndividualReply(PageRequest pageRequest) {
        Page <BlReply> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        String userId = null;
        try {
            String token = request.getHeader(FieldStatusEnum.HEARD).substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            userId = claims.getId();
        } catch (Exception e) {
            throw new RuntimeException(StateEnums.REQUEST_ERROR.getMsg());
        }
        QueryWrapper<BlReply> queryWrapper = new QueryWrapper <>();
        queryWrapper.eq(BlReply.COL_USERID, userId);
        Page<BlReply> problemPage = replyMapper.selectPage(page, queryWrapper);
        PageResult pageResult = new PageResult(problemPage);
        return pageResult;
    }

    @Override
    public List <BlReply> newlist() {
        String token = (String) request.getAttribute(FieldStatusEnum.ROLE_USER);
        Claims claims = jwtUtil.parseJWT(token);
        String userId = claims.getId();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(BlReply.COL_USERID, userId);
        queryWrapper.orderByDesc(BlReply.COL_CREATETIME);
        List <BlReply> replies = replyMapper.selectList(queryWrapper);
        return replies.stream().limit(5).collect(Collectors.toList());
    }

    @Override
    public PageResult getByProId(String proId, PageRequest pageRequest) {
        Page <BlReply> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(BlReply.COL_PROBLEMID, proId);
        queryWrapper.orderByDesc(BlReply.COL_CREATETIME);
        Page<BlReply> problemPage =replyMapper.selectPage(page, queryWrapper);
        PageResult pageResult = new PageResult(problemPage);
        return pageResult;
    }

    @Override
    public String add(BlReply reply) {
        BlProblem problem = problemMapper.selectById(reply.getProblemid());
        problem.setReply(problem.getReply() + 1);
        problemMapper.updateById(problem);
        String token = request.getHeader(FieldStatusEnum.HEARD).substring(7);
        Claims claims = jwtUtil.parseJWT(token);
        String userId = claims.getId();
        reply.setId(idWorker.nextId() + "");
        reply.setUserid(userId);
        reply.setCreateTime(new Date());
        reply.setCreateBy(userId);
        replyMapper.insert(reply);
        return StateEnums.COMMENT_SUC.getMsg();
    }
}

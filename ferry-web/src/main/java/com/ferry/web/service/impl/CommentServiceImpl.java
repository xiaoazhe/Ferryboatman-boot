package com.ferry.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ferry.core.file.emums.FieldStatusEnum;
import com.ferry.core.file.emums.StateEnums;
import com.ferry.core.file.util.IdWorker;
import com.ferry.core.http.Result;
import com.ferry.core.page.PageRequest;
import com.ferry.server.blog.entity.BlComment;
import com.ferry.server.blog.entity.BlReply;
import com.ferry.server.blog.entity.BlUser;
import com.ferry.server.blog.mapper.*;
import com.ferry.web.service.CommentService;
import com.ferry.web.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 摆渡人
 * @Date: 2021/6/6
 */
@Service
public class CommentServiceImpl extends ServiceImpl <BlCommentMapper, BlComment> implements CommentService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private BlCommentMapper commentMapper;

    @Autowired
    private BlUserMapper userMapper;

    @Autowired
    private BlBlogMapper blogMapper;

    @Autowired
    private BlReplyMapper replyMapper;

    @Autowired
    private BlProblemMapper problemMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IdWorker idWorker;

    @Override
    public Result add(BlComment comment) {
        String userId = null;
        try {
            String token = request.getHeader(FieldStatusEnum.HEARD).substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            userId = claims.getId();
        } catch (Exception e) {
            return null;
        }
        if (comment.getToCommentId() == null || "".equals(comment.getToCommentId())) {
            comment.setFirstCommentId("1");
        }
        BlUser user = userMapper.selectById(userId);
        String name = user.getNickname() == null ? "匿名用户" : user.getNickname();
        comment.setCreateBy(name);
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        comment.setUserId(userId);
        comment.setId(idWorker.nextId()+"");
        comment.setStatus(1);
        comment.setType(0);
        comment.setSource("BLOG_INFO");
        commentMapper.insert(comment);
        return new Result().ok(StateEnums.COMMENT_SUC.getMsg());
    }

    @Override
    public Result getCommentAndReply(PageRequest pageRequest) {
        Page <BlComment> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        Page <BlReply> pageReply = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        String userId = null;
        try {
            String token = request.getHeader(FieldStatusEnum.HEARD).substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            userId = claims.getId();
        } catch (Exception e) {
            return new Result().ok(null);
        }
        Page <BlComment> comment = commentMapper.selectPage(page, new QueryWrapper <BlComment>().eq(BlComment.COL_USER_ID, userId)
        .ne(BlComment.COL_STATUS, 0));
        for (BlComment com : comment.getRecords()) {
            com.setBlBlog(blogMapper.selectById(com.getBlogId()));
        }
        Page <BlReply> replyPage = replyMapper.selectPage(pageReply, new QueryWrapper <BlReply>().eq(BlReply.COL_USERID, userId));
        for (BlReply reply : replyPage.getRecords()) {
            reply.setProblem(problemMapper.selectById(reply.getProblemid()));
        }
        Map resultMap = new HashMap();
        resultMap.put("commentPage", comment);
        resultMap.put("replyPage", replyPage);
        return new Result().ok(resultMap);
    }


}

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
import com.ferry.server.blog.entity.BlFriendLink;
import com.ferry.server.blog.mapper.BlFriendLinkMapper;
import com.ferry.web.service.FriendLinkService;
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
 * @Date: 2021/6/17
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl <BlFriendLinkMapper, BlFriendLink> implements FriendLinkService {

    @Autowired
    private BlFriendLinkMapper friendLinkMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String add(BlFriendLink friendLink) {
        String userId = null;
        try {
            String token = request.getHeader(FieldStatusEnum.HEARD).substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            userId = claims.getId();
        } catch (Exception e) {
            return null;
        }
        friendLink.setUid(idWorker.nextId() + "" );
        friendLink.setCreateTime(new Date());
        friendLink.setLinkStatus(0);
        friendLink.setCreateBy(userId);
        friendLink.setClickCount(0);
        friendLink.setStatus((byte) 1);
        friendLink.setUid(userId);
        friendLinkMapper.insert(friendLink);
        return StateEnums.REQUEST_SUCCESS.getMsg();
    }

    @Override
    public BlFriendLink findById(String id) {
        QueryWrapper<BlFriendLink> queryWrapper = new QueryWrapper <>();
        queryWrapper.ne(BlFriendLink.COL_LINK_STATUS, 0);
        queryWrapper.eq(BlFriendLink.COL_UID, id);
        return friendLinkMapper.selectOne(queryWrapper);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Page <BlFriendLink> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        QueryWrapper<BlFriendLink> queryWrapper = new QueryWrapper <>();
        queryWrapper.eq(BlFriendLink.COL_LINK_STATUS, 1);
        Page<BlFriendLink> problemPage = friendLinkMapper.selectPage(page, queryWrapper);
        PageResult pageResult = new PageResult(problemPage);
        return pageResult;
    }

    @Override
    public List <BlFriendLink> friendTop() {
        QueryWrapper<BlFriendLink> queryWrapper = new QueryWrapper <>();
        queryWrapper.eq(BlFriendLink.COL_LINK_STATUS, 1);
        queryWrapper.orderByDesc(BlFriendLink.COL_SORT);
        return friendLinkMapper.selectList(queryWrapper).
                stream().limit(5).collect(Collectors.toList());
    }
}

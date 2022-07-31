package com.ferry.web.service.impl;

import com.ferry.core.http.Result;
import com.ferry.server.blog.entity.BlBlog;
import com.ferry.server.blog.mapper.BlUserMapper;
import com.ferry.web.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 摆渡人
 * @Date: 2021/6/8
 */
@Service
public class BlogDealService {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlUserMapper userMapper;

    public Result saveBlog(BlBlog blBlog) {
        String name = userMapper.selectById(blBlog.getUserUid()).getNickname();
        blBlog.setCreateBy(name);
        blBlog.setAuthor(name);
        return blogService.saveBlog(blBlog);
    }
}

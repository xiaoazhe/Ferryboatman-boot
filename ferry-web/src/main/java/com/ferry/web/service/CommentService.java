package com.ferry.web.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.ferry.core.http.Result;
import com.ferry.core.page.PageRequest;
import com.ferry.server.blog.entity.BlComment;

/**
 * @Author: 摆渡人
 * @Date: 2021/6/6
 */
public interface CommentService extends IService <BlComment> {

    Result add(BlComment spit);

    Result getCommentAndReply(PageRequest pageRequest);
}

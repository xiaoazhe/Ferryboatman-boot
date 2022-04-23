package com.ferry.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ferry.core.page.PageRequest;
import com.ferry.core.page.PageResult;
import com.ferry.server.blog.entity.BlComment;

/**
 * @Author: 摆渡人
 * @Date: 2021/5/11
 */
public interface CommentService  extends IService <BlComment> {

    PageResult findPage(PageRequest pageRequest);

    String deleteById(String id);

    String deleteAll(String id);

    BlComment findById(String id);
}

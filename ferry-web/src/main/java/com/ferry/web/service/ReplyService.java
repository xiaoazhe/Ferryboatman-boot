package com.ferry.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ferry.core.page.PageRequest;
import com.ferry.core.page.PageResult;
import com.ferry.server.blog.entity.BlReply;

import java.util.List;

/**
 * @Author: 摆渡人
 * @Date: 2021/6/9
 */
public interface ReplyService extends IService <BlReply> {

    PageResult getIndividualReply(PageRequest pageRequest);

    List <BlReply> newlist();

    PageResult getByProId(String proId, PageRequest pageRequest);

    String add(BlReply reply);
}

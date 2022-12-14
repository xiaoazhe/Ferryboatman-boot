package com.ferry.web.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.ferry.core.page.PageRequest;
import com.ferry.core.page.PageResult;
import com.ferry.server.blog.entity.BlLabel;
import com.ferry.server.blog.entity.BlMusic;

import java.util.List;

/**
 * @Author: 摆渡人
 * @Date: 2021/6/9
 */
public interface LabelService extends IService <BlLabel> {

    PageResult selectAllByUser(PageRequest pageRequest);

    List <BlLabel> toplist();

    List<BlMusic> getMusicList();
}

package com.ferry.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ferry.core.page.PageRequest;
import com.ferry.core.page.PageResult;
import com.ferry.server.admin.entity.SysDict;

import java.util.List;

/**
 * @Author: 摆渡人
 * @Date: 2021/4/27
 */
public interface SysDictService extends IService <SysDict> {

    int delete(SysDict record);

    int delete(List <SysDict> records);

    PageResult findPage(PageRequest pageRequest);

    List<SysDict> findByLable(String lable);
}

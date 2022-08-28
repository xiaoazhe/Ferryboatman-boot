package com.ferry.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ferry.core.file.emums.FieldStatusEnum;
import com.ferry.core.file.emums.StateEnums;
import com.ferry.core.file.util.IdWorker;
import com.ferry.core.file.util.StringUtils;
import com.ferry.core.http.Result;
import com.ferry.core.page.PageRequest;
import com.ferry.core.page.PageResult;
import com.ferry.server.blog.entity.BlMaterial;
import com.ferry.server.blog.mapper.BlMaterialMapper;
import com.ferry.web.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 摆渡人
 * @Date: 2021/6/17
 */
@Service
public class MaterialServiceImpl extends ServiceImpl <BlMaterialMapper, BlMaterial> implements MaterialService {

    @Autowired
    private BlMaterialMapper materialMapper;

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Page <BlMaterial> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        String label = pageRequest.getName();
        QueryWrapper <BlMaterial> queryWrapper = new QueryWrapper <>();
        queryWrapper.eq(BlMaterial.COL_STATUS, 1);
        queryWrapper.like(!StringUtils.isBlank(label), BlMaterial.COL_TITLE, label);
        queryWrapper.orderByDesc(BlMaterial.COL_SORT);
        Page<BlMaterial> problemPage = materialMapper.selectPage(page, queryWrapper);
        PageResult pageResult = new PageResult(problemPage);
        return pageResult;
    }

    @Override
    public BlMaterial findById(String id) {
        QueryWrapper<BlMaterial> queryWrapper = new QueryWrapper <>();
        queryWrapper.ne(BlMaterial.COL_STATUS, 0);
        queryWrapper.eq(BlMaterial.COL_ID, id);
        return materialMapper.selectOne(queryWrapper);
    }
}

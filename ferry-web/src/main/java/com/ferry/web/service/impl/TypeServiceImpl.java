package com.ferry.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ferry.core.file.emums.FieldStatusEnum;
import com.ferry.core.file.util.StringUtils;
import com.ferry.core.page.PageRequest;
import com.ferry.core.page.PageResult;
import com.ferry.server.blog.entity.BlBlog;
import com.ferry.server.blog.entity.BlType;
import com.ferry.server.blog.mapper.BlBlogMapper;
import com.ferry.server.blog.mapper.BlTypeMapper;
import com.ferry.web.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: 摆渡人
 * @Date: 2021/5/7
 */
@Service
public class TypeServiceImpl extends ServiceImpl <BlTypeMapper, BlType> implements TypeService {

    @Autowired
    private BlTypeMapper blTypeMapper;

    @Autowired
    private BlBlogMapper blBlogMapper;

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Page <BlType> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        String label = pageRequest.getParamValue(FieldStatusEnum.NAME);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like(!StringUtils.isBlank(label),BlType.COL_NAME, label);
        queryWrapper.ne(BlType.COL_AVAILABLE,0);
        Page<BlType> typePage = blTypeMapper.selectPage(page, queryWrapper);
        PageResult pageResult = new PageResult(typePage);
        return pageResult;
    }


    @Override
    public boolean removeTypes(List <BlType> types) {
        for (BlType type : types) {
            blTypeMapper.deleteById(type.getId());
        }
        return true;
    }

    @Override
    public boolean saveType(BlType type) {
        if(type.getId() != null) {
            BlType oldType =  blTypeMapper.selectById(type.getId());
            if (oldType != null) {
                oldType.setAvailable(type.getAvailable());
                oldType.setUpdateTime(new Date());
                oldType.setDescription(type.getDescription());
                oldType.setName(type.getName());
                oldType.setSort(type.getSort());
                blTypeMapper.updateById(oldType);
            }
        } else {
            type.setCreateTime(new Date());
            blTypeMapper.insert(type);
        }
        return true;
    }

    @Override
    public List <BlType> findAll() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.ne(BlType.COL_ID, 0);
        queryWrapper.orderByDesc(BlType.COL_SORT);
        List <BlType> typeList= blTypeMapper.selectList(queryWrapper);
        return typeList;
    }

    @Override
    public BlType findById(String id) {
        BlBlog blog = blBlogMapper.selectById(id);
        BlType typeName = blTypeMapper.selectById(blog.getTypeId());
        return typeName;
    }

    @Override
    public PageResult findBlogPage(PageRequest pageRequest, String typeId) {
        Page <BlBlog> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
//        String label = pageRequest.getParamValue("name");
        String label = pageRequest.getName();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like(!StringUtils.isBlank(label), BlBlog.COL_TITLE, label);
        queryWrapper.eq(!StringUtils.equals("all", typeId), BlBlog.COL_TYPE_ID, typeId);
        queryWrapper.eq(BlBlog.COL_IS_PUBLISH, "1");
        queryWrapper.orderByDesc(BlBlog.COL_CREATE_TIME);
        Page<BlBlog> typePage = blBlogMapper.selectPage(page, queryWrapper);
        PageResult pageResult = new PageResult(typePage);
        return pageResult;
    }
}

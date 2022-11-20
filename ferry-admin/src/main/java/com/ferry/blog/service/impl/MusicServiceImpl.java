package com.ferry.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ferry.blog.service.MusicService;
import com.ferry.core.file.emums.StateEnums;
import com.ferry.core.file.util.StringUtils;
import com.ferry.core.page.PageRequest;
import com.ferry.core.page.PageResult;
import com.ferry.server.blog.entity.BlMusic;
import com.ferry.server.blog.mapper.BlMusicMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 摆渡人
 * @Date: 2021/5/10
 */

@Service
@Slf4j
public class MusicServiceImpl extends ServiceImpl <BlMusicMapper, BlMusic> implements MusicService {

    @Autowired
    private BlMusicMapper musicMapper;

    @Override
    public BlMusic getById(Integer id) {
        return musicMapper.selectById(id);
    }

    @Override
    public void deleteById(Integer id) {
        musicMapper.deleteById(id);
    }

    @Override
    public void enableById(Integer id) {
        BlMusic music = musicMapper.selectById(id);
        music.setEnable(StateEnums.ENABLED.getCode());
        musicMapper.updateById(music);
    }

    @Override
    public void disableById(Integer id) {
        BlMusic music = musicMapper.selectById(id);
        music.setEnable(StateEnums.NOT_ENABLE.getCode());
        musicMapper.updateById(music);
    }

    @Override
    public PageResult getByPage(PageRequest pageRequest) {
        Page <BlMusic> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        QueryWrapper<BlMusic> queryWrapper = new QueryWrapper();
        queryWrapper.like(!StringUtils.isBlank(pageRequest.getName()), BlMusic.COL_NAME, pageRequest.getName());
        queryWrapper.like(!StringUtils.isBlank(pageRequest.getName()), BlMusic.COL_ARTIST, pageRequest.getName());
        queryWrapper.eq(pageRequest.getEnabled() > 0, BlMusic.COL_ENABLE, pageRequest.getEnabled());
        queryWrapper.orderByDesc(BlMusic.COL_CREATE_TIME);
        Page<BlMusic> typePage = musicMapper.selectPage(page, queryWrapper);
        PageResult pageResult = new PageResult(typePage);
        return pageResult;
    }
}

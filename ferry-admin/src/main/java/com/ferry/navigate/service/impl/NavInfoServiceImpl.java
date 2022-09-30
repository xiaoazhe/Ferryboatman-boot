package com.ferry.navigate.service.impl;

import com.ferry.server.navigate.entity.NavInfo;
import com.ferry.server.navigate.mapper.NavInfoMapper;
import com.ferry.navigate.service.NavInfoService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (NavInfo)表服务实现类
 *
 * @author makejava
 * @since 2022-09-30 23:47:42
 */
@Service("navInfoService")
public class NavInfoServiceImpl implements NavInfoService {
    @Resource
    private NavInfoMapper navInfoMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NavInfo queryById(Integer id) {
        return this.navInfoMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param navInfo 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<NavInfo> queryByPage(NavInfo navInfo, PageRequest pageRequest) {
        long total = this.navInfoMapper.count(navInfo);
        return new PageImpl<>(this.navInfoMapper.queryAllByLimit(navInfo, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param navInfo 实例对象
     * @return 实例对象
     */
    @Override
    public NavInfo insert(NavInfo navInfo) {
        this.navInfoMapper.insert(navInfo);
        return navInfo;
    }

    /**
     * 修改数据
     *
     * @param navInfo 实例对象
     * @return 实例对象
     */
    @Override
    public NavInfo update(NavInfo navInfo) {
        this.navInfoMapper.update(navInfo);
        return this.queryById(navInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.navInfoMapper.deleteById(id) > 0;
    }
}

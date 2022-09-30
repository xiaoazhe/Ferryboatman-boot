package com.ferry.navigate.service.impl;

import com.ferry.server.navigate.entity.NavType;
import com.ferry.server.navigate.mapper.NavTypeMapper;
import com.ferry.navigate.service.NavTypeService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (NavType)表服务实现类
 *
 * @author makejava
 * @since 2022-09-30 23:47:02
 */
@Service("navTypeService")
public class NavTypeServiceImpl implements NavTypeService {
    @Resource
    private NavTypeMapper navTypeMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NavType queryById(Integer id) {
        return this.navTypeMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param navType 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<NavType> queryByPage(NavType navType, PageRequest pageRequest) {
        long total = this.navTypeMapper.count(navType);
        return new PageImpl<>(this.navTypeMapper.queryAllByLimit(navType, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param navType 实例对象
     * @return 实例对象
     */
    @Override
    public NavType insert(NavType navType) {
        this.navTypeMapper.insert(navType);
        return navType;
    }

    /**
     * 修改数据
     *
     * @param navType 实例对象
     * @return 实例对象
     */
    @Override
    public NavType update(NavType navType) {
        this.navTypeMapper.update(navType);
        return this.queryById(navType.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.navTypeMapper.deleteById(id) > 0;
    }
}

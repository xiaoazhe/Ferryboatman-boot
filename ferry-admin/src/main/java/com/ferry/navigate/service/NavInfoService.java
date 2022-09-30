package com.ferry.navigate.service;

import com.ferry.server.navigate.entity.NavInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (NavInfo)表服务接口
 *
 * @author makejava
 * @since 2022-09-30 23:47:42
 */
public interface NavInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NavInfo queryById(Integer id);

    /**
     * 分页查询
     *
     * @param navInfo 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<NavInfo> queryByPage(NavInfo navInfo, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param navInfo 实例对象
     * @return 实例对象
     */
    NavInfo insert(NavInfo navInfo);

    /**
     * 修改数据
     *
     * @param navInfo 实例对象
     * @return 实例对象
     */
    NavInfo update(NavInfo navInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}

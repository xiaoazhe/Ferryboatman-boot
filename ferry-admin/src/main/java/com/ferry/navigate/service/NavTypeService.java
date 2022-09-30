package com.ferry.navigate.service;

import com.ferry.server.navigate.entity.NavType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (NavType)表服务接口
 *
 * @author makejava
 * @since 2022-09-30 23:47:02
 */
public interface NavTypeService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NavType queryById(Integer id);

    /**
     * 分页查询
     *
     * @param navType 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<NavType> queryByPage(NavType navType, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param navType 实例对象
     * @return 实例对象
     */
    NavType insert(NavType navType);

    /**
     * 修改数据
     *
     * @param navType 实例对象
     * @return 实例对象
     */
    NavType update(NavType navType);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}

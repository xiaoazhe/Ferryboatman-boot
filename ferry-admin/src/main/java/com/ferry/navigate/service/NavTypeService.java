package com.ferry.navigate.service;

import com.ferry.core.page.PageRequest;
import com.ferry.core.page.PageResult;
import com.ferry.navigate.request.QueryPageRequest;
import com.ferry.server.navigate.entity.NavType;
import org.springframework.data.domain.Page;

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
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    PageResult queryByPage(QueryPageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param navType 实例对象
     * @return 实例对象
     */
    String insert(NavType navType);

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

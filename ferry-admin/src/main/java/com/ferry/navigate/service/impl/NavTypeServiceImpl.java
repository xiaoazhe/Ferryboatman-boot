package com.ferry.navigate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ferry.core.file.emums.StateEnums;
import com.ferry.core.file.util.StringUtils;
import com.ferry.core.page.PageRequest;
import com.ferry.core.page.PageResult;
import com.ferry.navigate.request.QueryPageRequest;
import com.ferry.server.admin.entity.SysNotify;
import com.ferry.server.navigate.entity.NavType;
import com.ferry.server.navigate.mapper.NavTypeMapper;
import com.ferry.navigate.service.NavTypeService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;

import javax.annotation.Resource;
import java.util.Objects;

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
        if (Objects.isNull(id)) {
            throw new RuntimeException(StateEnums.PARAMETER_ERROR.getMsg());
        }
        return this.navTypeMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public PageResult queryByPage(QueryPageRequest pageRequest) {
        Page<NavType> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());

        QueryWrapper<NavType> queryWrapper = new QueryWrapper<NavType>();
        queryWrapper.like(!StringUtils.isBlank(pageRequest.getFilterName()), NavType.NAV_TYPE_NAME, pageRequest.getFilterName());
        Page<NavType> userIPage = navTypeMapper.selectPage(page, queryWrapper);
        PageResult pageResult = new PageResult(userIPage);

        return pageResult;
    }

    /**
     * 新增数据
     *
     * @param insertRequest 实例对象
     * @return 实例对象
     */
    @Override
    public String insert(NavType insertRequest) {
        if (StringUtils.isBlank(insertRequest.getNavTypeName())) {
            throw new RuntimeException(StateEnums.PARAMETER_ERROR.getMsg());
        }
        this.navTypeMapper.insert(insertRequest);
        return StateEnums.REQUEST_SUCCESS.getMsg();
    }

    /**
     * 修改数据
     *
     * @param navType 实例对象
     * @return 实例对象
     */
    @Override
    public NavType update(NavType navType) {
        if (Objects.isNull(navType.getId())) {
            throw new RuntimeException(StateEnums.PARAMETER_ERROR.getMsg());
        }
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
        if (Objects.isNull(id)) {
            throw new RuntimeException(StateEnums.PARAMETER_ERROR.getMsg());
        }
        return this.navTypeMapper.deleteById(id) > 0;
    }
}

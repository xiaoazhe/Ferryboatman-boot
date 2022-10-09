package com.ferry.navigate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ferry.core.file.emums.StateEnums;
import com.ferry.core.file.util.StringUtils;
import com.ferry.core.page.PageRequest;
import com.ferry.core.page.PageResult;
import com.ferry.navigate.request.QueryPageRequest;
import com.ferry.navigate.response.NavAggregatesResponse;
import com.ferry.navigate.response.NavTypeResponse;
import com.ferry.server.admin.entity.SysNotify;
import com.ferry.server.navigate.entity.NavInfo;
import com.ferry.server.navigate.entity.NavType;
import com.ferry.server.navigate.mapper.NavInfoMapper;
import com.ferry.server.navigate.mapper.NavTypeMapper;
import com.ferry.navigate.service.NavTypeService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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

    @Resource
    private NavInfoMapper navInfoMapper;

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
        queryWrapper.isNull(NavType.NAV_PARENT_TYPE_ID);
        Page<NavType> navTypePage = navTypeMapper.selectPage(page, queryWrapper);
        List<NavAggregatesResponse> aggregatesResponseList = new ArrayList<>();
        for (NavType navType : navTypePage.getRecords()) {
            NavAggregatesResponse aggregatesResponse = buildNavResponse(navType);
            aggregatesResponseList.add(aggregatesResponse);
        }
        Page<NavAggregatesResponse> response = new Page<>();
        response.setTotal(navTypePage.getTotal());
        response.setRecords(aggregatesResponseList);
        response.setSize(navTypePage.getSize());
        response.setPages(navTypePage.getPages());
        response.setCurrent(navTypePage.getCurrent());
        PageResult pageResult = new PageResult(response);
        return pageResult;
    }


    private NavAggregatesResponse buildNavResponse(NavType navType) {
        NavAggregatesResponse navAggregatesResponse = new NavAggregatesResponse();
        navAggregatesResponse.setId(navType.getId());
        navAggregatesResponse.setNavTypeName(navType.getNavTypeName());
        navAggregatesResponse.setCreateTime(navType.getCreateTime());
        QueryWrapper<NavType> query = new QueryWrapper<NavType>();
        query.eq(NavType.NAV_PARENT_TYPE_ID, navType.getId());
        List<NavType> navTypeList = navTypeMapper.selectList(query);

        if (!navTypeList.isEmpty()) {
            List<NavTypeResponse> typeResponses = new ArrayList<>();
            navTypeList.stream().forEach(n -> {
                NavTypeResponse response = new NavTypeResponse();
                response.setNavTypeName(n.getNavTypeName());
                response.setId(n.getId());
                response.setNavParentTypeId(n.getNavParentTypeId());
                response.setCreateTime(n.getCreateTime());
                response.setChildren(new ArrayList<>());
                typeResponses.add(response);
            });
            navAggregatesResponse.setChildren(typeResponses);
        }
        return navAggregatesResponse;
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
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        if (ids.isEmpty()) {
            throw new RuntimeException(StateEnums.PARAMETER_ERROR.getMsg());
        }
        return this.navTypeMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<NavType> queryList() {
        QueryWrapper<NavType> query = new QueryWrapper<NavType>();
        query.isNull(NavType.IS_DELETED);
        query.orderByDesc(NavType.SORT);
        return navTypeMapper.selectList(query);
    }
}

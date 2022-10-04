package com.ferry.navigate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ferry.core.file.emums.StateEnums;
import com.ferry.core.file.util.StringUtils;
import com.ferry.core.page.PageResult;
import com.ferry.navigate.request.QueryPageRequest;
import com.ferry.server.navigate.entity.NavInfo;
import com.ferry.server.navigate.entity.NavType;
import com.ferry.server.navigate.mapper.NavInfoMapper;
import com.ferry.navigate.service.NavInfoService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

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
        if (Objects.isNull(id)) {
            throw new RuntimeException(StateEnums.PARAMETER_ERROR.getMsg());
        }
        return this.navInfoMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public PageResult queryByPage(QueryPageRequest pageRequest) {
        Page<NavInfo> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());

        QueryWrapper<NavInfo> queryWrapper = new QueryWrapper<NavInfo>();
        queryWrapper.eq(Objects.nonNull(pageRequest.getFilterId()), NavInfo.NAV_TYPE_ID, pageRequest.getFilterId());
        queryWrapper.like(!StringUtils.isBlank(pageRequest.getFilterName()), NavInfo.NAV_NAME, pageRequest.getFilterName());
        Page<NavInfo> userIPage = navInfoMapper.selectPage(page, queryWrapper);
        PageResult pageResult = new PageResult(userIPage);

        return pageResult;
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
        if (Objects.isNull(navInfo.getId())) {
            throw new RuntimeException(StateEnums.PARAMETER_ERROR.getMsg());
        }
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
    public boolean deleteById(List<Integer> ids) {
        if (Objects.isNull(ids)) {
            throw new RuntimeException(StateEnums.PARAMETER_ERROR.getMsg());
        }
        return this.navInfoMapper.deleteBatchIds(ids) > 0;
    }
}

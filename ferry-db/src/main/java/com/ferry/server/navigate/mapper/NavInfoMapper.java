package com.ferry.server.navigate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ferry.server.navigate.entity.NavInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (NavInfo)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-30 23:47:42
 */
public interface NavInfoMapper extends BaseMapper<NavInfo> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NavInfo queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param navInfo 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<NavInfo> queryAllByLimit(NavInfo navInfo, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param navInfo 查询条件
     * @return 总行数
     */
    long count(NavInfo navInfo);

    /**
     * 新增数据
     *
     * @param navInfo 实例对象
     * @return 影响行数
     */
    int insert(NavInfo navInfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<NavInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<NavInfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<NavInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<NavInfo> entities);

    /**
     * 修改数据
     *
     * @param navInfo 实例对象
     * @return 影响行数
     */
    int update(NavInfo navInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}


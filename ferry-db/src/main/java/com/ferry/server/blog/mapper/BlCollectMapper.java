package com.ferry.server.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ferry.server.blog.entity.BlCollect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * @Author: 摆渡人
 * @Date: 2021/6/12
 */
public interface BlCollectMapper extends BaseMapper<BlCollect> {
    int updateBatch(List <BlCollect> list);

    int batchInsert(@Param("list") List <BlCollect> list);

    int insertOrUpdate(BlCollect record);

    int insertOrUpdateSelective(BlCollect record);
}
package com.ferry.server.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ferry.server.blog.entity.BlLabel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * @Author: 摆渡人
 * @Date: 2021/6/9
 */
public interface BlLabelMapper extends BaseMapper<BlLabel> {
    int updateBatch(List <BlLabel> list);

    int batchInsert(@Param("list") List <BlLabel> list);

    int insertOrUpdate(BlLabel record);

    int insertOrUpdateSelective(BlLabel record);
}
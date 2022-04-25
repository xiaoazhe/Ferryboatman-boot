package com.ferry.server.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ferry.server.blog.entity.BlProLabel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * @Author: 摆渡人
 * @Date: 2021/6/9
 */
public interface BlProLabelMapper extends BaseMapper<BlProLabel> {
    int updateBatch(List <BlProLabel> list);

    int batchInsert(@Param("list") List <BlProLabel> list);

    int insertOrUpdate(BlProLabel record);

    int insertOrUpdateSelective(BlProLabel record);

    List<BlProLabel> selectAll();
}
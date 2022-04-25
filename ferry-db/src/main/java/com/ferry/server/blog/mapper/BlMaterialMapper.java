package com.ferry.server.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ferry.server.blog.entity.BlMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * @Author: 摆渡人
 * @Date: 2021/6/17
 */
public interface BlMaterialMapper extends BaseMapper<BlMaterial> {
    int updateBatch(List <BlMaterial> list);

    int batchInsert(@Param("list") List <BlMaterial> list);

    int insertOrUpdate(BlMaterial record);

    int insertOrUpdateSelective(BlMaterial record);
}
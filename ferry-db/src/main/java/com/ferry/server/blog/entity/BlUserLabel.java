package com.ferry.server.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 
 * @Author: 摆渡人
 * @Date: 2021/6/9
 */
@Data
@TableName(value = "bl_user_label")
@EqualsAndHashCode(callSuper = false)
public class BlUserLabel implements Serializable {
    /**
     * 用户ID
     */
    @TableId(value = "uid", type = IdType.INPUT)
    private String uid;

    /**
     * 标签ID
     */
    @TableId(value = "lid", type = IdType.INPUT)
    private String lid;

    private static final long serialVersionUID = 1L;

    public static final String COL_UID = "uid";

    public static final String COL_LID = "lid";
}
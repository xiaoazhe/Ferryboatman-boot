package com.ferry.server.navigate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (NavType)实体类
 *
 * @author makejava
 * @since 2022-09-30 23:47:01
 */
@Data
@TableName(value = "nav_type")
public class NavType implements Serializable {
    private static final long serialVersionUID = 463791060844485480L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类型名称
     */
    @TableId(value = "nav_type_name")
    private String navTypeName;

    /**
     * 父类型id
     */
    @TableId(value = "nav_parent_type_id")
    private Integer navParentTypeId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 更新人
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 是否删除  1：已删除  0：正常
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    public static final String NAV_TYPE_NAME = "nav_type_name";

    public static final String NAV_PARENT_TYPE_ID = "nav_parent_type_id";
}


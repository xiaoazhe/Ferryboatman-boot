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
 * (NavInfo)实体类
 *
 * @author makejava
 * @since 2022-09-30 23:47:42
 */
@Data
@TableName(value = "nav_info")
public class NavInfo implements Serializable {
    private static final long serialVersionUID = 212416113570795405L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类型id
     */
    @TableId(value = "nav_type_id")
    private Integer navTypeId;
    /**
     * 名称
     */
    @TableId(value = "nav_name")
    private String navName;

    /**
     * 描述
     */
    @TableId(value = "nav_desc")
    private String navDesc;

    /**
     * 跳转url
     */
    @TableId(value = "nav_url")
    private String navUrl;

    /**
     * logo链接
     */
    @TableId(value = "nav_logo")
    private String navLogo;

    /**
     * 1:公开；0私有
     */
    @TableId(value = "nav_shared")
    private String navShared;

    /**
     * 标记
     */
    @TableId(value = "nav_tag")
    private String navTag;

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
    @TableId(value = "is_delete")
    private Integer isDelete;

    public static final String NAV_NAME = "nav_name";

}


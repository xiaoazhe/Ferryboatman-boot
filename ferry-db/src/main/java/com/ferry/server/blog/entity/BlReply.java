package com.ferry.server.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Author: 摆渡人
 * @Date: 2021/6/9
 */
@Data
@TableName(value = "bl_reply")
@EqualsAndHashCode(callSuper = false)
public class BlReply implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 问题ID
     */
    @TableField(value = "problemid")
    private String problemid;

    /**
     * 回答内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 创建日期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新日期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 回答人ID
     */
    @TableField(value = "userid")
    private String userid;

    /**
     * 回答人昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 更新人
     */
    @TableField(value = "last_update_by")
    private String lastUpdateBy;

    @TableField(exist = false)
    private BlProblem problem;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_PROBLEMID = "problemid";

    public static final String COL_CONTENT = "content";

    public static final String COL_CREATETIME = "create_time";

    public static final String COL_UPDATETIME = "update_time";

    public static final String COL_USERID = "userid";

    public static final String COL_NICKNAME = "nickname";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_LAST_UPDATE_BY = "last_update_by";
}
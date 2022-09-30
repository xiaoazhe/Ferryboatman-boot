package com.ferry.server.navigate.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (NavType)实体类
 *
 * @author makejava
 * @since 2022-09-30 23:47:01
 */
public class NavType implements Serializable {
    private static final long serialVersionUID = 463791060844485480L;
    
    private Integer id;
    /**
     * 类型名称
     */
    private String navTypeName;
    /**
     * 父类型id
     */
    private String navParentTypeId;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private String lastUpdateBy;
    /**
     * 更新时间
     */
    private Date lastUpdateTime;
    /**
     * 是否删除  1：已删除  0：正常
     */
    private Long isDelete;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNavTypeName() {
        return navTypeName;
    }

    public void setNavTypeName(String navTypeName) {
        this.navTypeName = navTypeName;
    }

    public String getNavParentTypeId() {
        return navParentTypeId;
    }

    public void setNavParentTypeId(String navParentTypeId) {
        this.navParentTypeId = navParentTypeId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Long isDelete) {
        this.isDelete = isDelete;
    }

}


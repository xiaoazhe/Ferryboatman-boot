package com.ferry.server.navigate.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (NavInfo)实体类
 *
 * @author makejava
 * @since 2022-09-30 23:47:42
 */
public class NavInfo implements Serializable {
    private static final long serialVersionUID = 212416113570795405L;
    
    private Integer id;
    /**
     * 类型id
     */
    private Integer navTypeId;
    /**
     * 名称
     */
    private String navName;
    /**
     * 描述
     */
    private String navDesc;
    /**
     * 跳转url
     */
    private String navUrl;
    /**
     * logo链接
     */
    private String navLogo;
    /**
     * 1:公开；0私有
     */
    private String navShared;
    /**
     * 标记
     */
    private String navTag;
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

    public Integer getNavTypeId() {
        return navTypeId;
    }

    public void setNavTypeId(Integer navTypeId) {
        this.navTypeId = navTypeId;
    }

    public String getNavName() {
        return navName;
    }

    public void setNavName(String navName) {
        this.navName = navName;
    }

    public String getNavDesc() {
        return navDesc;
    }

    public void setNavDesc(String navDesc) {
        this.navDesc = navDesc;
    }

    public String getNavUrl() {
        return navUrl;
    }

    public void setNavUrl(String navUrl) {
        this.navUrl = navUrl;
    }

    public String getNavLogo() {
        return navLogo;
    }

    public void setNavLogo(String navLogo) {
        this.navLogo = navLogo;
    }

    public String getNavShared() {
        return navShared;
    }

    public void setNavShared(String navShared) {
        this.navShared = navShared;
    }

    public String getNavTag() {
        return navTag;
    }

    public void setNavTag(String navTag) {
        this.navTag = navTag;
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


package com.ferry.navigate.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 摆渡人
 * @Date: 2022/10/3
 */
@Data
public class NavInfoResponse {

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
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}

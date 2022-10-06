package com.ferry.web.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: 摆渡人
 * @Date: 2022/10/3
 */
@Data
public class NavAggregatesResponse {
    private Integer id;

    /**
     * 类型名称
     */
    private String navTypeName;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private List<NavInfoResponse> navInfoList;

    private List<NavTypeResponse> children;
}

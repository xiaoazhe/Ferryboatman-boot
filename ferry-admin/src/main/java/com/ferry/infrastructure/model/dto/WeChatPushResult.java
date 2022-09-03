package com.ferry.infrastructure.model.dto;

import lombok.Data;

/**
 * 
 * @Author: 摆渡人
 * @Date: 2022/8/23
 * @Description 
 */
@Data
public class WeChatPushResult {

    private Integer errcode;

    private String errmsg;

    private Long msgid;
}

package com.ferry.infrastructure.model.dto;

import lombok.Data;

/**
 * 
 * @Author: 摆渡人
 * @Date: 2022/8/23
 * @Description 
 */
@Data
public class WeChatAcTokenResult {


    private String errcode;

    private String errmsg;

    private String access_token;

    private Integer expires_in;

}

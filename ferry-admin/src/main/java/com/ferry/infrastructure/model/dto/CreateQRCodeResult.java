package com.ferry.infrastructure.model.dto;

import lombok.Data;

/**
 * 
 * @Author: 摆渡人
 * @Date: 2022/8/23
 * @Description 
 */
@Data
public class CreateQRCodeResult {
    private String ticket;

    private String expire_seconds;

    private String url;
}
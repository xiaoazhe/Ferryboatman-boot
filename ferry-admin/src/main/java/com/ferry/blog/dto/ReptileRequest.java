package com.ferry.blog.dto;

import lombok.Data;

/**
 * @Author: 摆渡人
 * @Date: 2022/10/29
 */
@Data
public class ReptileRequest {
    private Long typeId;

    private String url;

    private boolean convertImg;

    private String title;

    private String userId;

    private int count = 1;
}

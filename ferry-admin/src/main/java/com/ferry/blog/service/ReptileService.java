package com.ferry.blog.service;

import com.ferry.blog.dto.ReptileRequest;

import java.io.PrintWriter;

/**
 * @Author: 摆渡人
 * @Date: 2022/10/29
 */
public interface ReptileService {

    void crawlSingle(Long typeId, String url, boolean convertImg, PrintWriter writer);

    void bringBackList(ReptileRequest request, PrintWriter writer);
}

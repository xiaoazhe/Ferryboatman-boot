package com.ferry.web.service;


import com.ferry.server.navigate.entity.NavInfo;
import com.ferry.web.response.NavAggregatesResponse;

import java.util.List;

/**
 * @Author: 摆渡人
 * @Date: 2021/5/7
 */
public interface NavService{

    List<NavAggregatesResponse> findAllNav();

    String insert(NavInfo navInfo);
}

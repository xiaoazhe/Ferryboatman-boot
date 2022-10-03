package com.ferry.navigate.request;

import com.ferry.core.page.PageRequest;
import lombok.Data;

/**
 * @Author: 摆渡人
 * @Date: 2022/10/3
 */
@Data
public class QueryPageRequest extends PageRequest {

    private String filterName;

}

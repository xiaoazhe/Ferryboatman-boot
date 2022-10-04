package com.ferry.navigate.request;

import com.ferry.core.page.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 摆渡人
 * @Date: 2022/10/3
 */
@Data
public class QueryPageRequest extends PageRequest implements Serializable {

    private String filterName;

    private String filterId;

}

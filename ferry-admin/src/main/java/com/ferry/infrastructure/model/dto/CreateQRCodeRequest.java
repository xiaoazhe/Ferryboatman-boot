package com.ferry.infrastructure.model.dto;

import lombok.Data;

/**
 * @Author: 摆渡人
 * @Date: 2022/8/23
 * @Description
 */
@Data
public class CreateQRCodeRequest {

    private String expire_seconds;

    private String action_name;

    private ActionInfo action_info;

    @Data
    public static class ActionInfo{
        private Scene scene;
    }

    @Data
    public static class Scene{

        private Long scene_id;

        private String scene_str;
    }
}

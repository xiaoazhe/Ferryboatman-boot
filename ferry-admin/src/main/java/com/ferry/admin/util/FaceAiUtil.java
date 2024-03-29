package com.ferry.admin.util;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.face.AipFace;
import com.ferry.admin.aspect.SysLogAspect;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

@Component
public class FaceAiUtil {

    @Value("${ai.appId}")
    private String APP_ID;
    @Value("${ai.apiKey}")
    private String API_KEY;
    @Value("${ai.secretKey}")
    private String SECRET_KEY;
    @Value("${ai.imageType}")
    private String IMAGE_TYPE;
    @Value("${ai.groupId}")
    private String groupId;
    @Value("${ai.error.switch}")
    private Boolean aiErrorSwitch;

    private AipFace client;

    private HashMap<String, String> options = new HashMap<String, String>();

    public FaceAiUtil() {
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
    }

    @PostConstruct
    public void init() {
        client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
    }

    //判断用户是否注册了面部信息
    public Boolean faceExist(String userId){
        JSONObject res=client.getUser(userId,groupId,null);
        Integer errorCode=res.getInt("error_code");
        return errorCode==0?true:false;
    }

    /**
     * 用户信息查询接口
     *
     * @param userId - 用户id（由数字、字母、下划线组成），长度限制128B
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject getUser(String userId){
        JSONObject res=client.getUser(userId,groupId,null);
        return res;
    }

    /**
     *  人脸注册 ：将用户照片存入人脸库中
     */
    public JSONObject faceRegister(String userId, String image, boolean type) {
        // 人脸注册
        try {
            JSONObject res = null;
            if (type) {
                res = client.addUser(image, IMAGE_TYPE, groupId, userId, options);
            } else {
                res = client.addUser(image, "URL", groupId, userId, options);
            }
            Integer errorCode = res.getInt("error_code");
            return res;
        } catch (Exception e) {
            throw new RuntimeException("人脸库加入异常: {}", e.getCause());
        }

    }

    /**
     *  人脸更新 ：更新人脸库中的用户照片
     */
    public JSONObject faceUpdate(String userId, String image, boolean type) {
        try {
            // 人脸更新
            JSONObject res = null;
            if (type) {
                res = client.updateUser(image, IMAGE_TYPE, groupId, userId, options);
            } else {
                res = client.updateUser(image, "URL", groupId, userId, options);
            }
            return res;
        }  catch (Exception e) {
            throw new RuntimeException("人脸库更新异常: {}", e.getCause());
        }

    }

    /**
     * 人脸检测：判断上传图片中是否具有面部头像
     */
    public Boolean faceCheck(String image) {
        try {
            JSONObject res = client.detect(image, IMAGE_TYPE, options);
            if (res.has("error_code") && res.getInt("error_code") == 0) {
                JSONObject resultObject = res.getJSONObject("result");
                Integer faceNum = resultObject.getInt("face_num");
                return faceNum == 1 ? true : false;
            }else{
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("人脸检测服务异常:{}", e.getCause());
        }
    }

    /**
     *  人脸查找：查找人脸库中最相似的人脸并返回数据
     *          处理：用户的匹配得分（score）大于80分，即可认为是同一个用户
     */
    public String faceSearch(String image, Boolean type) {
        try {
            JSONObject res = null;
            if (type) {
                res = client.search(image, IMAGE_TYPE, groupId, options);
            } else {
                res = client.search(image, "URL", groupId, options);
            }

            if (res.has("error_code") && res.getInt("error_code") == 0) {
                JSONObject result = res.getJSONObject("result");
                JSONArray userList = result.getJSONArray("user_list");
                if (userList.length() > 0) {
                    JSONObject user = userList.getJSONObject(0);
                    double score = user.getDouble("score");
                    if(score > 80) {
                        return user.getString("user_id");
                    }
                }
            } else if (aiErrorSwitch) {
                logger.info("人脸识别失败，走默认管理员权限res :{} ", JSON.toJSONString(res));
                return "1";
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("人脸查询服务异常:{}", e.getCause());
        }

    }
    private static final Logger logger = LoggerFactory.getLogger(FaceAiUtil.class);

    /**
     * 获取用户人脸列表接口
     *
     * @param userId - 用户id（由数字、字母、下划线组成），长度限制128B
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject faceGetList(String userId) {
        JSONObject res = client.faceGetlist(userId, groupId, options);
        if (res.has("error_code") && res.getInt("error_code") == 0) {
            JSONObject result = res.getJSONObject("result");
            JSONArray userList = result.getJSONArray("face_list");
            return result;
        }
        return null;
    }

    /**
     * 获取用户列表接口
     *
     * options - options列表:
     *   start 默认值0，起始序号
     *   length 返回数量，默认值100，最大值1000
     * @return JSONObject
     */
    public List getGroupUsers() {
        JSONObject res = client.getGroupUsers(groupId, options);
        if (res.has("error_code") && res.getInt("error_code") == 0) {
            JSONObject result = res.getJSONObject("result");
            JSONArray userList = result.getJSONArray("user_id_list");
            return userList.toList();
        }
        return null;
    }
    /**
     * 人脸删除接口
     *
     * @param userId - 用户id（由数字、字母、下划线组成），长度限制128B
     * @param faceToken - 需要删除的人脸图片token，（由数字、字母、下划线组成）长度限制64B
     * options - options列表:
     * @return JSONObject
     */
    public List faceDelete(String userId, String faceToken) {
        JSONObject res = client.faceDelete( userId, groupId, faceToken, options);
        if (res.has("error_code") && res.getInt("error_code") == 0) {
            JSONObject result = res.getJSONObject("result");
            JSONArray userList = result.getJSONArray("user_id_list");
            return userList.toList();
        }
        return null;
    }
}

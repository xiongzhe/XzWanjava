package com.xiongz.android.core.util.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

/**
 * Fastjson工具类
 * Created by xiongz on 2017/12/19.
 */
public class FastjsonUtil {

    /**
     * 解析jsonObject
     *
     * @param text
     * @return
     */
    public static JSONObject parseObject(String text) {
        return JSON.parseObject(text);
    }

    /**
     * 解析jsonObject
     *
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    /**
     * 解析jsonArray
     *
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    /**
     * map -> json
     *
     * @param object
     */
    public static String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }


    /**
     * 解析泛型
     *
     * @param text
     * @param <T>
     * @return
     */
    public static <T> T parseT(String text, TypeReference<T> type) {
        return JSON.parseObject(text, type);
    }

    /**
     * 判断是否是jsonArray
     *
     * @param test
     * @return
     */
    public final static boolean isJSONArrayValid(String test) {
        try {
            JSONArray.parseArray(test);
        } catch (JSONException ex) {
            return false;
        }
        return true;
    }
}

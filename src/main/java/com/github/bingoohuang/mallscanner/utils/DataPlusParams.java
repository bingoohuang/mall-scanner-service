package com.github.bingoohuang.mallscanner.utils;

import com.alibaba.fastjson.JSONObject;

public class DataPlusParams {
    /*
     * 获取参数的json对象
     */
    public static JSONObject getParam(int type, String dataValue) {
        JSONObject obj = new JSONObject();
        obj.put("dataType", type);
        obj.put("dataValue", dataValue);

        return obj;
    }

}

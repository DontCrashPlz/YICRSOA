package com.hnsi.zheng.hnti_erp_app.beans;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * 用于把网络请求得到的jsonObject传入到事件详情页中去
 * Created by Zheng on 2016/6/23.
 */
public class MatterInfoEntity implements Serializable{
    private JSONObject jsonObject;

    @Override
    public String toString() {
        return "MatterInfoEntity{" +
                "jsonObject=" + jsonObject +
                '}';
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}

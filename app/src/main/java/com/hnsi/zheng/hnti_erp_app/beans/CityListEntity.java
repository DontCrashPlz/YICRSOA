package com.hnsi.zheng.hnti_erp_app.beans;

import org.json.JSONArray;

/**
 * 城市列表数据实体
 * Created by Zheng on 2016/8/31.
 */
public class CityListEntity {
    private String province="";
    private JSONArray cityListArray;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public JSONArray getCityListArray() {
        return cityListArray;
    }

    public void setCityListArray(JSONArray cityListArray) {
        this.cityListArray = cityListArray;
    }

    @Override
    public String toString() {
        return "CityListEntity{" +
                "province='" + province + '\'' +
                ", cityListArray=" + cityListArray +
                '}';
    }
}

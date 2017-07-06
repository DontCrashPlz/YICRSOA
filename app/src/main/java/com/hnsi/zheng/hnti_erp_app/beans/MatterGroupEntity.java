package com.hnsi.zheng.hnti_erp_app.beans;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 事件分组数据实体
 * Created by zheng on 2016/6/26.
 */
public class MatterGroupEntity {
    private String groupKey="";
    private boolean audit;
    private String label="";
    private ArrayList<JSONObject>childList=new ArrayList<>();

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public ArrayList<JSONObject> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<JSONObject> childList) {
        this.childList = childList;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isAudit() {
        return audit;
    }

    public void setAudit(boolean audit) {
        this.audit = audit;
    }

    @Override
    public String toString() {
        return "MatterGroupEntity{" +
                "groupKey='" + groupKey + '\'' +
                ", Audit=" + audit +
                ", label='" + label + '\'' +
                ", childList=" + childList +
                '}';
    }
}

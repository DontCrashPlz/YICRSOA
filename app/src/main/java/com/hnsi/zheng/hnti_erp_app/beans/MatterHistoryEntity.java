package com.hnsi.zheng.hnti_erp_app.beans;

import java.io.Serializable;

/**
 * 事件审批记录信息实体
 * Created by Zheng on 2016/7/4.
 */
public class MatterHistoryEntity implements Serializable{
    private String name="";
    private String endTime="";
    private String activityName="";
    private String decision;
    private String content="";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "MatterHistoryEntity{" +
                "name='" + name + '\'' +
                ", endTime='" + endTime + '\'' +
                ", activityName='" + activityName + '\'' +
                ", decision='" + decision + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

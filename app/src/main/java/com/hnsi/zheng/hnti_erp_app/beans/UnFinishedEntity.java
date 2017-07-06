package com.hnsi.zheng.hnti_erp_app.beans;

/**
 * 待办事项的数据实体
 * Created by Zheng on 2016/6/20.
 */
public class UnFinishedEntity {
    /** 分类名称 */
    private String processChName="";
    /** 标题名称 */
    private String processInstName="";
    /** 开始时间 */
    private String startTime="";
    /** 参数一 */
    private int workItemID;
    /** 参数二 */
    private String activityDefID="";
    /** 参数三 */
    private int processInstID;
    /** urlMap中对应的url项的key */
    private String processDefName="";

    public String getProcessChName() {
        return processChName;
    }

    public void setProcessChName(String processChName) {
        this.processChName = processChName;
    }

    public String getProcessDefName() {
        return processDefName;
    }

    public void setProcessDefName(String processDefName) {
        this.processDefName = processDefName;
    }

    public int getProcessInstID() {
        return processInstID;
    }

    public void setProcessInstID(int processInstID) {
        this.processInstID = processInstID;
    }

    public String getActivityDefID() {
        return activityDefID;
    }

    public void setActivityDefID(String activityDefID) {
        this.activityDefID = activityDefID;
    }

    public int getWorkItemID() {
        return workItemID;
    }

    public void setWorkItemID(int workItemID) {
        this.workItemID = workItemID;
    }

    public String getProcessInstName() {
        return processInstName;
    }

    public void setProcessInstName(String processInstName) {
        this.processInstName = processInstName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "UnFinishedEntity{" +
                "processChName='" + processChName + '\'' +
                ", processInstName='" + processInstName + '\'' +
                ", createTime='" + startTime + '\'' +
                ", workItemID=" + workItemID +
                ", activityDefID='" + activityDefID + '\'' +
                ", processInstID=" + processInstID +
                ", processDefName='" + processDefName + '\'' +
                '}';
    }
}

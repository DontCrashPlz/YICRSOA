package com.hnsi.zheng.hnti_erp_app.beans;

/**
 * 已办事项的数据实体
 * Created by Zheng on 2016/6/21.
 */
public class FinishedEntity {
    /** 分类名称 */
    private String processChName="";
    /** 标题名称 */
    private String processInstName="";
    /** 开始时间 */
    private String startTime="";
    /** 参数名称 */
    private String processInstId="";
    /** urlMap中对应的key */
    private String processDefName="";

    public String getProcessChName() {
        return processChName;
    }

    public void setProcessChName(String processChName) {
        this.processChName = processChName;
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

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public String getProcessDefName() {
        return processDefName;
    }

    public void setProcessDefName(String processDefName) {
        this.processDefName = processDefName;
    }

    @Override
    public String toString() {
        return "FinishedEntity{" +
                "processChName='" + processChName + '\'' +
                ", processInstName='" + processInstName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", processInstId='" + processInstId + '\'' +
                ", processDefName='" + processDefName + '\'' +
                '}';
    }
}

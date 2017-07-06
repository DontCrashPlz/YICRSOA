package com.hnsi.zheng.hnti_erp_app.beans;

/**
 * 规章制度数据实体
 * Created by Zheng on 2016/7/27.
 */
public class RulesEntity {
    private int id;
    private String title="";
    private String operationTime="";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    @Override
    public String toString() {
        return "RulesEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", operationTime='" + operationTime + '\'' +
                '}';
    }
}
//        {
//        "id":1,
//        "title":"绩效考核制度",
//        "operationTime":"2016-07-26 10:48:13.0"
//        }

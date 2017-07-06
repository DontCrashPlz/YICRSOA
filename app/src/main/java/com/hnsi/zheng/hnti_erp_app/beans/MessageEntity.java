package com.hnsi.zheng.hnti_erp_app.beans;

/**
 * 消息数据实体
 * Created by Zheng on 2016/8/4.
 */
public class MessageEntity {
    private int id;
    private String title="";
    private String msgType="";
    private String sendEmpname="";
    private String sendTime="";

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

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getSendEmpname() {
        return sendEmpname;
    }

    public void setSendEmpname(String sendEmpname) {
        this.sendEmpname = sendEmpname;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", msgType='" + msgType + '\'' +
                ", sendEmpname='" + sendEmpname + '\'' +
                ", sendTime='" + sendTime + '\'' +
                '}';
    }
}
//        {
//        "id":1514,
//        "msgType":"流程反馈",
//        "title":"项目策划流程结束",
//        "sendEmpname":"朱家昌",
//        "sendTime":"2016-08-03 00:00:00.0"
//        }
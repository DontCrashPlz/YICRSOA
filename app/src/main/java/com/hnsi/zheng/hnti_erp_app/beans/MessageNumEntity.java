package com.hnsi.zheng.hnti_erp_app.beans;

/**
 * 消息数量信息实体
 * Created by Zheng on 2016/11/10.
 */
public class MessageNumEntity {
    //待办流程数量
    private int flowNum;
    //公文传阅数量
    private int docNum;
    //消息数量
    private int msgNum;

    public int getMsgNum() {
        return msgNum;
    }

    public void setMsgNum(int msgNum) {
        this.msgNum = msgNum;
    }

    public int getDocNum() {
        return docNum;
    }

    public void setDocNum(int docNum) {
        this.docNum = docNum;
    }

    public int getFlowNum() {
        return flowNum;
    }

    public void setFlowNum(int flowNum) {
        this.flowNum = flowNum;
    }

    @Override
    public String toString() {
        return "MessageNumEntity{" +
                "flowNum=" + flowNum +
                ", docNum=" + docNum +
                ", msgNum=" + msgNum +
                '}';
    }
}

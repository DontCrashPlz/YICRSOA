package com.hnsi.zheng.hnti_erp_app.beans;

/**
 * 新闻实体
 * Created by Zheng on 2016/5/23.
 */
public class NewsEntity {
    private int id;
    private int newsClass;
    private String classname="";
    private String startDate="";
    private String operatorName="";
    private String title="";
    private String operationDeptName="";
    private String imgsrc="";

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNewsClass() {
        return newsClass;
    }

    public void setNewsClass(int newsClass) {
        this.newsClass = newsClass;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOperationDeptName() {
        return operationDeptName;
    }

    public void setOperationDeptName(String operationDeptName) {
        this.operationDeptName = operationDeptName;
    }

    @Override
    public String toString() {
        return "NewsEntity{" +
                "id=" + id +
                ", newsClass=" + newsClass +
                ", classname='" + classname + '\'' +
                ", startDate='" + startDate + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", title='" + title + '\'' +
                ", operationDeptName='" + operationDeptName + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                '}';
    }
}
//        "id":1321,
//        "newsClass":3,
//        "classname":"内部新闻",
//        "startDate":"2016-09-29 00:00:00.0",
//        "operatorName":"sysadmin",
//        "title":"测试1",
//        "viewScope":1,
//        "firstDeptId":8,
//        "operationDeptName":"软信科技子公司",
//        "imgsrc":"/default/erp/common/fileUpload/download.jsp?isOpen=false&fileid=18205"
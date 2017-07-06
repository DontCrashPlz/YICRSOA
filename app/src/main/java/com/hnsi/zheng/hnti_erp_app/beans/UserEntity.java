package com.hnsi.zheng.hnti_erp_app.beans;

/**
 * 用户信息实体，用于登录后保存用户信息。
 * Created by Zheng on 2016/5/13.
 */
public class UserEntity {
    //姓名
    private String empname="";
    //手机
    private String mobileno="";
    //办公电话
    private String otel="";
    //邮箱
    private String oemail="";
    //性别
    private String sex="";
    //部门
    private String orgname="";
    //职务
    private String posiname="";
    //头像url
    private String headimg="";

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getOtel() {
        return otel;
    }

    public void setOtel(String otel) {
        this.otel = otel;
    }

    public String getOemail() {
        return oemail;
    }

    public void setOemail(String oemail) {
        this.oemail = oemail;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getPosiname() {
        return posiname;
    }

    public void setPosiname(String posiname) {
        this.posiname = posiname;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "empname='" + empname + '\'' +
                ", mobileno='" + mobileno + '\'' +
                ", otel='" + otel + '\'' +
                ", oemail='" + oemail + '\'' +
                ", sex='" + sex + '\'' +
                ", orgname='" + orgname + '\'' +
                ", posiname='" + posiname + '\'' +
                ", headimg='" + headimg + '\'' +
                '}';
    }
}

//    {
//        "msg":"登录成功",               提示信息
//        "success":true,                登录状态
//        "userInfo":{
//        "empname":"sysadmin",          姓名
//        "mobileno":"18637151545",      手机
//        "otel":"18637151545",          办公电话
//        "oemail":null,                 邮箱
//        "sex":"女",                    性别
//        "orgid":8,                     部门id
//        "orgname":"软信科技子公司",      部门
//        "posiname":"分公司副总经理",     职务
//        "empid":1                         作用未知
//        "headimg":"/default/erp/common/fileUpload/download.jsp?isOpen=false&fileid=null" 头像url
//        }
//    }
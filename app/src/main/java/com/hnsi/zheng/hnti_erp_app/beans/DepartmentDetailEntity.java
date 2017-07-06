package com.hnsi.zheng.hnti_erp_app.beans;

import java.io.Serializable;

/**
 * 部门详情数据实体
 * 人员信息实体
 * 用于存储部门下人员的详细信息
 * Created by Zheng on 2016/5/17.
 */
public class DepartmentDetailEntity implements Serializable{
    private String empname="";
    private String sex="";
    private String mobileno="";
    private String orgname="";
    private String posiname="";
    private String otel="";
    private String oemail="";
    private int empid;
    private String headimgUrl="";
    private int orgid;

    public int getOrgid() {
        return orgid;
    }

    public void setOrgid(int orgid) {
        this.orgid = orgid;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getOtel() {
        return otel;
    }

    public void setOtel(String otel) {
        this.otel = otel;
    }

    public String getPosiname() {
        return posiname;
    }

    public void setPosiname(String posiname) {
        this.posiname = posiname;
    }

    public String getOemail() {
        return oemail;
    }

    public void setOemail(String oemail) {
        this.oemail = oemail;
    }

    public String getHeadimgUrl() {
        return headimgUrl;
    }

    public void setHeadimgUrl(String headimgUrl) {
        this.headimgUrl = headimgUrl;
    }

    @Override
    public String toString() {
        return "DepartmentDetailEntity{" +
                "empname='" + empname + '\'' +
                ", sex='" + sex + '\'' +
                ", mobileno='" + mobileno + '\'' +
                ", orgname='" + orgname + '\'' +
                ", posiname='" + posiname + '\'' +
                ", otel='" + otel + '\'' +
                ", oemail='" + oemail + '\'' +
                ", empid=" + empid +
                ", headimgUrl='" + headimgUrl + '\'' +
                ", orgid=" + orgid +
                '}';
    }
}

//        {
//        "empname":"sysadmin",
//        "mobileno":"18637151545",
//        "otel":"18637151545",
//        "oemail":null,
//        "sex":"男",
//        "orgid":8,
//        "orgname":"软信科技子公司",
//        "posiname":"分公司副总经理",
//        "empid":1,
//        "headimg":"/default/erp/common/fileUpload/download.jsp?isOpen=false&fileid=16183"
//        }

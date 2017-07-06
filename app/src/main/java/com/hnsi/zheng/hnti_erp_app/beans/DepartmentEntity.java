package com.hnsi.zheng.hnti_erp_app.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 部门信息实体
 * 用于存储部门的详细信息
 * Created by Zheng on 2016/5/17.
 */
public class DepartmentEntity implements Serializable{

    private int orgid;
    private String orgname="";
    private int parentorgid;
    //部门列表
    private ArrayList <DepartmentEntity> childDepartmentList=new ArrayList<>();
    //人员列表
    private ArrayList <DepartmentDetailEntity> detailList=new ArrayList<>();

    public int getOrgid() {
        return orgid;
    }

    public void setOrgid(int orgid) {
        this.orgid = orgid;
    }

    public ArrayList<DepartmentEntity> getChildDepartmentList() {
        return childDepartmentList;
    }

    public void setChildDepartmentList(ArrayList<DepartmentEntity> childDepartmentList) {
        this.childDepartmentList = childDepartmentList;
    }

    public ArrayList<DepartmentDetailEntity> getDetailList() {
        return detailList;
    }

    public void setDetailList(ArrayList<DepartmentDetailEntity> detailList) {
        this.detailList = detailList;
    }

    public int getParentorgid() {
        return parentorgid;
    }

    public void setParentorgid(int parentorgid) {
        this.parentorgid = parentorgid;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    @Override
    public String toString() {
        return "DepartmentEntity{" +
                "orgid=" + orgid +
                ", orgname='" + orgname + '\'' +
                ", parentorgid=" + parentorgid +
                ", childDepartmentList=" + childDepartmentList +
                ", detailList=" + detailList +
                '}';
    }
}

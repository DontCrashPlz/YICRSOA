package com.hnsi.zheng.hnti_erp_app.newcontacts;

import java.util.List;

/**
 * Created by Zheng on 2017/5/27.
 */

public class ItemData {

    public static final int ITEM_TYPE_PARENT = 0;
    public static final int ITEM_TYPE_CHILD_PARENT = 1;
    public static final int ITEM_TYPE_CHILD = 2;

    private int type;
    private String text;
    private String text2;
    private boolean expand;
    private List<ItemData> children;

    private int orgid;
    private int parentorgid;
    private String sex;
    private String iconUrl;
    private int empid;

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public List<ItemData> getChildren() {
        return children;
    }

    public void setChildren(List<ItemData> children) {
        this.children = children;
    }

    public int getOrgid() {
        return orgid;
    }

    public void setOrgid(int orgid) {
        this.orgid = orgid;
    }

    public int getParentorgid() {
        return parentorgid;
    }

    public void setParentorgid(int parentorgid) {
        this.parentorgid = parentorgid;
    }

//    public ItemData(int type, String text, String text2, boolean expand, List<ItemData> children, int orgid, int parentorgid) {
//
//        this.type = type;
//        this.text = text;
//        this.text2 = text2;
//        this.expand = expand;
//        this.children = children;
//        this.orgid = orgid;
//        this.parentorgid = parentorgid;
//    }
}

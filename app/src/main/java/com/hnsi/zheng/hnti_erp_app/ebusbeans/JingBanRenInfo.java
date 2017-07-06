package com.hnsi.zheng.hnti_erp_app.ebusbeans;

/**
 * Created by Zheng on 2017/5/25.
 */

public class JingBanRenInfo {
    private int empId;
    private String empName;
    public JingBanRenInfo(int empId,String empName){
        this.empId=empId;
        this.empName=empName;
    }

    public int getEmpId() {
        return empId;
    }

    public String getEmpName() {
        return empName;
    }
}

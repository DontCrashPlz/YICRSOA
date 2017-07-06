package com.hnsi.zheng.hnti_erp_app.shareclass;

import java.util.HashMap;

/**
 * 审批事件审批提交的参数实体
 * 采用一个全局单例的HashMap(参数不需要考虑提交次序)
 * Created by Zheng on 2016/6/30.
 */
public class HashMap2MatterParam {

    private static HashMap2MatterParam current;

    public static HashMap2MatterParam getInstance() {
        synchronized (HashMap2MatterParam.class) {
            if(current == null) {
                current = new HashMap2MatterParam();
            }
            return current;
        }
    }

    HashMap<String, String> mHashMap;

    public HashMap2MatterParam() {
        mHashMap = new HashMap<>();
    }

    /**
     * 向审批提交参数map里放入参数数据
     * @param key 参数名
     * @param value 参数值
     */
    public void putEle(String key,String value){
        mHashMap.put(key,value);
    }

    /**
     * 获取审批提交参数map
     * @return
     */
    public HashMap get(){
        return mHashMap;
    }

}

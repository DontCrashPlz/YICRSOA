package com.hnsi.zheng.hnti_erp_app.shareclass;

import java.util.LinkedHashMap;

/**
 * 需要传递到TableView的LinkedHashMap
 * Created by Zheng on 2016/6/29.
 */
public class LinkedHashMap2TableViewActivity {

        private static LinkedHashMap2TableViewActivity current;

        public static LinkedHashMap2TableViewActivity getInstance() {
            synchronized (LinkedHashMap2TableViewActivity.class) {
                if(current == null) {
                    current = new LinkedHashMap2TableViewActivity();
                }
                return current;
            }
        }

        LinkedHashMap<String, String> mLinkedHashMap;

        public LinkedHashMap2TableViewActivity() {
            mLinkedHashMap = new LinkedHashMap<>();
        }

        public void put(LinkedHashMap data) {
            mLinkedHashMap=data;
        }

        public LinkedHashMap get() {
            return mLinkedHashMap;
        }
}

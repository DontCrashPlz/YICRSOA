package com.hnsi.zheng.hnti_erp_app.widgets;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.hnsi.zheng.hnti_erp_app.utils.DataCleanManager;

/**
 * 清除缓存弹窗
 * Created by Zheng on 2016/7/25.
 */
public class CleanCacheDialog extends AlertDialog{

    protected CleanCacheDialog(Context context) {
        super(context);
    }

    public static CleanCacheDialog showDialog(final Context context) {
        final CleanCacheDialog mDialogInstance = new CleanCacheDialog(context);
        mDialogInstance.setCancelable(true);
        mDialogInstance.setTitle("清除缓存");
        mDialogInstance.setMessage("您确定要清除缓存吗？");
        mDialogInstance.setButton(DialogInterface.BUTTON_NEGATIVE,
                "取消",
                new OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {}
                });
        mDialogInstance.setButton(
                DialogInterface.BUTTON_POSITIVE,
                "确定",
                new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialogInstance.dismiss();
                        DataCleanManager.cleanAppCache(context);
                    }
                });
        mDialogInstance.show();
        return mDialogInstance;
    }

}

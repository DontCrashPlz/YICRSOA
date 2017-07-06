package com.hnsi.zheng.hnti_erp_app.widgets;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * 退出弹窗
 * Created by Zheng on 2016/7/25.
 */
public class ExitDialog extends AlertDialog{

    protected ExitDialog(Activity activity) {
        super(activity);
    }

    public static ExitDialog showDialog(final Activity activity) {
        final ExitDialog mDialogInstance = new ExitDialog(activity);
        mDialogInstance.setCancelable(true);
        mDialogInstance.setTitle("退出应用");
        mDialogInstance.setMessage("您确定要退出应用吗？");
        mDialogInstance.setButton(DialogInterface.BUTTON_NEGATIVE,
                "取消",
                new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {}
                });
        mDialogInstance.setButton(
                DialogInterface.BUTTON_POSITIVE,
                "退出",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialogInstance.dismiss();
                        activity.finish();
                    }
                });
        mDialogInstance.show();
        return mDialogInstance;
    }

}

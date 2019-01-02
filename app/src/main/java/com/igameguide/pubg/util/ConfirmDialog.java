package com.igameguide.pubg.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.igameguide.pubg.R;

public class ConfirmDialog {

    //点击确认按钮回调接口
    public interface OnConfirmListener {
        public void onConfirmClick();
    }

    /**
     * @Title: show
     * @Description: 显示Dialog
     * @param activity
     *            提示内容
     *            void
     * @throws
     */
    public static void show(Activity activity,
                            final OnConfirmListener confirmListenerOne, final OnConfirmListener confirmListenerTwo) {
        // 加载布局文件
        View view = View.inflate(activity, R.layout.confirm_dialog, null);
        TextView confirm = (TextView) view.findViewById(R.id.confirm);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        // 创建Dialog
        final AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialog.setCancelable(false);// 设置点击dialog以外区域不取消Dialog
        dialog.show();
        dialog.setContentView(view);
        dialog.getWindow().setLayout(getWidth(activity) / 20 * 19,
                WindowManager.LayoutParams.WRAP_CONTENT);//设置弹出框宽度为屏幕宽度的三分之二

        // 确定
        confirm.setOnClickListener(v -> {
            dialog.dismiss();
            confirmListenerTwo.onConfirmClick();
        });
        // 取消
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
            confirmListenerOne.onConfirmClick();
        });
    }


    /**
     * 判断字符串是否为null或空
     *
     * @param string
     * @return true:为 空或null;false:不为 空或null
     */
    public static boolean isNullOrEmpty(String string) {
        boolean flag = false;
        if (null == string || string.trim().length() == 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 获取屏幕宽度
     *
     * @param activity
     * @return
     */
    public static int getWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        return width;
    }

}

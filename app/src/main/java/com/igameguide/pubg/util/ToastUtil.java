package com.igameguide.pubg.util;

import android.widget.Toast;

import com.igameguide.pubg.base.PubgApplication;

public class ToastUtil {
    /**
     * 显示提示框
     * @param text
     */
    public static void showToas(String text) {
        Toast.makeText(PubgApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
    }
}

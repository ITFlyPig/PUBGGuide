package com.statstracker.forpubggame.util.defaulthelper;

import android.view.View;
import android.view.ViewGroup;

import com.statstracker.forpubggame.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * 提供Activity一些公用的View
 */
public class CommonActivityViewHelper {
    private ViewGroup mDefaultContainer;
    private CommonActItf mCommonAct;

    private CommonActivityViewHelper(){};//禁止使用无参构造函数

    /**
     * 初始化
     * @param defaultViewContainer
     */
    public CommonActivityViewHelper(ViewGroup defaultViewContainer) {
        if (defaultViewContainer == null) {
            return;
        }
        mDefaultContainer = defaultViewContainer;
        mCommonAct = new CommonActImpl();
    }

    /**
     * 显示加载动画
     */
    public void showLoading() {
        if (mDefaultContainer == null || mCommonAct == null) {
            return;
        }
        View v = mDefaultContainer.findViewById(mCommonAct.getLoadingViewid());
        if (v == null) {
            v = mCommonAct.getLoadingView();
            mDefaultContainer.addView(v, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        } else {
            v.setVisibility(View.VISIBLE);
        }
        AVLoadingIndicatorView loadingIndicatorView = v.findViewById(R.id.loading);
        loadingIndicatorView.show();

    }

    /**
     * 隐藏加载动画
     */
    public void hiddenLoading() {
        if (mDefaultContainer == null || mCommonAct == null) {
            return;
        }
        View v = mDefaultContainer.findViewById(mCommonAct.getLoadingViewid());
        if (v == null) {
           return;

        } else {
            v.setVisibility(View.GONE);
        }

    }

    /**
     * 显示加载错误页面
     */
    public void showError() {

    }

    /**
     * 显示加载为空的页面
     */
    public void showEmpty() {

    }

    public void release() {
        mDefaultContainer = null;
    }
}

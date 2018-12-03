package com.igameguide.pubg.util.defaulthelper;

import android.view.LayoutInflater;
import android.view.View;

import com.igameguide.pubg.R;
import com.igameguide.pubg.base.PubgApplication;

public class CommonActImpl implements CommonActItf {
    @Override
    public View getLoadingView() {
        return LayoutInflater.from(PubgApplication.getInstance()).inflate(R.layout.view_loading, null);
    }

    @Override
    public int getLoadingViewid() {
        return R.id.rl_loading_whole;
    }

    @Override
    public View getEmptyView() {
        return null;
    }

    @Override
    public int getEmptyViewId() {
        return 0;
    }

    @Override
    public View getErrorView() {
        return null;
    }

    @Override
    public int getErrorViewId() {
        return 0;
    }
}

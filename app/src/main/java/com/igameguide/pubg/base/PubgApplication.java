package com.igameguide.pubg.base;

import android.app.Application;

import com.lzy.okgo.OkGo;

public class PubgApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initNetwork();
    }


    /**
     * 初始化网络框架
     */
    private void initNetwork(){
        OkGo.getInstance().init(this);
    }
}

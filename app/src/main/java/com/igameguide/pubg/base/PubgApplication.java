package com.igameguide.pubg.base;

import android.app.Application;

import com.lzy.okgo.OkGo;

public class PubgApplication extends Application {

    private static PubgApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initNetwork();
    }


    /**
     * 初始化网络框架
     */
    private void initNetwork(){
        OkGo.getInstance().init(this);
    }


    public static PubgApplication getInstance() {
        return mInstance;
    }


}

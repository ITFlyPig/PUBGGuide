package com.igameguide.pubg.base;

import android.support.multidex.MultiDexApplication;

import com.bumptech.glide.annotation.GlideModule;
import com.google.android.gms.ads.MobileAds;
import com.lzy.okgo.OkGo;
import com.igameguide.pubg.R;
import com.umeng.analytics.MobclickAgent;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PubgApplication extends MultiDexApplication {

    private static PubgApplication mInstance;
    private Executor mExecutor = Executors.newFixedThreadPool(4);

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initNetwork();
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, getString(R.string.umeng_key), "release", MobclickAgent.EScenarioType.E_UM_NORMAL, true));
        MobileAds.initialize(this);

        MobclickAgent.setDebugMode(true);

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

    /**
     * 添加任务到线程池
     * @param runnable
     */
    public void addTask(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        mExecutor.execute(runnable);

    }


}

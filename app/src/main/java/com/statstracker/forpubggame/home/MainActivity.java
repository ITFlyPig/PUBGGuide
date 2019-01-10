package com.statstracker.forpubggame.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.statstracker.forpubggame.R;
import com.statstracker.forpubggame.pic.WallPaperFragment;
import com.statstracker.forpubggame.util.Constant;
import com.statstracker.forpubggame.util.GoogleBillingUtil;
import com.statstracker.forpubggame.util.LocalUtil;
import com.statstracker.forpubggame.util.PayStatusUtil;
import com.statstracker.forpubggame.video.VideoFragment;
import com.statstracker.forpubggame.weapon.WeaponFragment;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    MainContract.Presenter mPresenter;
    ViewPager viewPager;
    CommonTabLayout tabLayout;


    private String[] mTitles = new String[]{"战绩", "视频", "武器", "壁纸"};
    private List<Fragment> mFragments;
    private int[] mIconUnselectIds = {
            R.mipmap.icon_data_unsel, R.mipmap.icon_guides_unsel,
             R.mipmap.icon_weapon_unsel, R.mipmap.icon_video_unsel, R.mipmap.icon_wallpaper_nusel};
    private int[] mIconSelectIds = {
            R.mipmap.icon_data_sel, R.mipmap.icon_guides_sel,
             R.mipmap.icon_weapon_sel, R.mipmap.icon_video_sel, R.mipmap.icon_wallpaper_sel};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    private String stats;
    private String videos;
    private String weapons;
    private String wallpapers;


    private GoogleBillingUtil googleBillingUtil;
    private static  String SUB_ID;//订阅的id
    private MyOnPurchaseFinishedListener mOnPurchaseFinishedListener = new MyOnPurchaseFinishedListener();//购买回调接口
    private MyOnQueryFinishedListener mOnQueryFinishedListener = new MyOnQueryFinishedListener();//查询回调接口
    private MyOnStartSetupFinishedListener mOnStartSetupFinishedListener = new MyOnStartSetupFinishedListener();//启动结果回调接口


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MainPresenter(this);

        stats = getResources().getString(R.string.stats);
        videos = getResources().getString(R.string.videos);
        weapons = getResources().getString(R.string.weapons);
        wallpapers = getResources().getString(R.string.wallpapers);


        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);

        if (LocalUtil.match("en", "US", this)) {
            mTitles = new String[]{stats, weapons};
            mIconUnselectIds = new int[] {R.mipmap.icon_data_unsel, R.mipmap.icon_weapon_unsel};
            mIconSelectIds = new int[] {R.mipmap.icon_data_sel, R.mipmap.icon_weapon_sel};
        } else {
            mTitles = new String[]{stats, videos, weapons, wallpapers};
        }


        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {

            String title = mTitles[i];
            Fragment f = getFragmentByName(title);
            if (f == null) {
                continue;
            }
            mFragments.add(f);
            mTabEntities.add(new TabEntity(title, mIconSelectIds[i], mIconUnselectIds[i]));
        }

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabLayout.setTabData(mTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                tabLayout.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        MobclickAgent.setDebugMode(true);

        EventBus.getDefault().register(this);

        SUB_ID = GoogleBillingUtil.getInstance().subsSKUS[0];
    }


    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;

    }


    @Override
    public void onLoadStart() {

    }

    @Override
    public void onLoadFail() {

    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private Fragment getFragmentByName(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        if (TextUtils.equals(name, stats)) {
//            Log.d("wyl", name + " StandingsFragment");
            return StandingsFragment.getInstance();
        } else if (TextUtils.equals(name, videos)) {
//            Log.d("wyl", name + " VideoFragment");
            return VideoFragment.getInstance();
        }else if (TextUtils.equals(name, weapons)) {
//            Log.d("wyl", name + " WeaponFragment");
            return WeaponFragment.getInstance();
        }else if (TextUtils.equals(name, wallpapers)) {
//            Log.d("wyl", name + " WallPaperFragment");
            return WallPaperFragment.getInstance();
        }
        return null;

    }


    //查询商品信息回调接口
    private class MyOnQueryFinishedListener implements GoogleBillingUtil.OnQueryFinishedListener {
        @Override
        public void onQuerySuccess(String skuType, List<SkuDetails> list) {
            Log.d("wyl", "查询商品信息回调接口 onQuerySuccess");
            if (list != null) {
                for (SkuDetails skuDetails : list) {
                    String log = "";
                    if (skuType == BillingClient.SkuType.INAPP) {
                        log += "内购的商品:";
                    } else if (skuType == BillingClient.SkuType.SUBS) {
                        log += "订阅的商品:";
                    }
                    Log.d("wyl", log + skuDetails.getTitle() + " 序列号：" + skuDetails.getSku() + " 价格：" + skuDetails.getPrice());
                }
            }


            //查询成功，返回商品列表，
            //skuDetails.getPrice()获得价格(文本)
            //skuDetails.getType()获得类型 sub或者inapp,因为sub和inapp的查询结果都走这里，所以需要判断。
            //googleBillingUtil.getSubsPositionBySku(skuDetails.getSku())获得当前subs sku的序号
            //googleBillingUtil.getInAppPositionBySku(skuDetails.getSku())获得当前inapp suk的序号
        }

        @Override
        public void onQueryFail(int responseCode) {
            Log.d("wyl", "查询商品信息回调接口 onQueryFail");
            //查询失败

        }

        @Override
        public void onQueryError() {
            //查询错误
            Log.d("wyl", "查询商品信息回调接口 onQueryError");
        }
    }

    //服务初始化结果回调接口
    private class MyOnStartSetupFinishedListener implements GoogleBillingUtil.OnStartSetupFinishedListener {
        @Override
        public void onSetupSuccess() {
            Log.d("wyl", "服务初始化结果回调接口 onSetupSuccess");


            Log.d("wyl", "开始查询已经购买商品");
            List<Purchase> inapps = googleBillingUtil.queryPurchasesInApp();
            if (inapps != null) {
                for (Purchase inapp : inapps) {
                    Log.d("wyl", "已经购买的商品：" + inapp.getSku());
                }
            }
            Log.d("wyl", "开始查询已经订阅商品");
            List<Purchase> subs = googleBillingUtil.queryPurchasesSubs();
            if (subs != null) {
                for (Purchase sub : subs) {
                    Log.d("wyl", "已经订阅的商品：" + sub.getSku());
                }
            }

            int size = googleBillingUtil.getPurchasesSizeSubs();
            Log.d("wyl", "获取有效订阅的数量：" + size);
            handleQueryResult(size);



//            Toast.makeText(BaseApplication.getInstance(), "DownPro 有效订阅的数量：" + size + ":::" + (subs == null ? 0 :subs.size()), Toast.LENGTH_LONG).show();

        }

        @Override
        public void onSetupFail(int responseCode) {
            Log.d("wyl", "服务初始化结果回调接口 onSetupFail");
            handleQueryResult(-1);

        }

        @Override
        public void onSetupError() {
            Log.d("wyl", "服务初始化结果回调接口 onSetupError");
            handleQueryResult(-1);

        }
    }

    //购买商品回调接口
    private class MyOnPurchaseFinishedListener implements GoogleBillingUtil.OnPurchaseFinishedListener {
        @Override
        public void onPurchaseSuccess(List<Purchase> list) {
            //内购或者订阅成功,可以通过purchase.getSku()获取suk进而来判断是哪个商品
            Log.d("wyl", "购买商品回调接口 onPurchaseSuccess");
            if (list != null && list.size() > 0) {

                //订阅成功，取消弹出框
                EventBus.getDefault().post(new EventModel(Constant.Event.DISS_DIALOG));


                for (Purchase purchase : list) {
                    String sku = purchase.getSku();
                    if (!TextUtils.isEmpty(sku) && TextUtils.equals(sku, SUB_ID)) {//订阅商品成功，记录
                        PayStatusUtil.savePaySubStatus(true);
                    }

                    String log = "";
                    if (googleBillingUtil.handlePurchase(purchase)) {
                        log = log + "商品序列号：" + purchase.getSku();
                        Log.d("wyl", " 尚明" + "购买的商品通过验证：" + purchase.getSignature());
                    } else {
                        log = log + "商品序列号：" + purchase.getSku();
                        Log.d("wyl", "购买的商品未通过验证：" + purchase.getSignature());
                    }
                    Log.d("wyl", "购买或者订阅成功：" + log);
                }
            }
        }

        @Override
        public void onPurchaseFail(int responseCode) {
            Log.d("wyl", "购买商品回调接口 onPurchaseFail：" + responseCode);

        }

        @Override
        public void onPurchaseError() {
            Log.d("wyl", "购买商品回调接口 onPurchaseError");

        }

    }

    /**
     * 处理查询的结果
     * @param size 大于0 表示查询到的数量 -1表示查询失败
     */
    private void handleQueryResult(int size) {
        Log.d("wyl", "获取有效订阅的数量：" + size);
        PayStatusUtil.savePaySubStatus(size > 0 ? true : false);

        if (PayStatusUtil.isSubAvailable()) {//取消弹出弹窗
            EventBus.getDefault().post(new EventModel(Constant.Event.DISS_DIALOG));
        } else {//开始订阅
            if (size == 0) {
                googleBillingUtil.purchaseSubs(this, SUB_ID);
            }
        }

    }

    /**
     * 初始化谷歌内购
     */
    private void initGoogleBilling() {
        GoogleBillingUtil.cleanListener();
        googleBillingUtil = GoogleBillingUtil.getInstance()
                .setOnPurchaseFinishedListener(mOnPurchaseFinishedListener)
                .setOnQueryFinishedListener(mOnQueryFinishedListener)
                .setOnStartSetupFinishedListener(mOnStartSetupFinishedListener)
                .build();
    }



    @Subscribe
    public void onSubEvent(EventModel eventModel) {
        if (eventModel == null) {
            return;

        }
        if (eventModel.code == Constant.Event.QUERY_SUB) {
            if (googleBillingUtil != null && googleBillingUtil.isReady()) {
                int size = googleBillingUtil.getPurchasesSizeSubs();
                handleQueryResult(size);
            } else {//走正常的流程
                initGoogleBilling();
            }
        } else if (eventModel.code == Constant.Event.QUERY_SUB_AND_BUY) {//先查询订阅是否有效，无效的话开始订阅，有效的话取消弹窗
            if (googleBillingUtil != null && googleBillingUtil.isReady()) {
                int size = googleBillingUtil.getPurchasesSizeSubs();
                handleQueryResult(size);
            } else {//走正常的流程

                initGoogleBilling();
            }
        } else if (eventModel.code == Constant.Event.DISS_DIALOG) {
//            if (mAdView != null) {
//                mAdView.setVisibility(View.GONE);
//            }
        } else if (eventModel.code == Constant.Event.SHOW_DIALOG) {
//            if (mAdView != null) {
//                mAdView.setVisibility(View.VISIBLE);
//            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

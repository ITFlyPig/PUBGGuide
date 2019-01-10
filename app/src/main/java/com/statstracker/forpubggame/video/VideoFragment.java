package com.statstracker.forpubggame.video;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.statstracker.forpubggame.R;
import com.statstracker.forpubggame.home.SimpleCardFragment;
import com.statstracker.forpubggame.home.TabEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


@SuppressLint("ValidFragment")
public class VideoFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.tablayout)
    CommonTabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    Unbinder unbinder;
    private InterstitialAd mInterstitialAd;
    private String[] mTitles = new String[]{"PC视频", "手游视频", "搞笑视频"};
    private List<Fragment> mFragments;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private MyPagerAdapter mAdapter;


    public static VideoFragment getInstance() {
        VideoFragment sf = new VideoFragment();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mFragments = new ArrayList<>();

        for (int i = 0; i < mTitles.length; i++) {
            if (i == 0) {
                mFragments.add(VideoListFragment.getInstance());
            } else if (i == 1) {
                mFragments.add(VideoMobileListFragment.getInstance());
            } else if (i == 2) {
                mFragments.add(VideoFunnyListFragment.getInstance());
            }
            mTabEntities.add(new VideoTabEntity(mTitles[i]));
        }

        mAdapter = new MyPagerAdapter(getChildFragmentManager());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video, null);
        unbinder = ButterKnife.bind(this, v);
        viewpager.setAdapter(mAdapter);
        tablayout.setTabData(mTabEntities);
        tablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tablayout.setCurrentTab(i);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.ADUnitID_Screen));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadScreenAd();
            }
        }, 5000);


        return v;
    }


    /**
     * 加载插屏页广告
     */
    private void loadScreenAd() {
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialAd.show();
            }
        });
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            Log.d("wyl", "MyPagerAdapter size:" + mFragments.size());
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


    /**
     * Tab标题
     */
    private static class VideoTabEntity implements CustomTabEntity{
        private String mTitle;

        private VideoTabEntity() {
        }

        public VideoTabEntity(String mTitle) {
            this.mTitle = mTitle;
        }

        @Override
        public String getTabTitle() {
            return mTitle;
        }

        @Override
        public int getTabSelectedIcon() {
            return 0;
        }

        @Override
        public int getTabUnselectedIcon() {
            return 0;
        }
    }
}
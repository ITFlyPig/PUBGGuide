package com.igameguide.pubg.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.igameguide.pubg.R;
import com.igameguide.pubg.detail.bean.Paiwei;
import com.igameguide.pubg.detail.bean.SeasonDetail;
import com.igameguide.pubg.util.ConstantValue;
import com.igameguide.pubg.util.ToastUtil;
import com.igameguide.pubg.util.defaulthelper.CommonActivityViewHelper;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class DetailActivity extends AppCompatActivity implements DetailContract.View, View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_region_middle_title)
    TextView tvRegionMiddleTitle;
    @BindView(R.id.iv_region_right)
    ImageView ivRegionRight;
    @BindView(R.id.tv_mode_middle_title)
    TextView tvModeMiddleTitle;
    @BindView(R.id.iv_mode_right)
    ImageView ivModeRight;
    @BindView(R.id.wins)
    TextView wins;
    @BindView(R.id.wins_lv)
    TextView winsLv;
    @BindView(R.id.top10)
    TextView top10;
    @BindView(R.id.maxkill)
    TextView maxkill;
    @BindView(R.id.longestKill)
    TextView longestKill;
    @BindView(R.id.headshotKills)
    TextView headshotKills;
    @BindView(R.id.timeSurvivedAvg)
    TextView timeSurvivedAvg;
    @BindView(R.id.kd)
    TextView kd;
    @BindView(R.id.rl_default)
    RelativeLayout rlDefault;
    @BindView(R.id.rl_region)
    RelativeLayout rlRegion;
    @BindView(R.id.rl_mode)
    RelativeLayout rlMode;
    @BindView(R.id.picker)
    NumberPickerView picker;
    @BindView(R.id.rl_done)
    RelativeLayout rlDone;
    @BindView(R.id.ll_pick)
    LinearLayout llPick;
    @BindView(R.id.iv_left_icon)
    ImageView ivLeftIcon;
    @BindView(R.id.titletext)
    TextView titletext;
    @BindView(R.id.titlebar)
    RelativeLayout titlebar;


    private DetailContract.Presenter mPresenter;
    private CommonActivityViewHelper mCommonHelper;
    private String mRegion;
    private String mPlayerName;
    private String mMode;
    private String[] mRegionArray = new String[]{"pc-as", "pc-eu", "pc-jp", "pc-kakao", "pc-krjp", "pc-na", "pc-oc"
            , "pc-ru", "pc-sa", "pc-sea", "pc-tournament"};
    private String[] mRegionNamesArray = new String[]{"Asia", "Europe", "Japan", "Kakao", "Korea", "North America", "Oceania"
            , "Russia", "South and Central America", "South East Asia", "Tournaments"};
    private String[] mModeArray = new String[]{"solo", "squad", "duo"};
    private String[] mModeNameArray = new String[]{"单排", "双排", "多排"};
    private int mSelectRegionIndex = -1;
    private int mSelectModeIndex = -1;
    private boolean isRegion = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);


        mPlayerName = getIntent().getStringExtra(ConstantValue.IntentKey.PLAYER_NAME);

        mCommonHelper = new CommonActivityViewHelper(rlDefault);


        mPresenter = new DetailPresenter(this);
        rlRegion.setOnClickListener(this);
        rlMode.setOnClickListener(this);
        rlDone.setOnClickListener(this);
        llPick.setOnClickListener(this);
        ivLeftIcon.setOnClickListener(this);
        titletext.setOnClickListener(this);
    }


    /**
     * 加载数据
     */
    private void load() {
//        mRegion = mRegionArray[0];
//        mPlayerName = "White-Mickey";
        mPresenter.loadPlayerData(mRegion, mPlayerName, null);
    }

    @Override
    public void showLoading() {
        mCommonHelper.showLoading();

    }

    @Override
    public void dismissLoading() {
        mCommonHelper.hiddenLoading();

    }

    @Override
    public void onLoadSucess(SeasonDetail seasonDetail) {
        dismissLoading();
        ToastUtil.showToas("网络请求成功");
        if (seasonDetail != null) {
            if (TextUtils.equals(mModeArray[0], mMode)) {
                updateUi(seasonDetail.solo);
            } else if (TextUtils.equals(mModeArray[1], mMode)) {
                updateUi(seasonDetail.squad);
            } else if (TextUtils.equals(mModeArray[2], mMode)) {
                updateUi(seasonDetail.duo);
            }

        }
    }


    private void updateUi(Paiwei paiwei) {
        if (paiwei == null) {//没有查询结果的情况
            wins.setText("0");
            winsLv.setText("0%");

            top10.setText("0");
            maxkill.setText("0");
            longestKill.setText("0 minute");
            headshotKills.setText("0%");
            timeSurvivedAvg.setText("0 meter");
            kd.setText("0");
        } else {
            wins.setText(String.valueOf((int) paiwei.wins));

            float winsl = 0;
            DecimalFormat df = new DecimalFormat("#.00");
            if (paiwei.roundsPlayed != 0) {
                winsl = paiwei.wins / paiwei.roundsPlayed;
            }
            if (winsl > 0 && winsl < 1) {
                winsl *= 100;
            }

            winsLv.setText(df.format(winsl) + "%");

            top10.setText(String.valueOf(paiwei.top10s));
            maxkill.setText(String.valueOf(paiwei.maxKillStreaks));
            longestKill.setText(df.format(paiwei.longestKill) + " minute");

            float killsl = 0;
            if (paiwei.kills != 0) {
                killsl = paiwei.headshotKills / paiwei.kills;
            }

            if (killsl > 0 && killsl < 1) {
                killsl *= 100;
            }

            headshotKills.setText(df.format(killsl) + "%");


            float survivedAvg = 0;
            if (paiwei.roundsPlayed != 0) {
                survivedAvg = paiwei.timeSurvived / (60 * paiwei.roundsPlayed);

            }
            timeSurvivedAvg.setText(df.format(survivedAvg) + " meter");

            String kdStr = "";
            if (paiwei.roundsPlayed == 0) {
                kdStr = "Perfect";
            } else {
                kdStr = String.valueOf(paiwei.kills / (paiwei.roundsPlayed - paiwei.wins));
            }
            kd.setText(kdStr);
        }


    }


    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void onLoadStart() {
        showLoading();

    }

    @Override
    public void onLoadFail() {

        Thread.dumpStack();
        dismissLoading();
        ToastUtil.showToas("网络请求错误");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_region:
                isRegion = true;
                llPick.setVisibility(View.VISIBLE);
                picker.refreshByNewDisplayedValues(mRegionNamesArray);
                break;
            case R.id.rl_mode:
                isRegion = false;
                llPick.setVisibility(View.VISIBLE);
                picker.refreshByNewDisplayedValues(mModeNameArray);
                break;
            case R.id.rl_done:
                llPick.setVisibility(View.INVISIBLE);
                if (isRegion) {
                    mSelectRegionIndex = picker.getPickedIndexRelativeToRaw();
                    if (mSelectRegionIndex >= 0 && mSelectRegionIndex < mRegionArray.length) {
                        mRegion = mRegionArray[mSelectRegionIndex];
                    }

                } else {
                    mSelectModeIndex = picker.getPickedIndexRelativeToRaw();
                    if (mSelectModeIndex >= 0 && mSelectModeIndex < mModeArray.length) {
                        mMode = mModeArray[mSelectModeIndex];
                    }

                }

                if (mSelectRegionIndex >= 0 && mSelectModeIndex >= 0) {
                    load();
                }

                break;
            case R.id.iv_left_icon:
            case R.id.titletext:
                finish();
                break;
        }
    }

}

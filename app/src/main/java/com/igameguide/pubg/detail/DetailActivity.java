package com.igameguide.pubg.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.igameguide.pubg.R;
import com.igameguide.pubg.detail.bean.Paiwei;
import com.igameguide.pubg.detail.bean.SeasonDetail;
import com.igameguide.pubg.util.ToastUtil;
import com.igameguide.pubg.util.defaulthelper.CommonActivityViewHelper;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {
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


    private DetailContract.Presenter mPresenter;
    private CommonActivityViewHelper mCommonHelper;
    private String mRegion;
    private String mPlayerName;
    private String[] mRegionArray = new String[]{"pc-as", "pc-eu", "pc-jp", "pc-kakao", "pc-krjp", "pc-na", "pc-oc"
            , "pc-ru", "pc-sa", "pc-sea", "pc-tournament"};
    private String[] mRegionNamesArray = new String[]{"Asia", "Europe", "Japan", "Kakao", "Korea", "North America", "Oceania"
            , "Russia", "South and Central America", "South East Asia", "Tournaments"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);


        mCommonHelper = new CommonActivityViewHelper(rlDefault);


        mPresenter = new DetailPresenter(this);

        load();
    }


    /**
     * 加载数据
     */
    private void load() {
        mRegion = mRegionArray[0];
        mPlayerName = "White-Mickey";
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
            updateUi(seasonDetail.solo);
        }
    }


    private void updateUi(Paiwei paiwei) {
        if (paiwei == null) {
            return;

        }

        wins.setText(String.valueOf((int)paiwei.wins));

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
}

package com.igameguide.pubg.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.igameguide.pubg.R;
import com.igameguide.pubg.detail.DetailActivity;
import com.igameguide.pubg.util.ConstantValue;
import com.igameguide.pubg.util.ToastUtil;


@SuppressLint("ValidFragment")
public class StandingsFragment extends Fragment implements View.OnClickListener {
    TextView tvQuery;
    EditText etInput;
    AdView mAdView;
    private InterstitialAd mInterstitialAd;


    public static StandingsFragment getInstance() {
        StandingsFragment sf = new StandingsFragment();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_standings, null);
        tvQuery = v.findViewById(R.id.tv_query);
        tvQuery.setOnClickListener(this);
        etInput = v.findViewById(R.id.et_input_role);
        mAdView = v.findViewById(R.id.adView);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_query:
                String playerName = etInput.getText().toString();
                if (TextUtils.isEmpty(playerName)) {
                    ToastUtil.showToas(getResources().getString(R.string.input_not_empty));
                    return;
                }

                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(ConstantValue.IntentKey.PLAYER_NAME, playerName);
                startActivity(intent);

                loadScreenAd();
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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
}
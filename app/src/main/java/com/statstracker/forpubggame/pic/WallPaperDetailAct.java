package com.statstracker.forpubggame.pic;

import android.Manifest;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.statstracker.forpubggame.R;
import com.statstracker.forpubggame.base.GlideApp;
import com.statstracker.forpubggame.home.EventModel;
import com.statstracker.forpubggame.util.ConfirmDialog;
import com.statstracker.forpubggame.util.Constant;
import com.statstracker.forpubggame.util.PayStatusUtil;
import com.statstracker.forpubggame.util.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.UnityServices;
import com.unity3d.services.monetization.IUnityMonetizationListener;
import com.unity3d.services.monetization.UnityMonetization;
import com.unity3d.services.monetization.placementcontent.ads.IShowAdListener;
import com.unity3d.services.monetization.placementcontent.ads.ShowAdPlacementContent;
import com.unity3d.services.monetization.placementcontent.core.PlacementContent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.leefeng.promptlibrary.PromptDialog;

public class WallPaperDetailAct extends AppCompatActivity implements View.OnClickListener, IShowAdListener {

    @BindView(R.id.iv_content)
    ImageView ivContent;
    @BindView(R.id.iv_btn_download)
    ImageView ivBtnDownload;
    @BindView(R.id.spin_kit)
    SpinKitView spinKit;
    @BindView(R.id.tv_download_fail)
    TextView tvDownloadFail;

    private String mUrl;
    private String mTitle;
    private PromptDialog mPromptDialog;
    private String unityGameID = "2986658";
    private String rewardedPlacementId = "rewardedVideo";
    private boolean isOk = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_detail);
        ButterKnife.bind(this);

        ivBtnDownload.setOnClickListener(this);


        mUrl = getIntent().getStringExtra("url");
        mTitle = getIntent().getStringExtra("title");

        Sprite doubleBounce = new DoubleBounce();
        spinKit.setIndeterminateDrawable(doubleBounce);


        GlideApp.with(this)
                .load(mUrl)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        ToastUtil.showToas("图片加载失败");
                        spinKit.clearAnimation();
                        tvDownloadFail.setVisibility(View.VISIBLE);
                        doubleBounce.stop();
                        spinKit.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        doubleBounce.stop();
                        spinKit.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(ivContent);


        UnityMonetization.initialize(this, unityGameID, new IUnityMonetizationListener() {
            @Override
            public void onPlacementContentReady(String s, PlacementContent placementContent) {

            }

            @Override
            public void onPlacementContentStateChange(String s, PlacementContent placementContent, UnityMonetization.PlacementContentState placementContentState, UnityMonetization.PlacementContentState placementContentState1) {

            }

            @Override
            public void onUnityServicesError(UnityServices.UnityServicesError unityServicesError, String s) {

            }
        }, true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_btn_download:

                if (PayStatusUtil.isSubAvailable()) {
                    isOk = true;
                }

                if (!isOk) {
                    showDialog();
                } else {
                    final RxPermissions rxPermissions = new RxPermissions(this);
                    rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            .subscribe(granted -> {
                                if (granted) {
                                    //开始下载
                                    downloadPic();
                                } else {
                                    ToastUtil.showToas("存储权限被禁止，无法保存");
                                }

                            });
                }
                break;
        }
    }

    private void downloadPic() {
        String cameraPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
        OkDownload.getInstance().setFolder(cameraPath + "/PUBG Guide/");
        OkDownload.getInstance().getThreadPool().setCorePoolSize(1);
        OkDownload.request(mUrl, OkGo.<File>get(mUrl))
                .save()
                .register(new DownloadListener(mUrl) {
                    @Override
                    public void onStart(Progress progress) {

                    }

                    @Override
                    public void onProgress(Progress progress) {

                    }

                    @Override
                    public void onError(Progress progress) {
                        ToastUtil.showToas("图片下载失败");

                    }

                    @Override
                    public void onFinish(File file, Progress progress) {
                        ToastUtil.showToas("图片下载成功，路径为：" + file.getAbsolutePath());

                    }

                    @Override
                    public void onRemove(Progress progress) {

                    }
                })
                .start();
    }

    /**
     * 显示弹窗
     */
    private void showDialog() {
        if (mPromptDialog == null) {
            mPromptDialog = new PromptDialog(this);
        }
        ConfirmDialog.show(this, () -> {
            //观看广告

            viewAd();
        }, () -> {
            //订阅
            EventBus.getDefault().post(new EventModel(Constant.Event.QUERY_SUB_AND_BUY));

        });
    }

    /**
     * 观看广告视频
     */
    private void viewAd() {
        if (UnityMonetization.isReady (rewardedPlacementId)) {
            PlacementContent pc = UnityMonetization.getPlacementContent (rewardedPlacementId);
            if (pc.getType ().equalsIgnoreCase ("SHOW_AD")) {
                ShowAdPlacementContent p = (ShowAdPlacementContent) pc;
                p.show(this, this);
            }
        } else {
            Log.e("tt","This Placement is not ready!");
        }
    }

    @Override
    public void onAdFinished(String s, UnityAds.FinishState finishState) {
        isOk = true;

    }

    @Override
    public void onAdStarted(String s) {

    }
}

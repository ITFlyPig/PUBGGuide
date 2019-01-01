package com.igameguide.pubg.pic;

import android.Manifest;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.igameguide.pubg.R;
import com.igameguide.pubg.base.GlideApp;
import com.igameguide.pubg.util.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WallPaperDetailAct extends AppCompatActivity implements View.OnClickListener {

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
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_btn_download:
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
}

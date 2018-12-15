package com.igameguide.pubg.video;

import com.igameguide.pubg.video.bean.VideoItemBean;

import java.util.List;

public class VideoListPresenter implements VideoListContract.Presenter {

    private VideoListContract.View mView;
    private VideoListModle mModle;


    @Override
    public List<VideoItemBean> getData(String language) {
        return mModle.getDataByLanguage(language);
    }

    @Override
    public void start() {

    }

    public VideoListPresenter(VideoListContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
        mModle = new VideoListModle();

    }
}

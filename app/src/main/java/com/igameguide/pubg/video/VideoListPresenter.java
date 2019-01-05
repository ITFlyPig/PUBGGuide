package com.igameguide.pubg.video;

import com.igameguide.pubg.video.bean.VideoItemBean;

import java.util.List;

public class VideoListPresenter implements VideoListContract.Presenter {

    private VideoListContract.View mView;
    private VideoListModle mModle;



    @Override
    public void start() {

    }

    public VideoListPresenter(VideoListContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
        mModle = new VideoListModle();

    }

    @Override
    public List<VideoItemBean> getPcData(String language) {
        return mModle.getPcDataByLanguage(language);
    }

    @Override
    public List<VideoItemBean> getModileData(String language) {
        return mModle.getMobileDataByLanguage(language);
    }

    @Override
    public List<VideoItemBean> getFunnyData(String language) {
        return mModle.getFunnyDataByLanguage(language);
    }
}

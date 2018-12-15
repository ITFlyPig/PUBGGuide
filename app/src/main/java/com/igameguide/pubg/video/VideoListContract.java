package com.igameguide.pubg.video;

import com.igameguide.pubg.base.BasePresenter;
import com.igameguide.pubg.base.BaseView;
import com.igameguide.pubg.video.bean.VideoItemBean;

import java.util.List;

public class VideoListContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter<View> {
        List<VideoItemBean> getData(String language);
    }


}

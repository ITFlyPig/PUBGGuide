package com.statstracker.forpubggame.video;

import com.statstracker.forpubggame.base.BasePresenter;
import com.statstracker.forpubggame.base.BaseView;
import com.statstracker.forpubggame.video.bean.VideoItemBean;

import java.util.List;

public class VideoListContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter<View> {
        List<VideoItemBean> getPcData(String language);
        List<VideoItemBean> getModileData(String language);
        List<VideoItemBean> getFunnyData(String language);
    }


}

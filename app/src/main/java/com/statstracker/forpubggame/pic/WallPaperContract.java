package com.statstracker.forpubggame.pic;

import com.statstracker.forpubggame.base.BasePresenter;
import com.statstracker.forpubggame.base.BaseView;
import com.statstracker.forpubggame.pic.bean.WallPaperBean;
import com.statstracker.forpubggame.video.bean.VideoItemBean;

import java.util.List;

public class WallPaperContract {

    public interface View extends BaseView<Presenter> {

    }

    public interface Presenter extends BasePresenter<View> {
        List<WallPaperBean> getWallPapers();
    }


}

package com.igameguide.pubg.pic;

import com.igameguide.pubg.base.BasePresenter;
import com.igameguide.pubg.base.BaseView;
import com.igameguide.pubg.pic.bean.WallPaperBean;
import com.igameguide.pubg.video.bean.VideoItemBean;

import java.util.List;

public class WallPaperContract {

    public interface View extends BaseView<Presenter> {

    }

    public interface Presenter extends BasePresenter<View> {
        List<WallPaperBean> getWallPapers();
    }


}

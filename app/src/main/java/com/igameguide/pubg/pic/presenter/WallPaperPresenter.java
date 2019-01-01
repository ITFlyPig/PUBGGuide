package com.igameguide.pubg.pic.presenter;

import com.igameguide.pubg.pic.WallPaperContract;
import com.igameguide.pubg.pic.bean.WallPaperBean;
import com.igameguide.pubg.pic.model.WallPaperModle;
import com.igameguide.pubg.video.VideoListContract;
import com.igameguide.pubg.video.VideoListModle;
import com.igameguide.pubg.video.bean.VideoItemBean;

import java.util.List;

public class WallPaperPresenter implements WallPaperContract.Presenter {

    private WallPaperContract.View mView;
    private WallPaperModle mModle;


    @Override
    public void start() {

    }

    public WallPaperPresenter(WallPaperContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
        mModle = new WallPaperModle();

    }

    @Override
    public List<WallPaperBean> getWallPapers() {
        return mModle.getAllWallPaper();
    }
}

package com.statstracker.forpubggame.pic.presenter;

import com.statstracker.forpubggame.pic.WallPaperContract;
import com.statstracker.forpubggame.pic.bean.WallPaperBean;
import com.statstracker.forpubggame.pic.model.WallPaperModle;
import com.statstracker.forpubggame.video.VideoListContract;
import com.statstracker.forpubggame.video.VideoListModle;
import com.statstracker.forpubggame.video.bean.VideoItemBean;

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

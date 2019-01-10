package com.statstracker.forpubggame.pic.model;

import com.statstracker.forpubggame.base.PubgApplication;
import com.statstracker.forpubggame.pic.bean.WallPaperBean;
import com.statstracker.forpubggame.util.DBHepler;

import java.util.List;

public class WallPaperModle {

    /**
     * 获取全部的壁纸数据
     * @return
     */
    public List<WallPaperBean> getAllWallPaper() {
        DBHepler.getInstance().OpenDatabase(PubgApplication.getInstance());
        return DBHepler.getInstance().getAllWallPapers();
    }
}

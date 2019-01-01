package com.igameguide.pubg.pic.model;

import com.igameguide.pubg.base.PubgApplication;
import com.igameguide.pubg.pic.bean.WallPaperBean;
import com.igameguide.pubg.util.DBHepler;

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

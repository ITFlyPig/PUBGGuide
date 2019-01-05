package com.igameguide.pubg.video;

import android.text.TextUtils;

import com.igameguide.pubg.base.PubgApplication;
import com.igameguide.pubg.util.DBHepler;
import com.igameguide.pubg.video.bean.VideoItemBean;

import java.util.List;

public class VideoListModle {

    /**
     * 查询PC视频
     * @param language
     * @return
     */
    public List<VideoItemBean> getPcDataByLanguage(String language) {
        if (TextUtils.isEmpty(language)) {
            return null;
        }
        DBHepler.getInstance().OpenDatabase(PubgApplication.getInstance());
        return DBHepler.getInstance().queryVideoBy(language, "PC");
    }
    /**
     * 查询手游视频
     * @param language
     * @return
     */
    public List<VideoItemBean> getMobileDataByLanguage(String language) {
        if (TextUtils.isEmpty(language)) {
            return null;
        }
        DBHepler.getInstance().OpenDatabase(PubgApplication.getInstance());
        return DBHepler.getInstance().queryVideoBy(language, "Mobile");
    }
    /**
     * 查询搞笑视频
     * @param language
     * @return
     */
    public List<VideoItemBean> getFunnyDataByLanguage(String language) {
        if (TextUtils.isEmpty(language)) {
            return null;
        }
        DBHepler.getInstance().OpenDatabase(PubgApplication.getInstance());
        return DBHepler.getInstance().queryVideoBy(language, "Funny");
    }
}

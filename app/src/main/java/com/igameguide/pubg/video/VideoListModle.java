package com.igameguide.pubg.video;

import android.text.TextUtils;

import com.igameguide.pubg.base.PubgApplication;
import com.igameguide.pubg.util.DBHepler;
import com.igameguide.pubg.video.bean.VideoItemBean;

import java.util.List;

public class VideoListModle {

    /**
     * 据语言查询数据
     * @param language
     * @return
     */
    public List<VideoItemBean> getDataByLanguage(String language) {
        if (TextUtils.isEmpty(language)) {
            return null;
        }
        DBHepler.getInstance().OpenDatabase(PubgApplication.getInstance());
        return DBHepler.getInstance().queryByLanguage(language);
    }
}

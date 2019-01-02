package com.igameguide.pubg.weapon;

import android.text.TextUtils;

import com.igameguide.pubg.base.PubgApplication;
import com.igameguide.pubg.util.DBHepler;
import com.igameguide.pubg.video.bean.VideoItemBean;
import com.igameguide.pubg.weapon.bean.WeaponBean;

import java.util.List;

public class WeaponListModle {

    /**
     * 据语言查询数据
     * @param language
     * @return
     */
    public List<WeaponBean> getDataByLanguage(String language) {
        if (TextUtils.isEmpty(language)) {
            return null;
        }
        DBHepler.getInstance().OpenDatabase(PubgApplication.getInstance());
        return DBHepler.getInstance().queryWeaponByLanguage(language);
    }
}

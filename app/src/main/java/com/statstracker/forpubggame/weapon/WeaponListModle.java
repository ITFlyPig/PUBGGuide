package com.statstracker.forpubggame.weapon;

import android.text.TextUtils;

import com.statstracker.forpubggame.base.PubgApplication;
import com.statstracker.forpubggame.util.DBHepler;
import com.statstracker.forpubggame.video.bean.VideoItemBean;
import com.statstracker.forpubggame.weapon.bean.WeaponBean;

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

    /**
     * 据语言查询投掷物
     * @param language
     * @return
     */
    public List<WeaponBean> getThrowWeaponDataByLanguage(String language) {
        if (TextUtils.isEmpty(language)) {
            return null;
        }
        DBHepler.getInstance().OpenDatabase(PubgApplication.getInstance());
        return DBHepler.getInstance().queryThrowWeaponByLanguage(language);
    }

    /**
     * 据语言查询近战武器
     * @param language
     * @return
     */
    public List<WeaponBean> getMeleeWeaponDataByLanguage(String language) {
        if (TextUtils.isEmpty(language)) {
            return null;
        }
        DBHepler.getInstance().OpenDatabase(PubgApplication.getInstance());
        return DBHepler.getInstance().queryMeleeByLanguage(language);
    }
}

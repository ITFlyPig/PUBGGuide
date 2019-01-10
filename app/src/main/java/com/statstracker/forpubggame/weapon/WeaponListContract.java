package com.statstracker.forpubggame.weapon;

import com.statstracker.forpubggame.base.BasePresenter;
import com.statstracker.forpubggame.base.BaseView;
import com.statstracker.forpubggame.video.bean.VideoItemBean;
import com.statstracker.forpubggame.weapon.bean.WeaponBean;

import java.util.List;

public class WeaponListContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter<View> {
        List<WeaponBean> getData(String language);
        List<WeaponBean> getThrowWeaponData(String language);
        List<WeaponBean> getMeleeData(String language);
    }


}

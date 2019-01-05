package com.igameguide.pubg.weapon;

import com.igameguide.pubg.base.BasePresenter;
import com.igameguide.pubg.base.BaseView;
import com.igameguide.pubg.video.bean.VideoItemBean;
import com.igameguide.pubg.weapon.bean.WeaponBean;

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

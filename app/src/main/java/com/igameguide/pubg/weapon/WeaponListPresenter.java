package com.igameguide.pubg.weapon;

import com.igameguide.pubg.weapon.bean.WeaponBean;

import java.util.List;

public class WeaponListPresenter implements WeaponListContract.Presenter {

    private WeaponListContract.View mView;
    private WeaponListModle mModle;


    @Override
    public List<WeaponBean> getData(String language) {
        return mModle.getDataByLanguage(language);
    }

    @Override
    public List<WeaponBean> getThrowWeaponData(String language) {
        return mModle.getThrowWeaponDataByLanguage(language);
    }

    @Override
    public List<WeaponBean> getMeleeData(String language) {
        return mModle.getMeleeWeaponDataByLanguage(language);
    }

    @Override
    public void start() {

    }

    public WeaponListPresenter(WeaponListContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
        mModle = new WeaponListModle();

    }
}

package com.statstracker.forpubggame.home;

import com.statstracker.forpubggame.base.BasePresenter;
import com.statstracker.forpubggame.base.BaseView;

public class MainContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter<View> {
        /**
         * 加载数据
         * @param platform
         * @param playerName
         */
        void loadPlayerData(String platform, String playerName);

    }
}

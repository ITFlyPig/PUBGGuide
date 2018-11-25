package com.igameguide.pubg.home;

import com.igameguide.pubg.base.BasePresenter;
import com.igameguide.pubg.base.BaseView;

public class MainContract {
    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter<View>{
        /**
         * 加载数据
         * @param platform
         * @param playerName
         */
        void loadPlayerData(String platform, String playerName);

    }
}

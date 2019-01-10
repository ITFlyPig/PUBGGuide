package com.statstracker.forpubggame.detail;

import com.statstracker.forpubggame.base.BasePresenter;
import com.statstracker.forpubggame.base.BaseView;
import com.statstracker.forpubggame.detail.bean.SeasonDetail;

public class DetailContract {

    interface View extends BaseView<Presenter> {

        void showLoading();
        void dismissLoading();
        void onLoadSucess(SeasonDetail seasonDetail);
        void onLoadFail();

    }

    interface Presenter extends BasePresenter<View> {
        /**
         * 查询战绩
         * @param region 服务器
         * @param playerName 角色ID
         * @param type 类型（单排、双排等）
         */
        void loadPlayerData(String region, String playerName, String type);

    }
}

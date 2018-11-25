package com.igameguide.pubg.home;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;




    @Override
    public void loadPlayerData(String platform, String playerName) {

    }

    @Override
    public void start() {

    }

    /**
     * 经典啊
     * @param view
     */
    public MainPresenter(MainContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
    }
}

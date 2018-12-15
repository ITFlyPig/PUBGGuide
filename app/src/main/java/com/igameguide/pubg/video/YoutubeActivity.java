package com.igameguide.pubg.video;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.igameguide.pubg.R;
import com.igameguide.pubg.video.player.YoutubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class YoutubeActivity extends AppCompatActivity {
    public static final String TAG=YoutubeActivity.class.getSimpleName();
    //一个页面可以播放多个视频，将所有的播放控件收集到这里进行维护，主要是控制离开页面时候的暂停
    private List<YoutubePlayerView> playerViewList;
    private View mVideoProgressView;
    private View mCustomView;//全屏显示的View
    private LinearLayout ll_player_container;
    private int mOriginalSystemUiVisibility;
    private int mOriginalOrientation;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private String videoUrl,title;
    private LinearLayout nativeAdContainer;
    private LinearLayout adView;
    //facebook
    private LinearLayout rll_title;
    private TextView title_tv;
    private ProgressBar pbLarge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        videoUrl = getIntent().getExtras().getString("url");
        title = getIntent().getExtras().getString("title");
        title_tv = (TextView)findViewById(R.id.tv_title);
        pbLarge = (ProgressBar)findViewById(R.id.pbLarge);
        pbLarge.setVisibility(View.VISIBLE);
        title_tv.setText(title);
        init();

    }

    private void init(){
        rll_title=(LinearLayout)findViewById(R.id.rll_title);
        String videoID = YoutubePlayerView.parseIDfromVideoUrl(videoUrl);
        Log.i("Alex","视频的ID是=="+videoID);
        View youtubeView = LayoutInflater.from(this).inflate(R.layout.layout_youtube_player, null);
        YoutubePlayerView youtubePlayerView = (YoutubePlayerView) youtubeView.findViewById(R.id.youtubePlayerView);
        youtubePlayerView.setAutoPlayerHeight(this);
        youtubePlayerView.initialize(videoID, new YoutubePlayerCallBack(youtubePlayerView), mWebChromeClient);
        if(playerViewList == null){
            playerViewList = new ArrayList<>();
        }
        ll_player_container = (LinearLayout) findViewById(R.id.ll_player_container);
        ll_player_container.addView(youtubeView);
        playerViewList.add(youtubePlayerView);


    }

    private class YoutubePlayerCallBack implements YoutubePlayerView.YouTubeListener {

        private YoutubePlayerView mYoutubeView;

        YoutubePlayerCallBack(YoutubePlayerView view){
            this.mYoutubeView = view;
            Log.i(TAG, "YoutubePlayerCallBack: ");
        }

        @Override
        public void onReady() {
            Log.i(TAG, "onReady: ");
            pbLarge.setVisibility(View.GONE);
        }

        @Override
        public void onStateChange(YoutubePlayerView.STATE state) {
            Log.i(TAG, "YoutubePlayerCallBack: "+"-----"+"onStateChange");
            if(state == YoutubePlayerView.STATE.PLAYING && mYoutubeView!=null){
                Log.i(TAG, "onStateChange: ");
                pbLarge.setVisibility(View.GONE);
                if(playerViewList!=null){
                    for(YoutubePlayerView v : playerViewList){
                        if (v != null && v != mYoutubeView && (v.getPlayerState() == YoutubePlayerView.STATE.PLAYING ||
                                v.getPlayerState() == YoutubePlayerView.STATE.PAUSED)) {
                            v.stop();
                        }
                    }
                }
            }
        }

        @Override
        public void onPlaybackQualityChange(String arg) {
            Log.i(TAG, "onPlaybackQualityChange: "+arg);
        }

        @Override
        public void onPlaybackRateChange(String arg) {
            Log.i(TAG, "onPlaybackRateChange: "+arg);
        }

        @Override
        public void onError(String arg) {
            Log.i(TAG, "onError: "+arg);
        }

        @Override
        public void onApiChange(String arg) {
            Log.i(TAG, "onApiChange: "+arg);
        }

        @Override
        public void onCurrentSecond(double second) {
            Log.i(TAG, "onCurrentSecond: "+second);
        }

        @Override
        public void onDuration(double duration) {
            Log.i(TAG, "onDuration: "+duration);
        }

        @Override
        public void logs(String log) {
            Log.i(TAG, "onDuration: "+log);
        }
    }



    /**
     * 用于全屏显示的代码
     */
    private WebChromeClient mWebChromeClient = new WebChromeClient(){

        @Override
        public View getVideoLoadingProgressView() {
            if (mVideoProgressView == null) {
                LayoutInflater inflater = LayoutInflater.from(YoutubeActivity.this);
                mVideoProgressView = inflater.inflate(R.layout.video_layout_loading, null);
            }
            return mVideoProgressView;
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                onHideCustomView();
                return;
            }

            // 1. Stash the current state
            mCustomView = view;
            mOriginalSystemUiVisibility =getWindow().getDecorView().getSystemUiVisibility();
            mOriginalOrientation =getRequestedOrientation();
            Log.i("Alex","原来的屏幕方向是"+mOriginalOrientation);
            // 2. Stash the custom view callback
            mCustomViewCallback = callback;

            // 3. Add the custom view to the view hierarchy
            FrameLayout decor = (FrameLayout)getWindow().getDecorView();
            decor.addView(mCustomView, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
//            if(mVideoFullScreenBack!=null){
//                mVideoFullScreenBack.setVisibility(View.VISIBLE);
//            }
            rll_title.setVisibility(View.GONE);
            // 4. Change the state of the window
             getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_IMMERSIVE);
           setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            mOriginalOrientation =getRequestedOrientation();
            Log.i("Alex","原来的屏幕方向是--"+mOriginalOrientation);
        }

        @Override
        public void onHideCustomView() {

            // 1. Remove the custom view
            FrameLayout decor = (FrameLayout)getWindow().getDecorView();
            decor.removeView(mCustomView);
            mCustomView = null;


            // 2. Restore the state to it's original form
           getWindow().getDecorView().setSystemUiVisibility(mOriginalSystemUiVisibility);
//            AlertToast("竖屏"+mOriginalOrientation);
            Log.i(TAG, "onHideCustomView: "+mOriginalOrientation);
//           setRequestedOrientation(mOriginalOrientation);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
            rll_title.setVisibility(View.VISIBLE);
            // 3. Call the custom view callback
            if(mCustomViewCallback!=null){
                mCustomViewCallback.onCustomViewHidden();
                mCustomViewCallback = null;
            }

        }
    };

    @Override
    public void onPause() {
        //视频播放器当页面停止的时候所有的视频播放全部暂停
        if(playerViewList!=null){
            for(YoutubePlayerView v : playerViewList){
                if(v.getPlayerState() == YoutubePlayerView.STATE.PLAYING ){
                    v.pause();
                }else if(v.getPlayerState() == YoutubePlayerView.STATE.BUFFERING){
                    v.stop();
                }
            }
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (playerViewList != null) {
            for (YoutubePlayerView v : playerViewList) {
                if (v != null) v.onDestroy();
            }
        }
    }
    public boolean closeFullScreen(){
        if(mCustomView!=null && mCustomViewCallback!=null){
            mWebChromeClient.onHideCustomView();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Log.i("Alex", "进入onBackPressed方法");
        closeFullScreen();
        super.onBackPressed();
    }

    public void onClickBack(View v){
        finish();
    }
}

package com.statstracker.forpubggame.pic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.statstracker.forpubggame.R;
import com.statstracker.forpubggame.pic.adapter.WallPaperAdapter;
import com.statstracker.forpubggame.pic.bean.WallPaperBean;
import com.statstracker.forpubggame.pic.presenter.WallPaperPresenter;
import com.statstracker.forpubggame.video.VideoListFragment;
import com.statstracker.forpubggame.video.YoutubeActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


@SuppressLint("ValidFragment")
public class WallPaperFragment extends Fragment implements WallPaperContract.View {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private WallPaperContract.Presenter mPresenter;
    private WallPaperAdapter mAdapter;

    public static WallPaperFragment getInstance() {
        WallPaperFragment sf = new WallPaperFragment();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new WallPaperPresenter(this);
        mAdapter = new WallPaperAdapter(getContext());
        mAdapter.setOnItemClickListener(onItemClickListener);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video_list, null);
        unbinder = ButterKnife.bind(this, v);
        recyclerView.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        List<WallPaperBean> datas = mPresenter.getWallPapers();
        mAdapter.setmData(datas);


        return v;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void setPresenter(WallPaperContract.Presenter presenter) {

    }

    @Override
    public void onLoadStart() {

    }

    @Override
    public void onLoadFail() {

    }

    private WallPaperAdapter.OnItemClickListener onItemClickListener = new WallPaperAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(WallPaperBean bean) {
            if (bean == null || TextUtils.isEmpty(bean.downloadUrl)) {
                return;
            }
            Intent intent = new Intent(WallPaperFragment.this.getActivity(), WallPaperDetailAct.class);
            intent.putExtra("url", bean.downloadUrl);
            intent.putExtra("title", bean.title);
            startActivity(intent);

        }
    };
}
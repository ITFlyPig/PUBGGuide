package com.igameguide.pubg.video;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igameguide.pubg.R;
import com.igameguide.pubg.video.atapter.VideoListAdapter;
import com.igameguide.pubg.video.bean.VideoItemBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


@SuppressLint("ValidFragment")
public class VideoListFragment extends Fragment implements VideoListContract.View {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private VideoListContract.Presenter mPresenter;
    private VideoListAdapter mAdapter;

    public static VideoListFragment getInstance() {
        VideoListFragment sf = new VideoListFragment();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new VideoListPresenter(this);
        mAdapter = new VideoListAdapter(getContext());
        mAdapter.setOnItemClickListener(onItemClickListener);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video_list, null);
        unbinder = ButterKnife.bind(this, v);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        List<VideoItemBean> datas = mPresenter.getData("en");
        mAdapter.setmData(datas);


        return v;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void setPresenter(VideoListContract.Presenter presenter) {

    }

    @Override
    public void onLoadStart() {

    }

    @Override
    public void onLoadFail() {

    }

    private VideoListAdapter.OnItemClickListener onItemClickListener = new VideoListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(VideoItemBean bean) {
            if (bean == null || TextUtils.isEmpty(bean.link) || TextUtils.isEmpty(bean.link)) {
                return;
            }

            Intent intent=new Intent(VideoListFragment.this.getActivity(),YoutubeActivity.class);
            intent.putExtra("url",bean.link);
            intent.putExtra("title",bean.title);
            startActivity(intent);

        }
    };
}
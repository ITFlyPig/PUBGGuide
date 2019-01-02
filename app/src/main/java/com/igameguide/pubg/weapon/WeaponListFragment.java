package com.igameguide.pubg.weapon;

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
import com.igameguide.pubg.video.VideoListContract;
import com.igameguide.pubg.video.VideoListPresenter;
import com.igameguide.pubg.video.YoutubeActivity;
import com.igameguide.pubg.video.atapter.VideoListAdapter;
import com.igameguide.pubg.video.bean.VideoItemBean;
import com.igameguide.pubg.weapon.atapter.WeaponListAdapter;
import com.igameguide.pubg.weapon.bean.WeaponBean;
import com.wordplat.easydivider.RecyclerViewCornerRadius;
import com.wordplat.easydivider.RecyclerViewGridDivider;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


@SuppressLint("ValidFragment")
public class WeaponListFragment extends Fragment implements WeaponListContract.View {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private WeaponListContract.Presenter mPresenter;
    private WeaponListAdapter mAdapter;

    public static WeaponListFragment getInstance() {
        WeaponListFragment sf = new WeaponListFragment();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new WeaponListPresenter(this);
        mAdapter = new WeaponListAdapter(getContext());
        mAdapter.setOnItemClickListener(onItemClickListener);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video_list, null);
        unbinder = ButterKnife.bind(this, v);
        recyclerView.setAdapter(mAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        RecyclerViewCornerRadius cornerRadius = new RecyclerViewCornerRadius(recyclerView);
        cornerRadius.setCornerRadius(20);
        // 圆角背景必须第一个添加
        recyclerView.addItemDecoration(cornerRadius);

        // 九宫格列表添加分割线
        final int margin = 10;
        final int size = 20;
        RecyclerViewGridDivider recyclerViewGridDivider = new RecyclerViewGridDivider(2, size, size);
        recyclerViewGridDivider.setRowDividerMargin(margin * 2, margin * 2);
        recyclerViewGridDivider.setColDividerMargin(margin, margin);
        recyclerViewGridDivider.setShowLeftDivider(false);
        recyclerViewGridDivider.setShowRightDivider(true);
        recyclerViewGridDivider.setDividerClipToPadding(false);
        recyclerViewGridDivider.setDividerSize(0);
        recyclerViewGridDivider.setDividerColor(0x88000000);
        recyclerViewGridDivider.setBackgroundColor(0xffffffff);

        recyclerView.addItemDecoration(recyclerViewGridDivider);

        recyclerView.setLayoutManager(gridLayoutManager);

        List<WeaponBean> datas = mPresenter.getData("en");
        mAdapter.setmData(datas);


        return v;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void setPresenter(WeaponListContract.Presenter presenter) {

    }

    @Override
    public void onLoadStart() {

    }

    @Override
    public void onLoadFail() {

    }

    private WeaponListAdapter.OnItemClickListener onItemClickListener = new WeaponListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(WeaponBean bean) {
            if (bean == null) {
                return;
            }
            Intent intent = new Intent(WeaponListFragment.this.getActivity(), YoutubeActivity.class);
            intent.putExtra("data", bean);
            startActivity(intent);

        }
    };
}
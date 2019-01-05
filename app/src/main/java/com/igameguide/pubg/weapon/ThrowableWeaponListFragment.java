package com.igameguide.pubg.weapon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igameguide.pubg.R;
import com.igameguide.pubg.base.PubgApplication;
import com.igameguide.pubg.util.ConstantValue;
import com.igameguide.pubg.video.YoutubeActivity;
import com.igameguide.pubg.weapon.atapter.WeaponListAdapter;
import com.igameguide.pubg.weapon.bean.WeaponBean;
import com.igameguide.pubg.weapondetail.WeaponDetailActivity;
import com.igameguide.pubg.widget.EasyDivider;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


@SuppressLint("ValidFragment")
public class ThrowableWeaponListFragment extends Fragment implements WeaponListContract.View {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private WeaponListContract.Presenter mPresenter;
    private WeaponListAdapter mAdapter;

    public static ThrowableWeaponListFragment getInstance() {
        ThrowableWeaponListFragment sf = new ThrowableWeaponListFragment();
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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);

        EasyDivider easyDivider = new EasyDivider();

        recyclerView.addItemDecoration(easyDivider);

        recyclerView.setLayoutManager(gridLayoutManager);

        getData();




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

    private WeaponListAdapter.OnItemClickListener onItemClickListener = bean -> {
        if (bean == null) {
            return;
        }
        Intent intent = new Intent(ThrowableWeaponListFragment.this.getActivity(), WeaponDetailActivity.class);
        intent.putExtra("data", bean);
        intent.putExtra(ConstantValue.IntentKey.WEAPON_TYPE, ConstantValue.WeaponType.THROW_WEAPON);
        startActivity(intent);

    };

    /**
     * 异步的获取数据，避免卡顿
     */
    private void getData() {

        PubgApplication.getInstance().addTask(() -> {
            List<WeaponBean> datas = mPresenter.getThrowWeaponData("en");
            if (isAdded()) {
               getActivity().runOnUiThread(() -> mAdapter.setmData(datas));
            }

        });



    }
}
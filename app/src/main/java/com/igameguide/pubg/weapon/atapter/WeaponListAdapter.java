package com.igameguide.pubg.weapon.atapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igameguide.pubg.R;
import com.igameguide.pubg.base.GlideApp;
import com.igameguide.pubg.weapon.bean.WeaponBean;
import com.igameguide.pubg.widget.PubgImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeaponListAdapter extends RecyclerView.Adapter<WeaponListAdapter.VH> implements View.OnClickListener {

    private List<WeaponBean> mData;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;


    public WeaponListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_video_list, null, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        WeaponBean itemBean = mData.get(i);

        AssetManager assetManager = mContext.getAssets();
        InputStream is = null;
        try {
            is = assetManager.open("images/" + itemBean.logoId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (is == null) {
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        GlideApp.with(mContext).load(bitmap).into(vh.ivImg);
        vh.tvTitle.setText(itemBean.name);
        vh.llWhole.setOnClickListener(this);
        vh.llWhole.setTag(itemBean);

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_whole:
                WeaponBean itemBean = (WeaponBean) v.getTag();
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(itemBean);
                }
                break;
        }
    }

    public static class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_img)
        PubgImageView ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.ll_whole)
        LinearLayout llWhole;

        public VH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }

    public void setmData(List<WeaponBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener{
        void onItemClick(WeaponBean bean);
    }


    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}

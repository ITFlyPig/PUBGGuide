package com.igameguide.pubg.pic.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.igameguide.pubg.R;
import com.igameguide.pubg.pic.bean.WallPaperBean;
import com.igameguide.pubg.video.bean.VideoItemBean;
import com.igameguide.pubg.widget.PubgImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WallPaperAdapter extends RecyclerView.Adapter<WallPaperAdapter.VH> implements View.OnClickListener {


    private List<WallPaperBean> mData;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;


    public WallPaperAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_wallpaper_list, null, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {


        int temp = i * 3;
        if (temp < mData.size()) {
            WallPaperBean itemBeanLeft = mData.get(i);
            Glide.with(mContext)
                    .load(itemBeanLeft.preview)
                    .into(vh.ivLeft);
            vh.ivLeft.setTag(R.id.tag_second_key, itemBeanLeft);
            vh.ivLeft.setOnClickListener(this);
        }
        if (temp + 1 < mData.size()) {
            WallPaperBean itemBeanMiddle = mData.get(temp + 1);
            Glide.with(mContext)
                    .load(itemBeanMiddle.preview)
                    .into(vh.ivMiddle);
            vh.ivMiddle.setTag(R.id.tag_second_key, itemBeanMiddle);
            vh.ivMiddle.setOnClickListener(this);
        }

        if (temp + 2 < mData.size()) {
            WallPaperBean itemBeanRight = mData.get(temp + 2);
            Glide.with(mContext)
                    .load(itemBeanRight.preview)
                    .into(vh.ivRight);
            vh.ivRight.setTag(R.id.tag_second_key ,itemBeanRight);

            vh.ivRight.setOnClickListener(this);
        }


    }

    @Override
    public int getItemCount() {
        int size = mData == null ? 0 : (int) (mData.size() / 3f + 1);
        Log.d("wyl", "行数：" + size);
        return size;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
            case R.id.iv_right:
            case R.id.iv_middle:
                WallPaperBean itemBean = (WallPaperBean) v.getTag(R.id.tag_second_key);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(itemBean);
                }
                break;
        }
    }

    public static class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_left)
        ImageView ivLeft;
        @BindView(R.id.iv_middle)
        ImageView ivMiddle;
        @BindView(R.id.iv_right)
        ImageView ivRight;
        @BindView(R.id.ll_whole)
        LinearLayout llWhole;

        public VH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }

    public void setmData(List<WallPaperBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(WallPaperBean bean);
    }


    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}

package com.statstracker.forpubggame.video.atapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.statstracker.forpubggame.R;
import com.statstracker.forpubggame.video.bean.VideoItemBean;
import com.statstracker.forpubggame.widget.PubgImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VH> implements View.OnClickListener {

    private List<VideoItemBean> mData;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;


    public VideoListAdapter(Context mContext) {
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
        VideoItemBean itemBean = mData.get(i);
        vh.tvTitle.setText(itemBean.title);
        Picasso.get().load(itemBean.coverUrl).into(vh.ivImg);
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
                VideoItemBean itemBean = (VideoItemBean) v.getTag();
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

    public void setmData(List<VideoItemBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener{
        void onItemClick(VideoItemBean bean);
    }


    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}

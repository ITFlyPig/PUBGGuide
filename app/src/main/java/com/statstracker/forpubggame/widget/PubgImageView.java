package com.statstracker.forpubggame.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.statstracker.forpubggame.R;

/**
 * 自定义View，添加了一些特定的功能：
 * 1、按比例显示
 */
public class PubgImageView extends android.support.v7.widget.AppCompatImageView{

    private boolean isUseScale;//是否使用比例的功能 w / h
    private float mScale = 1;

    public PubgImageView(Context context) {
        this(context, null);
    }

    public PubgImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PubgImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PubgImageView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int index = a.getIndex(i);
            switch (index) {
                case R.styleable.PubgImageView_isUseScale:
                    isUseScale = a.getBoolean(index, false);
                    break;
                case R.styleable.PubgImageView_scale:
                    mScale = a.getFloat(index, 1);
                    break;

            }
        }
        a.recycle();


        init();
    }


    private void init() {

    }

    /**
     * 设置比例
     * @param scale
     */
    public void setScale(float scale) {
        this.mScale = scale;
        invalidate();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int w = MeasureSpec.getSize(widthMeasureSpec);

        if (isUseScale) {
            //获得宽度，然后按比例设置高度
            int h = (int) (w / mScale);
            setMeasuredDimension(widthMeasureSpec ,MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY));

        }


    }
}

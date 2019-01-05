package com.igameguide.pubg.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class NoSmoothScrollViewPager  extends ViewPager{
    public NoSmoothScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoSmoothScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }
}

package com.nacity.college.base.utils;

import com.github.jdsjlzx.recyclerview.LRecyclerView;

/**
 * Created by xz on 2016/7/14.
 */
public class ScrollViewWithListView extends LRecyclerView {

    public ScrollViewWithListView(android.content.Context context,
                                  android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Integer.MAX_VALUE >> 2,如果不设置，系统默认设置是显示两条
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

}
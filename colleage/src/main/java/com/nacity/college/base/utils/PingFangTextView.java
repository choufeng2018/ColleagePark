package com.nacity.college.base.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/11/13.
 */

public class PingFangTextView extends TextView {

    public PingFangTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public PingFangTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public PingFangTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("PingFang-Bold.ttf", context);
        setTypeface(customFont);
    }




}



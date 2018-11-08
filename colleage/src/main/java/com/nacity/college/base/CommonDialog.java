package com.nacity.college.base;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

public class CommonDialog extends Dialog {
    private static int default_width = 160; // 默认宽度
    private static int default_height = 120;// 默认高度


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public CommonDialog(Context context, int layout) {
        super(context);
        // TODO Auto-generated constructor stub
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        // set width,height by density and gravity
        float density = getDensity(context);
        params.width = LayoutParams.MATCH_PARENT;
        params.height = (int) (200 * density);
        params.gravity = Gravity.BOTTOM;
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        window.setAttributes(params);
    }

    public CommonDialog(Context context, int layout, int style) {
        super(context, style);
        // TODO Auto-generated constructor stub
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        // set width,height by density and gravity

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        window.setAttributes(params);
    }

    public CommonDialog(Context context, int width, int height, int layout, int style) {
        super(context, style);
        // set content
        setContentView(layout);
//		//set window params
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
//		//set width,height by density and gravity
        float density = getDensity(context);
        params.width = width;
        params.height = (int) (height * density);
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }


    public float getDensity(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.density;
    }
}
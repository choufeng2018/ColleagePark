package com.nacity.college.base.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.nacity.college.R;

/**
 * Created by xzz on 2017/9/5.
 **/

public class RxDialog extends Dialog{
    protected Context mContext;
    protected WindowManager.LayoutParams mLayoutParams;

    public WindowManager.LayoutParams getLayoutParams() {
        return this.mLayoutParams;
    }

    public RxDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.initView(context);
    }

    public RxDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.initView(context);
    }

    public RxDialog(Context context) {
        super(context);
        this.initView(context);
    }

    private void initView(Context context) {
        this.requestWindowFeature(1);
        this.getWindow().setBackgroundDrawableResource(R.drawable.transparent_bg);
        this.mContext = context;
        Window window = this.getWindow();
        this.mLayoutParams = window.getAttributes();
        this.mLayoutParams.alpha = 1.0F;
        window.setAttributes(this.mLayoutParams);
        if(this.mLayoutParams != null) {
            this.mLayoutParams.height = -1;
            this.mLayoutParams.gravity = 17;
        }

    }

    public RxDialog(Context context, float alpha, int gravity) {
        super(context);
        this.requestWindowFeature(1);
        this.getWindow().setBackgroundDrawableResource(R.drawable.transparent_bg);
        this.mContext = context;
        Window window = this.getWindow();
        this.mLayoutParams = window.getAttributes();
        this.mLayoutParams.alpha = 1.0F;
        window.setAttributes(this.mLayoutParams);
        if(this.mLayoutParams != null) {
            this.mLayoutParams.height = -1;
            this.mLayoutParams.gravity = gravity;
        }

    }

    public void skipTools() {
        if(Build.VERSION.SDK_INT >= 19) {
            this.getWindow().setFlags(1024, 1024);
        }
    }

    public void setFullScreen() {
        Window window = this.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = -1;
        lp.height = -1;
        window.setAttributes(lp);
    }

    public void setFullScreenWidth() {
        Window window = this.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = -1;
        lp.height = -2;
        window.setAttributes(lp);
    }

    public void setFullScreenHeight() {
        Window window = this.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = -2;
        lp.height = -1;
        window.setAttributes(lp);
    }

    public void setOnWhole() {
        this.getWindow().setType(2003);
    }
}

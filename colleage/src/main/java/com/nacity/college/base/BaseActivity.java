package com.nacity.college.base;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.nacity.college.R;
import com.nacity.college.ExpectActivity;
import com.nacity.college.MainApp;
import com.nacity.college.base.impl.FragmentPermissionListener;
import com.nacity.college.base.impl.PermissionListener;
import com.nacity.college.base.info.ApartmentInfoHelper;
import com.nacity.college.base.info.UserInfoHelper;
import com.nacity.college.base.utils.PublicWay;
import com.nacity.college.base.utils.SpUtil;
import com.nacity.college.base.utils.StatuBarUtil;
import com.nacity.college.push.ForceOfflineActivity;
import com.tbruyelle.rxpermissions.RxPermissions;

import es.dmoral.toasty.Toasty;

/**
 * Created by xzz on 2017/8/25.
 **/

public abstract class BaseActivity<T> extends FragmentActivity implements BaseView<T> {

    protected Context appContext;
    protected TextView titleName;
    private boolean dialogIsShow;
    protected UserInfoHelper userInfo = UserInfoHelper.getInstance(MainApp.mContext);
    protected ApartmentInfoHelper apartmentInfo = ApartmentInfoHelper.getInstance(MainApp.mContext);
    protected ParkLoadingDialog loadingDialog;
    private boolean canDismiss;
    protected Bundle transferAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        appContext = this;
        StatuBarUtil.setStatueBarTransparent(getWindow());
        StatuBarUtil.setStatueBarTextBlack(getWindow());


    }


    protected void setTitle(String titleContent) {

        titleName = (TextView) findViewById(R.id.title_name);
        if (titleName != null)
            titleName.setText(titleContent);
        View back = findViewById(R.id.back);
        if (back != null)
            back.setOnClickListener(v -> {
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                    finish();
                } else
                    onBackPressed();
            });


    }

    protected void setTitleName(String titleContent) {

        titleName = (TextView) findViewById(R.id.title_name);
        if (titleName != null)
            titleName.setText(titleContent);
        View back = findViewById(R.id.back);
        if (back != null)
            back.setOnClickListener(v -> {
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                    finish();
                } else
                    onBackPressed();
            });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToAnimation(2);
    }

    public void getPermission(String permission, PermissionListener permissionListener) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(permission).subscribe(grant -> {
            if (grant)
                permissionListener.accept(permission);
            else
                permissionListener.refuse(permission);

        });
    }

    public void getPermissionPhoto(String permission, View view, FragmentPermissionListener permissionListener) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(permission).subscribe(grant -> {
            if (grant)
                permissionListener.accept(permission, view);
            else
                permissionListener.refuse(permission);

        });
    }

    public void loadingShow() {
        canDismiss = false;
        if (loadingDialog == null)
            loadingDialog = new ParkLoadingDialog(appContext, "", R.drawable.loading_animation);
        if (!loadingDialog.isShowing()) {
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.show();
            loadingDialog.setOnDismissListener(dialog -> {
                if (!canDismiss) {
                    finish();
                }
            });
        }

    }

    public void loadingDismiss() {
        canDismiss = true;
        if (loadingDialog != null)
            loadingDialog.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
                finish();

            } else
                onBackPressed();
        }

        return super.onKeyDown(keyCode, event);

    }

    public int getScreenWidth() {
        WindowManager wm = (WindowManager) appContext.getSystemService(WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getWidth();
    }

    public void showInfo(String message) {
        loadingDismiss();
        Toasty.info(appContext, message).show();
    }

    public void showSuccess(String message) {
        loadingDismiss();
        Toasty.info(appContext, message).show();
    }

    public void showWarnInfo(String message) {
        loadingDismiss();
        Toasty.warning(appContext, message).show();
    }

    public void toExpectActivity(String title) {
        Intent intent = new Intent(appContext, ExpectActivity.class);
        intent.putExtra("Title", title);
        startActivity(intent);
    }

    public void loadError(Throwable e) {
        loadingDismiss();
        if (e instanceof java.net.SocketTimeoutException) {
            Toasty.normal(appContext, "连接超时").show();
        } else if (e instanceof java.net.UnknownHostException) {
            Toasty.normal(appContext, "没有网络的异次元").show();
        }

    }

    public Bundle gotoAnimation() {
        return ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
    }

    public void showErrorMessage(String message) {
        loadingDismiss();
        Toasty.warning(appContext, message).show();
    }

    public void goToAnimation(int type) {

        switch (type) {
            case 1:
                // 跳转到下一个界面
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case 2:
                // 按返回
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case 3:
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
    }

    public void getDataSuccess(T data) {
        loadingDismiss();
    }

    public void showMessage(String message) {
        loadingDismiss();
        Toasty.warning(appContext, message).show();
    }

    @Override
    protected void onRestart() {
        if (SpUtil.getBoolean("IsOffLine")) {
            if (PublicWay.forceOfflineActivity == null) {
                Intent intent1 = new Intent(this, ForceOfflineActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
//                goToAnimation(2);
            }
        }
        super.onRestart();
    }

    @Override
    public void submitDataSuccess(Object data) {
        loadingDismiss();
    }
}

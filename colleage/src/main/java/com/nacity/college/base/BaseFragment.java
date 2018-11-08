package com.nacity.college.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.nacity.college.R;
import com.nacity.college.ExpectActivity;
import com.nacity.college.MainApp;
import com.nacity.college.base.impl.FragmentPermissionListener;
import com.nacity.college.base.impl.PermissionListener;
import com.nacity.college.base.info.ApartmentInfoHelper;
import com.nacity.college.base.info.UserInfoHelper;
import com.tbruyelle.rxpermissions.RxPermissions;

import es.dmoral.toasty.Toasty;


/**
 * Created by xzz on 2017/6/29.
 **/

public abstract class BaseFragment<T> extends Fragment implements BaseView<T> {
    protected Context appContext = MainApp.mContext;
    protected ParkLoadingDialog loadingDialog;
    private boolean canDismiss;
    protected SharedPreferences sp;
    protected UserInfoHelper userInfo = UserInfoHelper.getInstance(MainApp.mContext);
    protected ApartmentInfoHelper apartmentInfo = ApartmentInfoHelper.getInstance(MainApp.mContext);



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void getPermission(String permission, PermissionListener permissionListener) {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(permission).subscribe(grant -> {

            if (grant)
                permissionListener.accept(permission);
            else
                permissionListener.refuse(permission);

        });
    }

    public void loadingShow() {
        canDismiss = false;
        if (loadingDialog == null)
            loadingDialog = new ParkLoadingDialog(getActivity(), "", R.drawable.loading_animation);
        if (!loadingDialog.isShowing()) {
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.show();
            loadingDialog.setOnDismissListener(dialog -> {
                if (!canDismiss) {
                    getActivity().finish();
                }
            });
        }

    }

    public void loadingDismiss() {
        canDismiss = true;
        if (loadingDialog != null)
            loadingDialog.dismiss();

    }

    protected void successShow(String message) {
        Toasty.info(appContext, message).show();
    }

    public int getScreenWidthPixels(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public void goToAnimation(int type) {

        switch (type) {
            case 1:
                // 跳转到下一个界面
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case 2:
                // 按返回
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case 3:
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
    }

    public int getScreenWidth() {
        WindowManager wm = (WindowManager) appContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getWidth();
    }


    public void getPermissionPhoto(String permission, View view, FragmentPermissionListener permissionListener) {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(permission).subscribe(grant -> {
            if (grant)
                permissionListener.accept(permission, view);
            else
                permissionListener.refuse(permission);

        });
    }

    public void loadError(Throwable e) {
        System.out.println(e+"error***********");
        loadingDismiss();
        if (e instanceof java.net.SocketTimeoutException) {
            Toasty.normal(appContext, "连接超时").show();
        } else if (e instanceof java.net.UnknownHostException) {
            Toasty.normal(appContext, "没有网络的异次元").show();
        }

    }

    public void showErrorMessage(String message) {
        loadingDismiss();
        Toasty.warning(appContext, message).show();
    }

    public void showMessage(String message) {
        loadingDismiss();
        Toasty.warning(appContext, message).show();

    }

    public void toExpectActivity(String title) {
        Intent intent = new Intent(appContext, ExpectActivity.class);
        intent.putExtra("Title", title);
        startActivity(intent);
    }

    @Override
    public void getDataSuccess(T data) {
        loadingDismiss();
    }

    @Override
    public void submitDataSuccess(Object data) {
        loadingDismiss();
    }
}

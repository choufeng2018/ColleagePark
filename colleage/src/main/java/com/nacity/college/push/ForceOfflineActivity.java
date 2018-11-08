package com.nacity.college.push;

import android.content.Intent;
import android.os.Bundle;

import com.nacity.college.R;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.CommonDialog;
import com.nacity.college.base.info.UserInfoHelper;
import com.nacity.college.base.utils.PublicWay;
import com.nacity.college.base.utils.SpUtil;
import com.nacity.college.base.utils.StatuBarUtil;
import com.nacity.college.login.LoginOrRegisterActivity;
import com.zhidian.cloudintercomlibrary.CloudIntercomLibrary;

import cn.jpush.android.api.JPushInterface;

/**
 *  Created by xzz on 2017/7/26.
 **/
public class ForceOfflineActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatuBarUtil.setStatueBarTransparent(getWindow());
        setContentView(R.layout.activity_push_jump);
        PublicWay.forceOfflineActivity=this;
        offlineDialog();
    }

    public void offlineDialog(){
      if ( PublicWay.pushJumpActivity!=null) {
          PublicWay.pushJumpActivity.finish();
          PublicWay.pushJumpActivity=null;
      }
//
        JPushInterface.clearAllNotifications(this);
        CloudIntercomLibrary.getInstance().logout(appContext,null);
        CommonDialog alertDialog = new CommonDialog(this, R.layout.dialog_forceoffline, R.style.myDialogTheme);
            alertDialog.show();
            alertDialog.setCancelable(false);

            alertDialog.findViewById(R.id.btn_go).setOnClickListener(v -> {
                UserInfoHelper mUserHelper = UserInfoHelper.getInstance(MainApp.mContext);
                mUserHelper.updateLogin(false);
                mUserHelper.updateUser(null);
            SpUtil.put( "IsOffLine", false);
                alertDialog.dismiss();
                Intent intent_01 = new Intent(this, LoginOrRegisterActivity.class);
                intent_01.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SpUtil.put(MainApp.KeyValue.KEY_HOME_DATA, "");

                startActivity(intent_01);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
                PublicWay.forceOfflineActivity=null;
            });
        }


}

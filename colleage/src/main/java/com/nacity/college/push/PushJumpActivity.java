package com.nacity.college.push;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.nacity.college.R;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.CommonDialog;
import com.nacity.college.base.Constant;
import com.nacity.college.base.utils.PublicWay;
import com.nacity.college.base.utils.SpUtil;
import com.nacity.college.base.utils.StatuBarUtil;
import com.nacity.college.circle.FansActivity;

import com.nacity.college.news.NewsDetailActivity;
import com.nacity.college.news.ParkActivitiesDetailAcitivity;
import com.nacity.college.property.ApplyDetailActivity;
import com.nacity.college.property.ParkingApplyDetailActivity;
import com.nacity.college.property.PraiseDetailActivity;
import com.nacity.college.property.ServiceDetailActivity;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by usb on 2016/8/5.
 **/
public class PushJumpActivity extends BaseActivity {


    private String data;
    private int    notificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatuBarUtil.setStatueBarTransparent(getWindow());
        setContentView(R.layout.activity_push_jump);
        data= SpUtil.getString("ReceiverData");
        notificationId= SpUtil.getInt("ReceiverNotificationId");

        competenceShow();
        PublicWay.pushJumpActivity=this;

    }

    /**
     * 派单弹窗
     **/
    private CommonDialog dialog;
    private void competenceShow() {
        Log.i("222222", "competenceShow: ");
          dialog = new CommonDialog(this, R.layout.dialog_assignment, R.style.myDialogTheme);
        Button btn_go = (Button) dialog.findViewById(R.id.btn_go);
        Button btn_close = (Button) dialog.findViewById(R.id.btn_close);
        TextView titleName = (TextView)dialog. findViewById(R.id.title_name);
        if (data!=null)
            titleName.setText(JSONObject.parseObject(data).getString("message"));
        String taskType= JSONObject.parseObject(data).getString("type");

            btn_go.setOnClickListener((View v) -> {
//                ChangeParkUtil.changeToHome(getThisContext(), mUserHelper);
//
//                    EventBus.getDefault().post(new RefreshEvent("TaskPoint"));
//                EventBus.getDefault().post(new RefreshEvent("TaskPoint"));
               message();
               dialog.dismiss();

           });
        btn_close.setOnClickListener((View v) -> {
            dialog.dismiss();
            finish();

        });

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnKeyListener(keylistener);
        dialog.show();

    }

    private void message() {


        if (!TextUtils.isEmpty(data)) {
        int type = Integer.parseInt(JSONObject.parseObject(data).getString("type"));

            if (isRunningForeground()) {
                ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
                ComponentName cn = am.getRunningTasks    (1).get(0).topActivity;
                Context c = null;
                try {

                    c = createPackageContext(cn.getPackageName(), CONTEXT_INCLUDE_CODE | CONTEXT_IGNORE_SECURITY);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                jumpActivity(c, type, data);

            } else {
                jumpActivity(this, type, data);
            }


        }
        JPushInterface.clearNotificationById(this, notificationId);
    }
    private  String[] serviceType={"4","0","1","3","2"};
    /**serviceType
     * 数据库type ---->  本地type
     * 1  家政服务----> 4  家政服务
     * 2  入室维修---->0  入室维修
     * 3  公共维修----> 1  公共维修
     * 4  园区配送---->3  园区配送
     * 5  投诉---->2投诉
**/
    /**
     * 要推送的消息
     */

    /***message
     * 消息类型
     1：圈子关注
     2：园区新闻
     3：车位申请回复
     4：装修申请回复
     5：服务工单被响应（入室、公共、投诉）
     6：服务工单处理中（入室、公共、投诉）
     7：服务工单处理完成（入室、公共、投诉）
     8：建议
     9：表扬
     10：园区活动
     100：账号挤下线
     */
//    private String messageType;
    private void jumpActivity(Context context, int type, String data) {

        Intent i;
       String sid = JSONObject.parseObject(data).getString("sid");
//        String sid = JSONObject.parseObject(data).getString("goodsId");
        System.out.println(type+"type"+sid);
        switch (type) {
//            1：圈子关注
            case 1:
                i = new Intent(context, FansActivity.class);
                startActivity(i);
                SpUtil.put(MainApp.KeyValue.KEY_HOME_DATA, "");
                goToAnimation(1);
                finish();
                break;
            // 2：园区新闻
            case 2:
               i = new Intent(context, NewsDetailActivity.class);
                i.putExtra("id", sid);
                i.putExtra("type","1");
                i.putExtra("noticeType",JSONObject.parseObject(data).getString("noticeType"));
                startActivity(i);
                SpUtil.put(MainApp.KeyValue.KEY_HOME_DATA, "");
                finish();
                break;
//            车位申请回复
            case 3:
                i = new Intent(context, ParkingApplyDetailActivity.class);
                i.putExtra("ParkingId",sid);
                startActivity(i);
                SpUtil.put(MainApp.KeyValue.KEY_HOME_DATA, "");
                goToAnimation(1);
                finish();

                break;
//            装修申请回复
            case 4:
                i=new Intent(appContext, ApplyDetailActivity.class);
                i.putExtra("DecorateId",sid);
                startActivity(i);
                SpUtil.put(MainApp.KeyValue.KEY_HOME_DATA, "");
                goToAnimation(1);
                finish();
                break;
            /**serviceType
             * 数据库type ---->  本地type
             * 1  家政服务----> 4  家政服务
             * 2  入室维修---->0  入室维修
             * 3  公共维修----> 1  公共维修
             * 4  园区配送---->3  园区配送
             * 5  投诉---->2投诉
             **/
//            服务工单被响应
            case 5:

                i=new Intent(appContext, ServiceDetailActivity.class);
                i.putExtra("ServiceId",sid);
                Log.i("2222", "jumpActivity1: "+JSONObject.parseObject(data).getString("serviceType"));
                Log.i("2222", "jumpActivity2: "+Integer.parseInt(JSONObject.parseObject(data).getString("serviceType")));
                i.putExtra("Type",serviceType[Integer.parseInt(JSONObject.parseObject(data).getString("serviceType"))-1] );
                startActivity(i);
                SpUtil.put(MainApp.KeyValue.KEY_HOME_DATA, "");
                goToAnimation(1);
                finish();

                break;
//            服务工单处理中
//             case 6:
//                 i=new Intent(appContext, ServiceDetailActivity.class);
//                i.putExtra("ServiceId",sid);
//                 i.putExtra("Type",serviceType[Integer.parseInt(JSONObject.parseObject(data).getString("serviceType"))-1] );
//                startActivity(i);
//                SpUtil.put(MainApp.KeyValue.KEY_HOME_DATA, "");
//                goToAnimation(1);
//                 finish();
//
//                 break;
//          服务工单处理完成
            case 7:
                i=new Intent(appContext, ServiceDetailActivity.class);
                i.putExtra("ServiceId",sid);
                i.putExtra("Type",serviceType[Integer.parseInt(JSONObject.parseObject(data).getString("serviceType"))-1] );
                startActivity(i);
                SpUtil.put(MainApp.KeyValue.KEY_HOME_DATA, "");
                goToAnimation(1);
                finish();

                break;
//            建议
            case 8:
                i=new Intent(appContext, PraiseDetailActivity.class);
                i.putExtra("FeedbackId",sid);
                i.putExtra("Title", Constant.SUGGEST_DETAIL);
                startActivity(i);
                SpUtil.put(MainApp.KeyValue.KEY_HOME_DATA, "");
                goToAnimation(1);
                finish();

                break;
//            表扬
            case 9:
                i=new Intent(appContext, PraiseDetailActivity.class);
                i.putExtra("FeedbackId",sid);
                i.putExtra("Title", Constant.PRAISE_DETAIL);
                startActivity(i);
                SpUtil.put(MainApp.KeyValue.KEY_HOME_DATA, "");
                goToAnimation(1);
                finish();
                break;
 //           园区活动

            case 10:
                i=  new Intent(appContext,ParkActivitiesDetailAcitivity.class);
                i.putExtra("id",sid);
                Log.i("2222", "setRecycleView: "+sid);
                startActivity(i);
                SpUtil.put(MainApp.KeyValue.KEY_HOME_DATA, "");
                goToAnimation(1);
                finish();
                break;
            case 11:

                break;
            case 12:

                break;
            default:
        }
    }


    private boolean isRunningForeground() {
        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        return !TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(getPackageName());
    }


    protected void onDestroy() {
        super.onDestroy();
    }

    private DialogInterface.OnKeyListener keylistener = (dialog, keyCode, event) -> {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            return true;
        } else {
            return false;
        }
    };
}

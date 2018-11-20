package com.nacity.college.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.college.common_libs.domain.user.UpdateTo;
import com.nacity.college.R;
import com.nacity.college.base.utils.StatuBarUtil;
import com.nacity.college.databinding.UpdateLayoutBinding;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.base.impl.PermissionListener;
import com.nacity.college.base.utils.AppUtil;
import com.nacity.college.base.utils.NoSlideViewPager;
import com.nacity.college.base.utils.PublicWay;
import com.nacity.college.base.utils.SpUtil;
import com.nacity.college.circle.FansActivity;
import com.nacity.college.main.fragment.CircleFragment;
import com.nacity.college.main.fragment.MailFragment;
import com.nacity.college.main.fragment.MainHomeFragment2;
import com.nacity.college.main.fragment.MyselfFragment;
import com.nacity.college.main.model.UpdateModel;
import com.nacity.college.main.presenter.UpdatePresenter;
import com.nacity.college.main.view.UpdateView;
import com.nacity.college.news.NewsDetailActivity;
import com.nacity.college.news.ParkActivitiesDetailAcitivity;
import com.nacity.college.property.ApplyDetailActivity;
import com.nacity.college.property.ParkingApplyDetailActivity;
import com.nacity.college.property.PraiseDetailActivity;
import com.nacity.college.property.ServiceDetailActivity;
import com.nacity.college.push.ForceOfflineActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity implements UpdateView, PermissionListener {


    @BindView(R.id.viewPager)
    NoSlideViewPager viewPager;
    @BindView(R.id.main_home_icon)
    View mainHomeIcon;
    @BindView(R.id.main_home_text)
    TextView mainHomeText;
    @BindView(R.id.main_circle_icon)
    View mainCircleIcon;
    @BindView(R.id.main_circle_text)
    TextView mainCircleText;
    @BindView(R.id.main_mail_icon)
    View mainMailIcon;
    @BindView(R.id.main_mail_text)
    TextView mainMailText;
    @BindView(R.id.main_myself_icon)
    View mainMyselfIcon;
    @BindView(R.id.main_myself_text)
    TextView mainMyselfText;
    private List<Fragment> fragmentList = new ArrayList<>();
    private Handler handler = new Handler();
    private boolean canClose;  //判断是否可以返回退出app
    private String downLoadUrl;
    private UpdateModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        StatuBarUtil.setStatueBarTextBlack(getWindow());
        initFragment();
        model = new UpdatePresenter(this);
        model.getUpdateInfo();
        SpUtil.put("IsSplashActivity", false);

        message();
        isOffLian();
    }
    @SuppressWarnings("deprecation")
    private boolean isRunningForeground() {
        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        return !TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(getPackageName());
    }

    @SuppressWarnings("deprecation")
    private void message() {
        String data = SpUtil.getString(MainApp.KeyValue.KEY_HOME_DATA);
        Log.i("99999", "data: "+data);
        if (!TextUtils.isEmpty(data)) {

            int type = Integer.parseInt(JSONObject.parseObject(data).getString("type"));

            if (isRunningForeground()) {
                ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
                ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
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
    }
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
             *
             *
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
//            园区活动
            case 10:
                i=  new Intent(appContext,ParkActivitiesDetailAcitivity.class);
                i.putExtra("id",sid);
                Log.i("2222", "setRecycleView: "+sid);
                startActivity(i);
                SpUtil.put(MainApp.KeyValue.KEY_HOME_DATA, "");
                goToAnimation(1);
                finish();
                break;
            default:
        }
        }
    private  String[] serviceType={"4","0","1","3","2"};
    private void isOffLian() {

        if (SpUtil.getBoolean("IsOffLine")){
            if (PublicWay.forceOfflineActivity==null) {
                Intent intent1 = new Intent(this, ForceOfflineActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
//                 goToAnimation(2);
            }
        }
    }
    private void initFragment() {
        fragmentList.add(new MainHomeFragment2());
        fragmentList.add(new CircleFragment());

        fragmentList.add(new MyselfFragment());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setMenuLayout(position);
                if (position==2)
                    ((MyselfFragment)fragmentList.get(2)).getUserInfo();
                else if (position==1)
                    ((CircleFragment)fragmentList.get(1)).setUserInfo();
                else if (position==0) {
                 handler.postDelayed(() -> ((MainHomeFragment2) fragmentList.get(0)).getData(),500);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(getIntent().getIntExtra("Index",0));
    }

    FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }
    };

    @OnClick({R.id.main_home_layout, R.id.main_circle_layout,  R.id.main_myself_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_home_layout:
                viewPager.setCurrentItem(0);
                break;
            case R.id.main_circle_layout:
                viewPager.setCurrentItem(1);
                break;
            case R.id.main_myself_layout:
                viewPager.setCurrentItem(2);
                break;
        }
    }

    void setMenuLayout(int currentPosition) {
        mainHomeIcon.setBackgroundResource(currentPosition == 0 ? R.drawable.main_home_select : R.drawable.main_home_un_select);
        mainHomeText.setTextColor(currentPosition == 0 ? Color.parseColor("#6d75a4") : Color.parseColor("#b4b4b4"));
        mainCircleIcon.setBackgroundResource(currentPosition == 1 ? R.drawable.main_circle_select : R.drawable.main_circle_un_select);
        mainCircleText.setTextColor(currentPosition == 1 ? Color.parseColor("#6d75a4") : Color.parseColor("#b4b4b4"));
        mainMyselfIcon.setBackgroundResource(currentPosition == 2 ? R.drawable.main_myself_select : R.drawable.main_myself_un_select);
        mainMyselfText.setTextColor(currentPosition == 2 ? Color.parseColor("#6d75a4") : Color.parseColor("#b4b4b4"));

    }

    @SuppressLint("RtlHardcoded")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (!canClose) {
                showInfo(Constant.CLOSE_APP);
                canClose = true;
                handler.postDelayed(() -> canClose = false, 5000);
                return true;
            }
            }



        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showUpdateDialog(UpdateTo updateTo) {
        NiftyDialogBuilder dialog = NiftyDialogBuilder.getInstance(appContext);
        View mView=View.inflate(appContext,R.layout.update_layout,null);
        dialog.setContentView(mView);
        UpdateLayoutBinding bind = DataBindingUtil.bind(mView);
        if (updateTo.getVerCode()> AppUtil.getVersionCode(appContext)){
//            bind.updateTitle.setText("是否升级到"+updateTo.getAppVersion()+"版本");
//            bind.updateSize.setText("大小："+updateTo.getAppSize());
            bind.noUpdateLayout.setVisibility(View.GONE);
            bind.updateLayout.setVisibility(View.VISIBLE);
            bind.updateContent.setText(updateTo.getUpdateDesc());
            bind.confirm.setOnClickListener(v -> {
                dialog.dismiss();
                getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,this);
            });
            bind.cancel.setOnClickListener(v -> dialog.dismiss());
            downLoadUrl=updateTo.getDownloadUrl();
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        }


    }


    private void showDownloadApp(String url){
        NiftyDialogBuilder dialog = NiftyDialogBuilder.getInstance(this);
        dialog.setContentView(R.layout.update_download_view);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        ProgressBar progressBar= (ProgressBar) dialog.findViewById(R.id.progress_bar);
        TextView updateProgress = (TextView) dialog.findViewById(R
                .id.update_progress);
        model.downloadApp(url,progressBar,updateProgress);
    }

    @Override
    public void installApk() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                "park.apk")), "application/vnd.android.package-archive");
        startActivity(intent);
        finish();
    }

    @Override
    public void accept(String permission) {
        showDownloadApp(downLoadUrl);
    }

    @Override
    public void refuse(String permission) {

    }
}

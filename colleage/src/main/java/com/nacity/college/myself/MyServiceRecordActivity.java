package com.nacity.college.myself;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.base.utils.EasySlidingTabRecord;
import com.nacity.college.base.utils.StatuBarUtil;
import com.nacity.college.myself.model.MyServiceRecordModel;
import com.nacity.college.myself.presenter.MyServiceRecordPresenter;
import com.nacity.college.myself.view.MyServiceRecordView;
import com.nacity.college.property.fragment.DecorateApplyRecordFragment;
import com.nacity.college.property.fragment.ParkingApplyFragment;
import com.nacity.college.property.fragment.PraiseRecordFragment;
import com.nacity.college.property.fragment.ServiceRecordFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xzz on 2017/9/14.
 **/

public class MyServiceRecordActivity extends BaseActivity implements MyServiceRecordView {

    @BindView(R.id.tpi)
    EasySlidingTabRecord tpi;
    @BindView(R.id.viewPager)
    ViewPager            viewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private PopupWindow popupWindow;
    private MyServiceRecordModel model;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service_record);
        ButterKnife.bind(this);
        StatuBarUtil.setStatueBarBlueColor(getWindow());
        setTitle(Constant.SERVICE_RECORD);
        model=new MyServiceRecordPresenter(this);
        setTpi();
        model.getServiceType();
    }

    private void setTpi() {
        //0室内维修,1公共维修,2 投诉、3装修申请、4车位申请、5园区配送、6表扬、7建议
//        mFragments.add(new ServiceRecordFragment("0"));//室内维修
//        mFragments.add(new ServiceRecordFragment("1"));//公共维修
//        mFragments.add(new ServiceRecordFragment("4"));//保洁服务
//        mFragments.add(new PraiseRecordFragment(1));//表扬
//        mFragments.add(new PraiseRecordFragment(2));//建议
//        mFragments.add(new ServiceRecordFragment("2"));//投诉
//        mFragments.add(new DecorateApplyRecordFragment());//装修申请
//        mFragments.add(new ParkingApplyFragment());//车位申请
//        mFragments.add(new ServiceRecordFragment("3"));//园区配送
        mFragments.add(new ServiceRecordFragment("0"));//室内维修
        mFragments.add(new ServiceRecordFragment("1"));//公共维修
        mFragments.add(new ServiceRecordFragment("2"));//投诉
        mFragments.add(new DecorateApplyRecordFragment());//装修申请
        mFragments.add(new ParkingApplyFragment());//车位申请
        mFragments.add(new ServiceRecordFragment("3"));//园区配送
        mFragments.add(new PraiseRecordFragment(1));//表扬
        mFragments.add(new PraiseRecordFragment(2));//建议
        viewPager.setAdapter(adapter);
        tpi.setViewPager(viewPager);
        tpi.setTextSize(getScreenWidth() * 26 / 750);
    }

    FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return 8;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Constant.MY_SERVICE_RECORD[position];
        }
    };

    @OnClick({R.id.service_channel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.service_channel:
                showTypeDialog();
                break;
        }

    }

    private void showTypeDialog() {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.dialog_service_record, null);

        GridLayout channelGorup = (GridLayout)contentView. findViewById(R.id.channel_group);

        for(int i = 0; i< Constant.MY_SERVICE_RECORD.length; i++){
            HashMap<String,Integer> hashMap=new HashMap<>();
            hashMap.put(Constant.MY_SERVICE_RECORD[i], i);
            View linearLayout= LinearLayout.inflate(this, R.layout.server_record_item, null);
            TextView serviceName = (TextView) linearLayout.findViewById(R.id.service_name);
            serviceName.setText(Constant.MY_SERVICE_RECORD[i]);
            WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            int mWidth=display.getWidth();
            int marginTop =(int)(mWidth*0.0583);
            int  width = (int)(mWidth*0.3055);
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
//            lp.width =  GridLayout.LayoutParams.WRAP_CONTENT;
            lp.width = width;
            lp.height = GridLayout.LayoutParams.WRAP_CONTENT;
            linearLayout.setTag(Constant.MY_SERVICE_RECORD[i]);
            lp.setMargins(0, marginTop, 0, 0);

            linearLayout.setLayoutParams(lp);
            linearLayout.setOnClickListener(v -> {
                viewPager.setCurrentItem(hashMap.get(v.getTag()));
                popupWindow.dismiss();

            });
            channelGorup.addView(linearLayout);
        }


        contentView.setFocusable(true);
        contentView.setFocusableInTouchMode(true);

        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        contentView.findViewById(R.id.pup_layout).setOnClickListener(v -> popupWindow.dismiss());
        popupWindow.setOutsideTouchable(true);

//        if (Build.VERSION.SDK_INT < 24)
//        {
//            popupWindow.showAsDropDown(easySlidingTabs);
//
//        }
//        else
//        {
//            // 适配 android 7.0
//            int[] location = new int[2];
//            easySlidingTabs.getLocationInWindow(location);
//            int x=location[0];
//            int y=location[1];
//           Log.i("22222", "x"+x+"y"+y);
//            popupWindow.showAtLocation(easySlidingTabs, Gravity.NO_GRAVITY, x,y+easySlidingTabs.getHeight());
//        }
        popupWindow.showAtLocation(tpi, Gravity.NO_GRAVITY, 0,0);
    }


    @Override
    public void getServiceTypeSuccess(String i) {
//        viewPager.setCurrentItem(Integer.parseInt(i));

    }
}
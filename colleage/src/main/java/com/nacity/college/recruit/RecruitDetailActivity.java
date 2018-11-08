package com.nacity.college.recruit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.college.common_libs.domain.recruit.RecruitItemTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.CommonDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *  Created by usb on 2017/9/8.
 */

public class RecruitDetailActivity extends BaseActivity {
    @BindView(R.id.workName)
     TextView   mWorkName;

    @BindView(R.id.wage)
     TextView   mWage;
    @BindView(R.id.upDateTime)
     TextView   mUpDateTime;
    @BindView(R.id.tv_workName)
     TextView   mTvWorkName;
    @BindView(R.id.smallWorkName)
     TextView   mSmallWorkName;
    @BindView(R.id.tv_command)
     TextView   mTvCommand;
    @BindView(R.id.command)
     TextView   mCommand;
    @BindView(R.id.tv_welfare)
     TextView   mTvWelfare;
    @BindView(R.id.welfare)
     GridLayout mWelfare;
    @BindView(R.id.workAddress)
     TextView   mWorkAddress;
    @BindView(R.id.workDescription)
     TextView   mWorkDescription;
    @BindView(R.id.companyDescription)
     TextView   mCompanyDescription;

    private String     phoneNumber;
    @BindView(R.id.statue)
     TextView   statue;
    private int        type;
    private String     recruitSid;
    @BindView(R.id.phone)
     TextView   phone;
    @BindView(R.id.companyName)
     TextView   companyName;


    private int           canUpdate;
    private RecruitItemTo itemTo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit_detail);
        ButterKnife.bind(this);
        setTitle("招聘详情");
        itemTo= (RecruitItemTo)getIntent().getSerializableExtra("mode");
        setView();
    }
    private void setView( ) {
        if (itemTo==null)
            return;
//        canUpdate=itemTo.getTodayUpdate();
//        if (getIntent().getBooleanExtra("IsMyRecruit",false)) {
//            mConsult.setVisibility(View.VISIBLE);
//            if (type != 0) {
//                statue.setText(type == 1 ? "审核中" : type == 2 ? "进行中" : "已失效");
//                statue.setTextColor(type == 1 ? Color.parseColor("#eb4f38") : type == 2 ? Color.parseColor("#008cee") : Color.parseColor("#999999"));
//                mConsult.setText(type == 1 ? "撤销发布" : type == 2 ? "更新发布" : "已失效");
//                if (type == 3)
//                    mConsult.setVisibility(View.GONE);
//            }
//        }
//        mWagesLeft.setText("面议".equals(itemTo.getSalary())?"":"¥");
//        recruitSid=itemTo.getSid();

        mWorkName.setText(itemTo.getTitle());
        mWage.setText(itemTo.getSalary());
        mWage.setText("面议".equals(itemTo.getSalary())?"面议":"¥ "+itemTo.getSalary());
        if(!TextUtils.isEmpty(itemTo.getUpdateTime()))
            if("刚刚".equals(itemTo.getUpdateTime()))
                mUpDateTime.setText(itemTo.getUpdateTime());
            else
                mUpDateTime.setText(itemTo.getUpdateTime()+"前");
        Log.i("7777", "setView: "+itemTo.getUpdateTime());
        mSmallWorkName.setText(itemTo.getJob());
        mCommand.setText("招"+itemTo.getNum()+"/"+("不限".equals(itemTo.getSex())?"不限男女":itemTo.getSex())+"/"+itemTo.getExperience()+"工作经验/"+itemTo.getEducation()+"学历/"+itemTo.getAge());
        if (!TextUtils.isEmpty(itemTo.getWelfare())){
            String[]welfareList=itemTo.getWelfare().split(",");
            for (int i=0;i<welfareList.length;i++){

                View welfareView=View.inflate(this,R.layout.welfare_text_item,null);
                TextView welfareText = (TextView) welfareView.findViewById(R.id.welfareText);
                welfareText.setText(welfareList[i]);
                GridLayout.LayoutParams layoutParams=new GridLayout.LayoutParams();
                layoutParams.setMargins(0,0,10,5);
                welfareView.setLayoutParams(layoutParams);
                mWelfare.addView(welfareView);
            }


        }else
            mTvWelfare.setVisibility(View.GONE);
        mWorkAddress.setText(itemTo.getAddress());
        mWorkDescription.setText(itemTo.getJobDesc());
        mCompanyDescription.setText(itemTo.getCompanyDesc());
        phoneNumber=itemTo.getPhone();
        phone.setText("联系电话："+itemTo.getPhone());
        companyName.setText("公司名称："+itemTo.getCompany());
    }
    private void initData() {

    }

    @OnClick({R.id.consult})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.consult:
                callShowDialog(itemTo.getPhone());
        }
    }
    private void callShowDialog(String phoneNumber) {
        final CommonDialog dialog = new CommonDialog(this,
                R.layout.dialog_call, R.style.myDialogTheme);
//        TextView title = (TextView) dialog.findViewById(R.id.title);
        TextView btnAdd = (TextView) dialog.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" +phoneNumber));
            startActivity(intent);
            dialog.dismiss();
        });
        TextView btnCancel = (TextView) dialog.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}

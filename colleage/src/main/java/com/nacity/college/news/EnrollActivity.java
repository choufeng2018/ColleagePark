package com.nacity.college.news;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.news.model.EnrollModel;
import com.nacity.college.news.presenter.EnrollPresenter;
import com.nacity.college.news.view.EnrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 *  Created by usb on 2017/12/11.
 */

public class EnrollActivity extends BaseActivity implements EnrollView {
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.address)
    EditText address;
    private EnrollModel mModel;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        setTitle(Constant.ACTIVITY_SIGN_UP);

        ButterKnife.bind(this);
        mModel = new EnrollPresenter(this);
        id= getIntent().getStringExtra("id");
        phone.setText(userInfo.getUserInfoTo().getUserMobile());
        Log.i("222", "onCreate: "+userInfo.getPhone());
        Log.i("222", "onCreate: "+userInfo.getSid());
//        Log.i("222", "onCreate: "+userInfo.getUserInfoTo().getUserMobile());
    }

    @Override
    public void enrollSuccess() {
        loadingDismiss();
        Toast.makeText(this, "报名成功", Toast.LENGTH_SHORT).show();
        Intent intent=  new Intent(this,ParkActivitiesDetailAcitivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("id", getIntent().getStringExtra("id"));
//        Log.i("2222", "setRecycleView: "+newsList.get(position).getStatus()+"--------------------"+newsList.get(position).getShowButton());
        startActivity(intent);
        finish();

    }



    @OnClick({R.id.submit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.submit:
                if(checking())
                    return;
                mModel.enrollActivity(id,name.getText().toString(),phone.getText().toString(),address.getText().toString());
                loadingShow();
                break;
        }
    }
    private boolean checking() {
        if (TextUtils.isEmpty(name.getText())) {
            Toasty.info(this, "请输入姓名").show();
            return true;
        }else if (TextUtils.isEmpty(phone.getText())) {
            Toasty.info(this, "请输入联系方式").show();
            return true;
        }
        else if (TextUtils.isEmpty(address.getText())) {
            Toasty.info(this, "请输入联系地址").show();
            return true;
        }
        return false;
    }
}

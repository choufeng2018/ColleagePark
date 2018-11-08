package com.nacity.college.property;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.college.common_libs.domain.property.ParkingApplyTo;
import com.nacity.college.R;
import com.nacity.college.databinding.ActivityParkingDetailBinding;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.base.PostImageDetailActivity;
import com.nacity.college.property.model.ParkingDetailModel;
import com.nacity.college.property.presenter.ParkingDetailPresenter;
import com.nacity.college.property.view.ParkingDetailView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 *  Created by usb on 2017/10/23.
 */

public class ParkingApplyDetailActivity extends BaseActivity implements ParkingDetailView {
    private ActivityParkingDetailBinding binding;
    private ParkingDetailModel model;
    private ParkingApplyTo               parkingApplyTo;
    @BindView(R.id.no_date)
    TextView             noDate;
    @BindView(R.id.reply_layout)
    RelativeLayout       replyLayout;
    @BindView(R.id.reply)
    TextView             reply;
    @BindView(R.id.status)
    TextView             status;
    @BindView(R.id.image_view)
    HorizontalScrollView imageView;
    @BindView(R.id.service_image)
    LinearLayout imageLayout;
    @BindView(R.id.name)
    TextView             name;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_parking_detail);
        model = new ParkingDetailPresenter(this);
        model.getParkingDetail(getIntent().getStringExtra("ParkingId"));
        loadingShow();
        setTitle(Constant.PARKING_APPLY);
        ButterKnife.bind(this);
    }

    @Override
    public void getParkingDetail(ParkingApplyTo parkingApplyTo) {
        loadingDismiss();
        this.parkingApplyTo = parkingApplyTo;
        binding.setMode(parkingApplyTo);
        setView();
    }

    private void setView() {
        if("1".equals(parkingApplyTo.getApplyStatus())){
            status.setTextColor(Color.parseColor("#ef4661"));
        }else if ("2".equals(parkingApplyTo.getApplyStatus())){
            status.setTextColor(Color.parseColor("#b4b4b4"));
        }
       /* else{
            status.setTextColor(Color.parseColor("#b4b4b4"));
        }*/
        if(TextUtils.isEmpty(parkingApplyTo.getAnswerTime())){
            replyLayout.setVisibility(View.GONE);
        }else {
            if(TextUtils.isEmpty(parkingApplyTo.getAnswerContent()))
                reply.setText("\u3000\u3000\u3000"+"暂无");
            else
                reply.setText("\u3000\u3000\u3000"+parkingApplyTo.getAnswerContent());
        }
        binding.name.setText("姓名："+parkingApplyTo.getUserName());
        binding.carNo.setText("车牌号："+parkingApplyTo.getCarNo());
        binding.applyTime.setText("申请时间："+parkingApplyTo.getCreateTime());
        if("0".equals(parkingApplyTo.getLoadType())){
            binding.applyType.setText("车位种类：其他");
        }else if("1".equals(parkingApplyTo.getLoadType())){
            binding.applyType.setText("车位种类：机械");
        } else if("2".equals(parkingApplyTo.getLoadType())){
            binding.applyType.setText("车位种类：平面");
        }
        if(TextUtils.isEmpty(parkingApplyTo.getDriverImgs())){
            noDate.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
        }else{
            imageView.setVisibility(View.VISIBLE);
            noDate.setVisibility(View.GONE);
            setImage();
        }
    }
    private void setImage() {
        int width = getScreenWidth()*145/1000;
        int margin = getScreenWidth()*2/100;
        String image=parkingApplyTo.getDriverImgs().replaceAll(",",";");
        String[] imageList = image.split(";");
        Observable.from(imageList).subscribe(imageUrl -> {
            ImageView rpImage = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
            params.leftMargin = margin;
            params.rightMargin = margin;
            Glide.with(MainApp.mContext).load(MainApp.getImagePath(imageUrl)).into(rpImage);
            rpImage.setTag(imageUrl);
//        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508341371894&di=044a21738a6217400730183f862e2c79&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201312%2F05%2F20131205172519_fSFSA.jpeg").into(rpImage);
            rpImage.setScaleType(ImageView.ScaleType.FIT_XY);
            rpImage.setOnClickListener((View v)-> {
                Intent intent = new Intent(appContext, PostImageDetailActivity.class);
                intent.putExtra("CurrentPath", (String) v.getTag());
                intent.putExtra("PathList", image);
                startActivity(intent);
            });
            imageLayout.addView(rpImage, params);
        });
    }

}

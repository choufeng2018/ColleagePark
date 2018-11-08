package com.nacity.college.rent;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.college.common_libs.domain.rent.HouseRentTo;
import com.college.common_libs.domain.rent.OnlineHousePropertyConfigTo;
import com.nacity.college.R;
import com.nacity.college.databinding.ActivityHouseDetailBinding;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.base.impl.PermissionListener;
import com.nacity.college.rent.presenter.GreenUnLimitDetailPresenter;
import com.nacity.college.rent.presenter.impl.GreenUnLimitDetailPresenterImpl;
import com.nacity.college.rent.view.GreenUnLimitDetailView;

import java.util.List;

import rx.Observable;


/**
 * Created by xzz on 2017/6/24.
 **/

public class HouseDetailActivity extends BaseActivity implements GreenUnLimitDetailView, PermissionListener {

    private ActivityHouseDetailBinding binding;
    private GreenUnLimitDetailPresenter presenter;
    private HouseRentTo houseTo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();

    }

    private void getIntentData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_house_detail);
        presenter = new GreenUnLimitDetailPresenterImpl(this);
        houseTo = (HouseRentTo) getIntent().getSerializableExtra("HouseTo");
        binding.setMode(houseTo);
        setView(houseTo);
    }

    private void setView(HouseRentTo houseTo) {
        setTitle(Constant.HOUSE_DETAIL);
        presenter.setAutoRow(binding.banner, houseTo.getHouseImages());
        binding.houseType.setVisibility(houseTo.getHouseChosen() == 1 ? View.GONE : View.VISIBLE);
        binding.lookHouse.setOnClickListener(v -> lookHouseDialog());
        if (houseTo.getConfigVos() != null && houseTo.getConfigVos().size() > 0)
            setHouseType(houseTo.getConfigVos());

    }

    private void setHouseType(List<OnlineHousePropertyConfigTo> configHouseType) {

        Observable.from(configHouseType).filter(onlineHousePropertyConfigTo -> 40==onlineHousePropertyConfigTo.getConfigType()).subscribe(houseValueTypeTo -> {
           binding.houseTypeLayout.setVisibility(View.VISIBLE);
            View view = View.inflate(appContext, R.layout.house_type_view, null);
            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(houseValueTypeTo.getConfigValue());
            binding.gridView.addView(view);
        });
        Observable.from(configHouseType).filter(onlineHousePropertyConfigTo -> 50==onlineHousePropertyConfigTo.getConfigType()).subscribe(houseValueTypeTo -> {
          binding.houseElectricLayout.setVisibility(View.VISIBLE);
            View view = View.inflate(appContext, R.layout.house_type_view, null);
            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(houseValueTypeTo.getConfigValue());
            binding.gridElectricView.addView(view);
        });
    }

    @Override
    public void setIndicator(String text) {
        binding.bannerIndicator.setText(text);

    }

    public void lookHouseDialog() {
        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(appContext);
        dialogBuilder.setContentView(R.layout.dialog_look_house);
        dialogBuilder.findViewById(R.id.confirm).setOnClickListener(v -> {
            dialogBuilder.dismiss();
            getPermission(Manifest.permission.CALL_PHONE, this);
        });
        dialogBuilder.findViewById(R.id.cancel).setOnClickListener(v -> dialogBuilder.dismiss());
        dialogBuilder.show();
        dialogBuilder.setCanceledOnTouchOutside(true);
    }

    @Override
    public void accept(String permission) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + houseTo.getHouseAppPhone()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void refuse(String permission) {

    }
}

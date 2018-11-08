package com.nacity.college.property;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bumptech.glide.Glide;
import com.college.common_libs.domain.property.ServiceTypeTo;
import com.nacity.college.R;
import com.nacity.college.databinding.HouseKeepingItemBinding;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.property.model.KeeperModel;
import com.nacity.college.property.presenter.KeeperPresenter;
import com.nacity.college.property.view.KeeperView;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by xzz on 2017/9/7.
 **/

public class HouseKeepingActivity extends BaseActivity implements KeeperView {

    @BindView(R.id.top_banner)
    ConvenientBanner topBanner;
    @BindView(R.id.grid_view)
    GridLayout gridView;
    @BindView(R.id.center_banner)
    ConvenientBanner centerBanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_keeping);
        setTitle(Constant.KEEPER_SERVICE);
        ButterKnife.bind(this);
        KeeperModel model = new KeeperPresenter(this);
        loadingShow();
        model.getKeeperCategory();
    }

    @Override
    public void getServiceTypeSuccess(List<ServiceTypeTo> serviceList) {
        loadingDismiss();
        gridView.removeAllViews();
        Observable.from(serviceList).subscribe(serviceTypeTo -> {
            View mView = View.inflate(appContext, R.layout.house_keeping_item, null);
            HouseKeepingItemBinding itemBinding = DataBindingUtil.bind(mView);
            itemBinding.setMode(serviceTypeTo);
            Glide.with(appContext).load(MainApp.getImagePath(serviceTypeTo.getImg())).into(itemBinding.serviceImage);
            mView.setOnClickListener(v -> {
                Intent intent;
                if (serviceTypeTo.getChild() != null) {
                    intent = new Intent(appContext, SelectServiceCategoryActivity.class);
                    intent.putExtra("ServiceList", (Serializable) serviceTypeTo.getChild());

                } else {
                    intent = new Intent(appContext, KeeperActivity.class);
                    intent.putExtra("ServiceList",  serviceTypeTo);
                }
                intent.putExtra("Title", Constant.KEEPER_SERVICE);
                startActivity(intent);
            });
            gridView.addView(mView);
        });
    }
}

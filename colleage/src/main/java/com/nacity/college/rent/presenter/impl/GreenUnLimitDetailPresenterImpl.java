package com.nacity.college.rent.presenter.impl;

import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.nacity.college.R;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.banner.BannerUtil;
import com.nacity.college.rent.presenter.GreenUnLimitDetailPresenter;
import com.nacity.college.rent.view.GreenUnLimitDetailView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by xzz on 2017/6/25.
 **/

public class GreenUnLimitDetailPresenterImpl extends BasePresenter implements GreenUnLimitDetailPresenter {

    private GreenUnLimitDetailView detailView;

    public GreenUnLimitDetailPresenterImpl(GreenUnLimitDetailView detailView) {
        this.detailView = detailView;
    }

    @Override
    public void setAutoRow(ConvenientBanner banner, String imagePaths) {
        List<AdvertiseTo> imageList = new ArrayList<>();
        if (!TextUtils.isEmpty(imagePaths)) {
            Observable.from(imagePaths.split(";")).subscribe(s -> {
                AdvertiseTo advertiseTo = new AdvertiseTo();
                advertiseTo.setAdImage(s);
                imageList.add(advertiseTo);
            });

            BannerUtil.setBanner(banner, imageList, R.drawable.park_rent_house_load);
            detailView.setIndicator("1/" + imageList.size());
            banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    detailView.setIndicator(position + 1 + "/" + imageList.size());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

    }
}

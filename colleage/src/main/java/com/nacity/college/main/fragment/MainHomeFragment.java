package com.nacity.college.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.college.common_libs.domain.user.MainMenuTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseFragment;
import com.nacity.college.base.Constant;
import com.nacity.college.base.banner.BannerUtil;
import com.nacity.college.guide.ParkGuideActivity;
import com.nacity.college.main.adapter.PropertyServiceBannerView;
import com.nacity.college.main.model.MainHomeModel;
import com.nacity.college.main.presenter.MainHomePresenter;
import com.nacity.college.main.view.MainHomeView;
import com.nacity.college.myself.ShareActivity;
import com.nacity.college.news.EntrepreneurshipServiceActivity;
import com.nacity.college.news.FinancingActivity;
import com.nacity.college.news.LegalServiceActivity;
import com.nacity.college.news.NewsActivity;
import com.nacity.college.news.ParkInnovateActivity;
import com.nacity.college.news.PolicyServiceActivity;
import com.nacity.college.recruit.RecruitActivity;
import com.nacity.college.rent.HouseListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xzz on 2017/8/30.
 **/

public class MainHomeFragment extends BaseFragment implements MainHomeView {
    @BindView(R.id.banner)
    ConvenientBanner<String> banner;
    @BindView(R.id.park_service_layout)
    GridLayout parkServiceLayout;
    @BindView(R.id.property_service_banner)
    ConvenientBanner<String> propertyServiceBanner;
    @BindView(R.id.service_layout)
    GridLayout serviceLayout;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_home, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        MainHomeModel model=new MainHomePresenter(this);

        setParkServiceLayout();
        setPropertyServiceLayout();
        setServiceLayout();
        return rootView;
    }


    private void setParkServiceLayout() {
        List<String>serviceList=new ArrayList<>();
        serviceList.add("园区新闻");
        serviceList.add("园区指引");
        serviceList.add("企业招聘");
        serviceList.add("创业沙龙");
        serviceList.add("租赁服务");
        serviceList.add("车位申请");
        serviceList.add("浙大创新");
        serviceList.add("法律服务");
        serviceList.add("政策信息");
        serviceList.add("创业服务");
        serviceList.add("投融资服务");

        for (int i = 0; i < 11; i++) {
            View parkServiceView = View.inflate(appContext, R.layout.park_service_item, null);
            ImageView parkServiceImage = (ImageView) parkServiceView.findViewById(R.id.park_service_image);
            TextView parkServiceName = (TextView) parkServiceView.findViewById(R.id.park_service_name);
            parkServiceName.setText(serviceList.get(i));
            parkServiceView.setTag(i);

            parkServiceView.setOnClickListener(v ->{

                switch ((int)v.getTag()){
                    case 0:
                        startActivity(new Intent(appContext, NewsActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(appContext, ParkGuideActivity.class));
                        break;
                    case 2:

                        startActivity(new Intent(appContext, RecruitActivity.class));

                        break;
                    case 3:
                        toExpectActivity(Constant.ETREPRENURAL_SALON);
                        break;
                    case 4:
                        startActivity(new Intent(appContext, HouseListActivity.class));
                        break;
                    case 5:
                        toExpectActivity(Constant.PARKING_LOT_APPLY);
                        break;
                    case 6:
                        startActivity(new Intent(appContext, ParkInnovateActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(appContext, LegalServiceActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(appContext, PolicyServiceActivity.class));
                        break;
                    case 9:
                        startActivity(new Intent(appContext, EntrepreneurshipServiceActivity.class));
                        break;
                    case 10:
                        startActivity(new Intent(appContext, FinancingActivity.class));
                        break;

                }

            });
            parkServiceLayout.addView(parkServiceView);

        }
        parkServiceLayout.setOnClickListener(v -> startActivity(new Intent(appContext, ShareActivity.class)));

    }

    private void setPropertyServiceLayout() {
        List<String> localImages = new ArrayList<>();
        PropertyServiceBannerView propertyServiceBannerView = new PropertyServiceBannerView();
        localImages.add("--");
        localImages.add("--");
        propertyServiceBanner.setMinimumHeight((int) (getScreenWidthPixels(appContext) * 0.5333));
        int[] pageIndicator = {R.drawable.wisdom_dot_un_select, R.drawable.wisdom_dot_select};
        propertyServiceBanner.setPages(
                new CBViewHolderCreator<PropertyServiceBannerView>() {
                    @Override
                    public PropertyServiceBannerView createHolder() {
                        return propertyServiceBannerView;
                    }
                }, localImages).setPageIndicator(pageIndicator);

    }


    private void setServiceLayout() {
        for (int i = 0; i < 7; i++) {
            View mView = View.inflate(appContext, R.layout.main_service_item, null);
            View lookDetail = mView.findViewById(R.id.look_detail);
            View lookInfoLayout = mView.findViewById(R.id.look_info_layout);
            if (i % 2 == 0) {
                lookDetail.setVisibility(View.VISIBLE);
                lookInfoLayout.setVisibility(View.GONE);
            } else {
                lookDetail.setVisibility(View.GONE);
                lookInfoLayout.setVisibility(View.VISIBLE);
            }
            serviceLayout.addView(mView);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //跳转到对应物业服务的activity
    private void jumpToServiceActivity(String type) {
        Intent intent;
//        switch (type) {
//            case 0:
//                intent = new Intent(appContext, RoomRepairActivity.class);
//                intent.putExtra("Type",0);
//                intent.putExtra("CategorySid","BCCF6994-9449-4E6D-9F5B-09CE08AD9353");
//                startActivity(intent);
//                goToAnimation(1);
//                break;
//            case 1:
//                intent = new Intent(appContext, PublicRepairActivity.class);
//                intent.putExtra("CategorySid","C733AA3D-32FA-4F5B-B299-061044661DC0");
//                startActivity(intent);
//                goToAnimation(1);
//                break;
//            case 2:
//                intent = new Intent(appContext, PropertyPraiseActivity.class);
//                startActivity(intent);
//                goToAnimation(1);
//                break;
//            case 3:
//                intent = new Intent(appContext, PropertySuggestActivity.class);
//                startActivity(intent);
//                goToAnimation(1);
//                break;
//            case 4:
//                intent = new Intent(appContext, PropertyComplaintActivity.class);
//                intent.putExtra("CategorySid","7D2B996C-12EC-4CD4-8499-B453E96AF11F");
//                startActivity(intent);
//                goToAnimation(1);
//                break;
//            case 5:
//                intent = new Intent(appContext, HouseKeepingActivity.class);
//                startActivity(intent);
//                goToAnimation(1);
//                break;
//            case 6:
//
//                break;
//        }


    }


    @Override
    public void getAdSuccess(List<AdvertiseTo> advertiseList) {
        BannerUtil.setBanner(banner,advertiseList,R.drawable.park_rent_house_load);
        banner.setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused}).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        banner.startTurning(5000);
    }

    @Override
    public void getParkServiceSuccess(List<MainMenuTo> data) {

    }

    @Override
    public void getPropertySuccess(List<MainMenuTo> data) {

    }

    @Override
    public void getServiceListSuccess(List<MainMenuTo> data) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (banner!=null)
            banner.stopTurning();
    }
}

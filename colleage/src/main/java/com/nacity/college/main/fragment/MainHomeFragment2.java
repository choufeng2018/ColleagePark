package com.nacity.college.main.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bumptech.glide.Glide;

import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.college.common_libs.domain.user.MainMenuTo;
import com.nacity.college.R;
import com.nacity.college.databinding.MainServiceItemBinding;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseFragment;
import com.nacity.college.base.banner.BannerUtil;
import com.nacity.college.base.info.ApartmentInfoHelper;
import com.nacity.college.base.utils.Cockroach;
import com.nacity.college.base.utils.DateUtil;
import com.nacity.college.guide.ParkGuideActivity;
import com.nacity.college.main.adapter.ParkServiceBannerView;
import com.nacity.college.main.model.MainHomeModel;
import com.nacity.college.main.presenter.MainHomePresenter;
import com.nacity.college.main.view.MainHomeView;
import com.nacity.college.news.EntrepreneurshipServiceActivity;
import com.nacity.college.news.FinancingActivity;
import com.nacity.college.news.LegalServiceActivity;
import com.nacity.college.news.NewsActivity;
import com.nacity.college.news.NewsDetailActivity;
import com.nacity.college.news.ParkActivitiesActivity;
import com.nacity.college.news.ParkInnovateActivity;
import com.nacity.college.news.PolicyServiceActivity;
import com.nacity.college.property.DecorationApplyActivity;
import com.nacity.college.property.ParkDistributionActivity;
import com.nacity.college.property.ParkingApplyActivity;
import com.nacity.college.property.PropertyComplaintActivity;
import com.nacity.college.property.PropertyPraiseActivity;
import com.nacity.college.property.PropertySuggestActivity;
import com.nacity.college.property.PublicRepairActivity;
import com.nacity.college.property.RoomRepairActivity;
import com.nacity.college.recruit.RecruitActivity;
import com.nacity.college.rent.HouseListActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;


/**
 * Created by xzz on 2017/8/30.
 **/

public class MainHomeFragment2 extends BaseFragment implements MainHomeView {
    @BindView(R.id.banner)
    ConvenientBanner<String> banner;
    @BindView(R.id.park_service_layout)
    ConvenientBanner<List<MainMenuTo>> parkServiceLayout;
    @BindView(R.id.property_service_banner)
    GridLayout propertyServiceBanner;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.service_layout)
    GridLayout serviceLayout;
    Unbinder unbinder;
    @BindView(R.id.property_service_text)
    TextView propertyServiceText;
    public MainHomeModel model = new MainHomePresenter(this);

    private List<AdvertiseTo> adList = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_home, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        model.getApartmentAd(apartmentInfo.getSid());
        model.getParkService();
        location.setText(apartmentInfo.getGardenName());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("5555", "onResume1: " + ApartmentInfoHelper.getInstance(MainApp.mContext).getGardenName());
        Log.i("5555", "onResume2-: " + apartmentInfo.getGardenName());
        location.setText(ApartmentInfoHelper.getInstance(MainApp.mContext).getGardenName());
//        model.getParkService();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private String isFirst = "111";

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i("55555", "setUserVisibleHint: " + isVisibleToUser);
        Log.i("55555", "isFirst: " + isFirst);
        if (isVisibleToUser) {
            if ("222".equals(isFirst)) {
                location.setText((ApartmentInfoHelper.getInstance(MainApp.mContext).getGardenName()));
            }
            isFirst = "222";

        }

    }

    //跳转到对应物业服务的activity
    private void jumpToServiceActivity(String type, View v) {
        Intent intent;
        switch (type) {
            case "0001":
                intent = new Intent(appContext, NewsActivity.class);
                intent.putExtra("Title", (String) v.getTag(R.id.park_service_name));
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0002":
                intent = new Intent(appContext, ParkGuideActivity.class);
                intent.putExtra("Title", (String) v.getTag(R.id.park_service_name));
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0003":
                intent = new Intent(appContext, RecruitActivity.class);
                intent.putExtra("Title", (String) v.getTag(R.id.park_service_name));
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0004":
                //创业沙龙
                intent = new Intent(appContext, ParkActivitiesActivity.class);
                startActivity(intent);
                goToAnimation(1);
//                toExpectActivity(Constant.ETREPRENURAL_SALON);
                break;
            case "0005":
                intent = new Intent(appContext, HouseListActivity.class);
                intent.putExtra("Title", (String) v.getTag(R.id.park_service_name));
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0011":
                startActivity(new Intent(appContext, ParkingApplyActivity.class));
                goToAnimation(1);
//                toExpectActivity(Constant.PARKING_LOT_APPLY);
                break;
            case "0007":
                intent = new Intent(appContext, ParkInnovateActivity.class);
                intent.putExtra("Title", (String) v.getTag(R.id.park_service_name));
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0008":
                intent = new Intent(appContext, LegalServiceActivity.class);
                intent.putExtra("Title", (String) v.getTag(R.id.park_service_name));
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0009":
                intent = new Intent(appContext, PolicyServiceActivity.class);
                intent.putExtra("Title", (String) v.getTag(R.id.park_service_name));
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0010":
                startActivity(new Intent(appContext, EntrepreneurshipServiceActivity.class));
                break;
            case "0006":
                intent = new Intent(appContext, FinancingActivity.class);
                intent.putExtra("Title", (String) v.getTag(R.id.park_service_name));
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0012":
                intent = new Intent(appContext, RoomRepairActivity.class);
                intent.putExtra("Type", 0);
                intent.putExtra("CategorySid", "0");
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0013":
                intent = new Intent(appContext, PublicRepairActivity.class);
                intent.putExtra("CategorySid", "1");
                startActivity(intent);
                goToAnimation(1);
                break;

            case "0016":

                intent = new Intent(appContext, PropertyPraiseActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;

            case "0018":
                intent = new Intent(appContext, PropertySuggestActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0017":
                intent = new Intent(appContext, PropertyComplaintActivity.class);
                intent.putExtra("CategorySid", "2");
                startActivity(intent);
                goToAnimation(1);
                break;

            case "0015":
                startActivity(new Intent(appContext, ParkDistributionActivity.class));
                break;
            case "0014":
            case "0019":
                toExpectActivity((String) v.getTag(R.id.park_service_name));
                break;
            case "0020":
                intent = new Intent(appContext, DecorationApplyActivity.class);
                intent.putExtra("CategorySid", "2");
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0021":

                break;

        }


    }


    @Override
    public void getAdSuccess(List<AdvertiseTo> advertiseList) {
        if (adList.equals(advertiseList))
            return;
        adList.clear();
        adList.addAll(advertiseList);
        BannerUtil.setBanner(banner, advertiseList, R.drawable.park_rent_house_load);
        banner.setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused}).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        banner.startTurning(5000);

    }

    @Override
    public void getParkServiceSuccess(List<MainMenuTo> data) {
        model.getPropertyService();
        if (data.size() == 0)
            return;

        List<List<MainMenuTo>> localImages = new ArrayList<>();
        for (int i = 0; i < (data.size() % 8 == 0 ? data.size() / 8 : data.size() / 8 + 1); i++) {
            List<MainMenuTo> mainMenuTos = new ArrayList<>();
            for (int j = 0; j < 8 && (i * 8 + j < data.size()); j++) {
                mainMenuTos.add(data.get(i * 8 + j));
            }
            localImages.add(mainMenuTos);
        }

        ParkServiceBannerView propertyServiceBannerView = new ParkServiceBannerView();
        parkServiceLayout.setMinimumHeight((int) (getScreenWidthPixels(appContext) * 0.48));
        int[] pageIndicator = {R.drawable.wisdom_dot_un_select, R.drawable.wisdom_dot_select};

        parkServiceLayout.setPages(() -> propertyServiceBannerView, localImages);
        if (localImages.size() > 1)
            parkServiceLayout.setPageIndicator(pageIndicator);
        else
            parkServiceLayout.setCanLoop(false);
        propertyServiceBannerView.setOnPropertyServiceOnClickListener(this::jumpToServiceActivity);

    }

    @Override
    public void getPropertySuccess(List<MainMenuTo> data) {
        model.getServiceList();
        propertyServiceText.setVisibility(View.VISIBLE);
        propertyServiceBanner.setVisibility(View.VISIBLE);
        if (data.size() == 0) {
            propertyServiceText.setVisibility(View.GONE);
            propertyServiceBanner.setVisibility(View.GONE);
            return;
        }
        propertyServiceBanner.removeAllViews();
                Observable.from(data).subscribe(mainMenuTo -> {
                    View mView = View.inflate(appContext, R.layout.main_property_service_item, null);
                    mView.setTag(mainMenuTo.getCode() + "");
                    ImageView propertyImage = (ImageView) mView.findViewById(R.id.property_image);
                    TextView propertyName = (TextView) mView.findViewById(R.id.property_text);
                    Glide.with(MainApp.mContext).load(MainApp.getImagePath(mainMenuTo.getPicUrl1())).into(propertyImage);
                    propertyName.setText(mainMenuTo.getShowName());
                    mView.setTag(R.id.park_service_name,mainMenuTo.getShowName());
                    propertyServiceBanner.addView(mView);
                    mView.setOnClickListener(view -> jumpToServiceActivity(mainMenuTo.getCode(),view));
        });



    }

    @Override
    public void getServiceListSuccess(List<MainMenuTo> data) {
        serviceLayout.removeAllViews();
        View newsView = View.inflate(appContext, R.layout.main_service_grid_item, null);
        Observable.from(data).filter(mainMenuTo ->("0001".equals(mainMenuTo.getCode()))).subscribe(mainMenuTo -> {

            View mView = View.inflate(appContext, R.layout.main_service_grid_child_item, null);
            Glide.with(appContext).load(mainMenuTo.getPicUrl()).into((ImageView) mView.findViewById(R.id.image));
            ((TextView)mView.findViewById(R.id.service_title)).setText(mainMenuTo.getTitle());
            ((TextView)mView.findViewById(R.id.service_content)).setText(mainMenuTo.getSummary());
            ((GridLayout)newsView.findViewById(R.id.grid_view)).addView(mView);
            mView.setOnClickListener(view -> {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("id", mainMenuTo.getArticleId());
                intent.putExtra("phone", mainMenuTo.getPhone());
                intent.putExtra("type", "7");
                intent.putExtra("typeOther", mainMenuTo.getArticleType());
                intent.putExtra("title", mainMenuTo.getTitle());
                startActivity(intent);
            });

        });
        if (((GridLayout)newsView.findViewById(R.id.grid_view)).getChildCount()>0)
        serviceLayout.addView(newsView);
        View innovateView = View.inflate(appContext, R.layout.main_service_grid_item, null);
        ((TextView)innovateView.findViewById(R.id.title_name)).setText("浙大创新");
        Observable.from(data).filter(mainMenuTo ->("0007".equals(mainMenuTo.getCode()))).subscribe(mainMenuTo -> {
            View mView = View.inflate(appContext, R.layout.main_service_grid_innovate_item, null);
            Glide.with(appContext).load(mainMenuTo.getPicUrl()).into((ImageView) mView.findViewById(R.id.image));
//            ((TextView)mView.findViewById(R.id.service_title)).setText(mainMenuTo.getTitle());
            ((TextView)mView.findViewById(R.id.service_title)).setText(mainMenuTo.getTitle());
            ((TextView)mView.findViewById(R.id.service_content)).setText(mainMenuTo.getSummary());
            ((GridLayout)innovateView.findViewById(R.id.grid_view)).addView(mView);
            mView.setOnClickListener(view -> {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("id", mainMenuTo.getArticleId());
                intent.putExtra("phone", mainMenuTo.getPhone());
                intent.putExtra("type", "2");
                intent.putExtra("typeOther", mainMenuTo.getArticleType());
                intent.putExtra("title", mainMenuTo.getTitle());
                startActivity(intent);
            });

        });
        if ( ((GridLayout)innovateView.findViewById(R.id.grid_view)).getChildCount()>0)
        serviceLayout.addView(innovateView);
        Observable.from(data).filter(mainMenuTo ->(!"0001".equals(mainMenuTo.getCode())&& !"0007".equals(mainMenuTo.getCode()))).subscribe(mainMenuTo -> {
            View mView = View.inflate(appContext, R.layout.main_service_item, null);
            MainServiceItemBinding bind = DataBindingUtil.bind(mView);
            bind.setMode(mainMenuTo);

            Glide.with(MainApp.mContext).load(MainApp.getImagePath(mainMenuTo.getPicUrl3())).into(bind.serviceIcon);
            Glide.with(MainApp.mContext).load(MainApp.getImagePath(mainMenuTo.getPicUrl())).into(bind.serviceImage);
            if (!TextUtils.isEmpty(mainMenuTo.getLastModTime()))
                bind.serviceTime.setText(DateUtil.formatDateString(DateUtil.mDateFormatString, mainMenuTo.getLastModTime()));
            serviceLayout.addView(mView);
            mView.setTag(R.id.park_service_type, mainMenuTo.getArticleType());
            mView.setTag(R.id.park_service_id, mainMenuTo.getArticleId());
            mView.setTag(R.id.park_service_phone, mainMenuTo.getPhone());
            mView.setTag(R.id.park_service_title, mainMenuTo.getTitle());
            mView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("id", (String) v.getTag(R.id.park_service_id));
                intent.putExtra("phone", (String) v.getTag(R.id.park_service_phone));
                intent.putExtra("type", "7");
                intent.putExtra("typeOther", (String) v.getTag(R.id.park_service_type));
                intent.putExtra("title", (String) v.getTag(R.id.park_service_title));
                startActivity(intent);
            });
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (banner != null)
            banner.stopTurning();
        Cockroach.uninstall();
    }

    public void getData() {

        model.getApartmentAd(apartmentInfo.getSid());
        model.getParkService();
    }











}

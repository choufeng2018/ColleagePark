package com.nacity.college.rent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.college.common_libs.domain.rent.HouseRentTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.banner.BannerUtil;
import com.nacity.college.base.utils.StatuBarUtil;
import com.nacity.college.rent.adapter.GreenUnLimitAdapter;
import com.nacity.college.rent.presenter.GreenUnLimitPresenter;
import com.nacity.college.rent.presenter.impl.GreenUnLimitPresenterImpl;
import com.nacity.college.rent.view.GreenUnLimitView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


/**
 * Created by xzz on 2017/6/23.
 **/

public class HouseListActivity extends BaseActivity implements GreenUnLimitView<HouseRentTo> {

    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    private List<HouseRentTo> houseList = new ArrayList<>();
    private GreenUnLimitPresenter presenter;
    private int pageIndex;
    private GreenUnLimitAdapter adapter;
    private ConvenientBanner banner;
    private TextView pageTip;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private View headView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_list);
        StatuBarUtil.setStatueBarTextBlack(getWindow());
        ButterKnife.bind(this);
        setRecycleView();
        presenter = new GreenUnLimitPresenterImpl(this);
        setTitle(getIntent().getStringExtra("Title"));
        presenter.getData(0);

        loadingShow();
    }

    private void addHeadView() {
        headView = View.inflate(appContext, R.layout.house_banner_head_view, null);
        banner = (ConvenientBanner) headView.findViewById(R.id.banner);
        pageTip = (TextView) headView.findViewById(R.id.page_tip);


    }


    public void setRecycleView() {
        addHeadView();
        adapter = new GreenUnLimitAdapter(appContext);
        adapter.setList(houseList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        lRecyclerViewAdapter.addHeaderView(headView);

        recycleView.setAdapter(lRecyclerViewAdapter);

        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);

        adapter.setOnItemClickListener((houseTo, jumpView) -> {
            ActivityOptionsCompat aop = ActivityOptionsCompat.makeSceneTransitionAnimation(HouseListActivity.this, jumpView, "HouseImage");
            Intent intent = new Intent(getApplicationContext(), HouseDetailActivity.class);
            intent.putExtra("HouseTo", houseTo);
            ActivityCompat.startActivity(HouseListActivity.this, intent, aop.toBundle());
        });

        recycleView.setOnRefreshListener(() -> {
            pageIndex = 0;
            presenter.getData(pageIndex);
        });

        recycleView.setOnLoadMoreListener(() -> {
            pageIndex++;
            presenter.getData(pageIndex);
        });
    }

    @Override
    public void refreshRecycleView(List<HouseRentTo> houseListTo) {
        loadingDismiss();
        recycleView.refreshComplete(20);
        if (pageIndex == 0)
            houseList = houseListTo;
        else
            houseList.addAll(houseListTo);
        if (houseListTo.size() < 20)
            recycleView.setNoMore(true);

        adapter.setList(houseList);
        adapter.notifyDataSetChanged();

    }


    @Override
    public void showMessage(String message) {
        Toasty.error(appContext, message).show();
    }

    @Override
    public void getAdSuccess(List<AdvertiseTo> advertiseTos) {
        BannerUtil.setBanner(banner, advertiseTos, R.drawable.park_rent_house_load);
        banner.startTurning(5000);
        pageTip.setText("1/" + advertiseTos.size());
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageTip.setText(1 + position + "/" + advertiseTos.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        lRecyclerViewAdapter.addHeaderView(headView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        banner.stopTurning();
    }
}

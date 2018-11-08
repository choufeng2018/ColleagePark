package com.nacity.college.property;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.college.common_libs.domain.property.ServiceMainTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.base.Event;
import com.nacity.college.property.adapter.PropertyServiceRecordAdapter;
import com.nacity.college.property.model.ServiceRecordModel;
import com.nacity.college.property.presenter.ServiceRecordPresenter;
import com.nacity.college.property.view.ServiceRecordView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 *  Created by usb on 2017/10/27.
 */

public class DistributionRecordActivity extends BaseActivity implements ServiceRecordView<ServiceMainTo> {
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    private int pageIndex;
    private List<ServiceMainTo> serviceRecordList = new ArrayList<>();
    private PropertyServiceRecordAdapter adapter;
    private ServiceRecordModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribution_record);
        setTitle(Constant.DISTRIBUTION_RECORD);
        ButterKnife.bind(this);
        setRecycleView();
        EventBus.getDefault().register(this);
        model = new ServiceRecordPresenter(this);
        model.getServiceRecord(0, "3");
        loadingShow();
    }
    public void setRecycleView() {
        adapter = new PropertyServiceRecordAdapter(appContext,"3");
        adapter.setList(serviceRecordList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycleView.setFooterViewColor(R.color.app_green, R.color.app_green, R.color.transparent);


        recycleView.setOnRefreshListener(() -> {
            pageIndex = 0;
            model.getServiceRecord(pageIndex, "3");
        });

        recycleView.setOnLoadMoreListener(() -> {
            pageIndex++;
            model.getServiceRecord(pageIndex, "3");
        });

        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent=new Intent(appContext, ServiceDetailActivity.class);
            intent.putExtra("ServiceId",serviceRecordList.get(position).getServiceId());
            intent.putExtra("Type","3");
            startActivity(intent);
            goToAnimation(1);
        });
    }

    @Override
    public void refreshRecycleView(List<ServiceMainTo> houseListTo) {
        loadingDismiss();
        recycleView.refreshComplete(20);
        if (pageIndex == 0)
            serviceRecordList = houseListTo;
        else
            serviceRecordList.addAll(houseListTo);
        if (houseListTo.size() < 20)
            recycleView.setNoMore(true);
        adapter.setList(serviceRecordList);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe
    public void cancelReportRefresh(Event event){
        if ("CancelReportRefresh".equals(event.getType()))
            model.getServiceRecord(0, "3");
    }
}

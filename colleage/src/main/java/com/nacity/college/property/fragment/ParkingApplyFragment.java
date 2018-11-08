package com.nacity.college.property.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.college.common_libs.domain.property.ParkingApplyListTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseFragment;
import com.nacity.college.property.ParkingApplyDetailActivity;
import com.nacity.college.property.adapter.ParkingApplyAdapter;
import com.nacity.college.property.model.ParkingApplyRecordModel;
import com.nacity.college.property.presenter.ParkingApplyRecordPresenter;
import com.nacity.college.property.view.ParkingApplyRecordView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by usb on 2017/10/23.
 */

public class ParkingApplyFragment extends BaseFragment implements ParkingApplyRecordView<ParkingApplyListTo> {
    Unbinder unbinder;
    private ParkingApplyRecordModel model;
    private ParkingApplyAdapter adapter;
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    private int pageIndex;
    private List<ParkingApplyListTo> serviceRecordList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_common_recycle_view, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        setRecycleView();
        model = new ParkingApplyRecordPresenter(this);
        model.getParkingApplyRecord(0);
        loadingShow();
        return mRootView;
    }
    public void setRecycleView() {
        adapter = new ParkingApplyAdapter(appContext);
        adapter.setList(serviceRecordList);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycleView.setFooterViewColor(R.color.app_green, R.color.app_green, R.color.transparent);


        recycleView.setOnRefreshListener(() -> {
            pageIndex = 0;
            model.getParkingApplyRecord(pageIndex);
        });

        recycleView.setOnLoadMoreListener(() -> {
            pageIndex++;
            model.getParkingApplyRecord(pageIndex);
        });

        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent=new Intent(appContext, ParkingApplyDetailActivity.class);
            intent.putExtra("ParkingId",serviceRecordList.get(position).getParkId());
            startActivity(intent);
            goToAnimation(1);
        });
    }

    @Override
    public void refreshRecycleView(List<ParkingApplyListTo> list) {
        loadingDismiss();

        recycleView.refreshComplete(20);
        if (pageIndex == 0)
            serviceRecordList = list;
        else
            serviceRecordList.addAll(list);
        if (list.size() < 20)
            recycleView.setNoMore(true);
        adapter.setList(serviceRecordList);
        adapter.notifyDataSetChanged();
    }



}
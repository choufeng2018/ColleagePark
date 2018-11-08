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
import com.college.common_libs.domain.property.DecorateApplyTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseFragment;
import com.nacity.college.property.ApplyDetailActivity;
import com.nacity.college.property.adapter.DecorateApplyAdapter;
import com.nacity.college.property.presenter.DecorateApplyRecordPresenter;
import com.nacity.college.property.view.DecorateApplyRecordView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by usb on 2017/10/17.
 */

public class DecorateApplyRecordFragment extends BaseFragment implements DecorateApplyRecordView<DecorateApplyTo> {
    Unbinder unbinder;
    private DecorateApplyRecordPresenter model;
    private DecorateApplyAdapter adapter;
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    private int pageIndex;
    private List<DecorateApplyTo> serviceRecordList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_common_recycle_view, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        setRecycleView();
        model = new DecorateApplyRecordPresenter(this);
        model.getDecorateApplyRecord(0);
        loadingShow();
        return mRootView;
    }
    public void setRecycleView() {
        adapter = new DecorateApplyAdapter(appContext);
        adapter.setList(serviceRecordList);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycleView.setFooterViewColor(R.color.app_green, R.color.app_green, R.color.transparent);


        recycleView.setOnRefreshListener(() -> {
            pageIndex = 0;
            model.getDecorateApplyRecord(pageIndex);
        });

        recycleView.setOnLoadMoreListener(() -> {
            pageIndex++;
            model.getDecorateApplyRecord(pageIndex);
        });

        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent=new Intent(appContext, ApplyDetailActivity.class);
            intent.putExtra("DecorateId",serviceRecordList.get(position).getDecorateId());
            startActivity(intent);
            goToAnimation(1);
        });
    }

    @Override
    public void refreshRecycleView(List<DecorateApplyTo> list) {
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

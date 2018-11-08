package com.nacity.college.property.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.college.common_libs.domain.property.ServiceMainTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseFragment;
import com.nacity.college.base.Event;
import com.nacity.college.property.ServiceDetailActivity;
import com.nacity.college.property.adapter.PropertyServiceRecordAdapter;
import com.nacity.college.property.model.ServiceRecordModel;
import com.nacity.college.property.presenter.ServiceRecordPresenter;
import com.nacity.college.property.view.ServiceRecordView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by xzz on 2017/9/4.
 **/

@SuppressLint("ValidFragment")
public class ServiceRecordFragment extends BaseFragment implements ServiceRecordView<ServiceMainTo> {
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    Unbinder unbinder;
    @BindView(R.id.no_more)
    TextView noMore;
    private int pageIndex;
    private List<ServiceMainTo> serviceRecordList = new ArrayList<>();
    private PropertyServiceRecordAdapter adapter;
    private ServiceRecordModel model;
    private String categorySid;
    private boolean isRegister;

    public ServiceRecordFragment(String categorySid) {
        this.categorySid = categorySid;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_common_recycle_view, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        if ("4".equals(categorySid)){
         noMore.setVisibility(View.VISIBLE);
        }else {
            setRecycleView();
            model = new ServiceRecordPresenter(this);
            model.getServiceRecord(0, categorySid);
            loadingShow();
            if (!isRegister)
            EventBus.getDefault().register(this);
            isRegister=true;
        }
        return mRootView;

    }


    public void setRecycleView() {
        adapter = new PropertyServiceRecordAdapter(appContext,categorySid);
        adapter.setList(serviceRecordList);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycleView.setFooterViewColor(R.color.app_green, R.color.app_green, R.color.transparent);


        recycleView.setOnRefreshListener(() -> {
            pageIndex = 0;
            model.getServiceRecord(pageIndex, categorySid);
        });

        recycleView.setOnLoadMoreListener(() -> {
            pageIndex++;
            model.getServiceRecord(pageIndex, categorySid);
        });

        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent=new Intent(appContext, ServiceDetailActivity.class);
            intent.putExtra("ServiceId",serviceRecordList.get(position).getServiceId());
            intent.putExtra("Type",categorySid);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void cancelReportRefresh(Event event){
        if ("CancelReportRefresh".equals(event.getType()))
            model.getServiceRecord(0,categorySid);
    }
}

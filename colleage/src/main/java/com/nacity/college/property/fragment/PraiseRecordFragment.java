package com.nacity.college.property.fragment;

import android.annotation.SuppressLint;
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
import com.college.common_libs.domain.property.FeedbackTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseFragment;
import com.nacity.college.base.BaseRecycleView;
import com.nacity.college.base.Constant;
import com.nacity.college.property.PraiseDetailActivity;
import com.nacity.college.property.adapter.PropertyPraiseRecordAdapter;
import com.nacity.college.property.presenter.PraiseRecordPresenter;
import com.nacity.college.property.view.ServiceRecordView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xzz on 2017/9/4.
 **/

@SuppressLint("ValidFragment")
public class PraiseRecordFragment extends BaseFragment implements ServiceRecordView<FeedbackTo>,BaseRecycleView<FeedbackTo> {
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    Unbinder unbinder;
    private int pageIndex;
    private List<FeedbackTo> serviceRecordList =new ArrayList<>();
    private PropertyPraiseRecordAdapter adapter;
    private PraiseRecordPresenter model;
    private int type;
    public PraiseRecordFragment(int type){
        this.type=type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_common_recycle_view, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        setRecycleView();
        model = new PraiseRecordPresenter(this);
        model.getServiceRecord(0,type);
        loadingShow();

        return mRootView;

    }




    public void setRecycleView() {
        adapter = new PropertyPraiseRecordAdapter(appContext,type);
        adapter.setList(serviceRecordList);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        LRecyclerViewAdapter lRecyclerViewAdapter=new LRecyclerViewAdapter(adapter);
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycleView.setFooterViewColor(R.color.app_green,R.color.app_green,R.color.transparent);

        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent=new Intent(appContext, PraiseDetailActivity.class);
            intent.putExtra("FeedbackId",serviceRecordList.get(position).getFeedbackId());
            intent.putExtra("Title",type==1? Constant.PRAISE_DETAIL: Constant.SUGGEST_DETAIL);
            startActivity(intent);
            goToAnimation(1);
        });

        recycleView.setOnRefreshListener(() -> {
            pageIndex=0;
            model.getServiceRecord(pageIndex,type);
        });

        recycleView.setOnLoadMoreListener(() -> {
            pageIndex++;
            model.getServiceRecord(pageIndex,type);
        });
    }

    @Override
    public void refreshRecycleView(List<FeedbackTo> houseListTo) {
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
}

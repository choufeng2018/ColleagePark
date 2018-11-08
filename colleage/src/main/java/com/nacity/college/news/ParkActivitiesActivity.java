package com.nacity.college.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.college.common_libs.domain.news.NewsTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.news.adapter.ActivityAdapter;
import com.nacity.college.news.model.ActivityModel;
import com.nacity.college.news.presenter.ActivityPresenter;
import com.nacity.college.news.view.NewsFragmentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  Created by usb on 2017/12/8.
 */

public class ParkActivitiesActivity extends BaseActivity implements NewsFragmentView {
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    private ActivityModel mActivityModel;
    private int pageIndex;
    private List<NewsTo> newsList =new ArrayList<>();
    private ActivityAdapter adapter;
    private boolean showButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_rec_common);
        setTitle(Constant.     ACTIVITY_PARK);
        ButterKnife.bind(this);
        mActivityModel = new ActivityPresenter(this);
        loadingShow();
        mActivityModel.getActivitysList(1,apartmentInfo.getSid());
        setRecycleView();

    }
    public void setRecycleView(){
        adapter = new ActivityAdapter(appContext);
        adapter.setList(newsList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent=  new Intent(ParkActivitiesActivity.this,ParkActivitiesDetailAcitivity.class);
            intent.putExtra("id",newsList.get(position).getId());
            Log.i("2222", "setRecycleView: "+newsList.get(position).getStatus()+"--------------------"+newsList.get(position).getShowButton());
            startActivity(intent);

//            if(newsList.get(position).getStatus()==2&&newsList.get(position).getShowButton()==1){
//                showButton=true;
//            }else{
//                showButton=false;
//            }
//
//            intent.putExtra("showButton",showButton);


        });
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setOnRefreshListener(() -> {
            pageIndex=1;
            mActivityModel.getActivitysList(pageIndex,apartmentInfo.getSid());

        });

        recycleView.setOnLoadMoreListener(() -> {
            pageIndex++;
            mActivityModel.getActivitysList(pageIndex,apartmentInfo.getSid());

        });


    }

    @Override
    public void getNewsList(List<NewsTo> mList) {
        loadingDismiss();
        recycleView.refreshComplete(20);
        if(mList!=null&&mList.size()>0){
            if (pageIndex == 1)
                newsList = mList;
            else
                newsList.addAll(mList);
            if (mList.size() < 20)
                recycleView.setNoMore(true);
            adapter.setList(newsList);
            adapter.notifyDataSetChanged();
        }
    }

}

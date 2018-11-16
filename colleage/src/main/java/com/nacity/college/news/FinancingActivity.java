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
import com.nacity.college.news.adapter.FinancingFragmentAdapter;
import com.nacity.college.news.adapter.NewsFragmentAdapter;
import com.nacity.college.news.model.NewsModel;
import com.nacity.college.news.presenter.NewsPresenter;
import com.nacity.college.news.view.NewsFragmentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  Created by usb on 2017/9/19. 投融资服务
 */

public class FinancingActivity extends BaseActivity implements NewsFragmentView {
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    private FinancingFragmentAdapter adapter;
    private int                 pageIndex;
    private List<NewsTo> newsList =new ArrayList<>();
    private NewsModel mNewsModel;
    private static int type = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_rec_common);
        setTitle(getIntent().getStringExtra("Title"));
        ButterKnife.bind(this);
        mNewsModel = new NewsPresenter(this);
        setRecycleView();
        loadingShow();
        mNewsModel = new NewsPresenter(this);
        mNewsModel.getNews(1,apartmentInfo.getSid(),type,null);

    }
    public void setRecycleView(){
        adapter = new FinancingFragmentAdapter(appContext);
        adapter.setList(newsList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent=  new Intent(FinancingActivity.this,NewsDetailActivity.class);
            intent.putExtra("id",newsList.get(position).getId());
            intent.putExtra("phone",newsList.get(position).getPhone());
            intent.putExtra("type","6");
            startActivity(intent);

        });
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setOnRefreshListener(() -> {
            pageIndex=1;
            mNewsModel.getNews(pageIndex,apartmentInfo.getSid(),type,null);

        });

        recycleView.setOnLoadMoreListener(() -> {
            pageIndex++;
            mNewsModel.getNews(pageIndex,apartmentInfo.getSid(),type,null);

        });


    }


    @Override
    public void getNewsList(List<NewsTo> mList) {
        loadingDismiss();
        recycleView.refreshComplete(20);
        Log.i("6666", "getNewsList: "+mList.size());
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
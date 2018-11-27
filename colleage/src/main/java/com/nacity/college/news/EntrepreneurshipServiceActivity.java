package com.nacity.college.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

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
 *  Created by usb on 2017/9/19. 创业服务
 */

public class EntrepreneurshipServiceActivity extends BaseActivity implements NewsFragmentView {
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    private NewsFragmentAdapter adapter;
    private int                 pageIndex;
    private List<NewsTo> newsList =new ArrayList<>();
    private NewsModel mNewsModel;
    //文章类型type
    private static int type = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_rec_common);
        setTitle("创业服务");
        ButterKnife.bind(this);
        mNewsModel = new NewsPresenter(this);
        setRecycleView();
        loadingShow();
        mNewsModel = new NewsPresenter(this);
        mNewsModel.getNews(1,apartmentInfo.getSid(),type,null);

    }
    public void setRecycleView(){
        adapter = new NewsFragmentAdapter(appContext);
        adapter.setList(newsList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent=  new Intent(EntrepreneurshipServiceActivity.this,NewsDetailActivity.class);
            intent.putExtra("id",newsList.get(position).getId());
            intent.putExtra("phone",newsList.get(position).getPhone());
            intent.putExtra("type","4");
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
package com.nacity.college.news.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.college.common_libs.domain.news.NewsTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseFragment;
import com.nacity.college.news.NewsDetailActivity;
import com.nacity.college.news.adapter.NewsFragmentAdapter;
import com.nacity.college.news.model.NewsModel;
import com.nacity.college.news.presenter.NewsPresenter;
import com.nacity.college.news.view.NewsFragmentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *  Created by usb on 2017/9/4.
 */
public class NewsFragment extends BaseFragment implements NewsFragmentView {
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    Unbinder unbinder;
    private NewsFragmentAdapter adapter;
    private List<NewsTo> newsList =new ArrayList<>();
    private int pageIndex=1;
    private String noticeType;
    private NewsModel mNewsModel;
    private static int type = 1;

    @SuppressLint("ValidFragment")
    public NewsFragment(){
    }
    public NewsFragment(String noticeType){
        this.noticeType=noticeType;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_news, null);
        unbinder = ButterKnife.bind(this, mView);
        setRecycleView();
        loadingShow();
        mNewsModel = new NewsPresenter(this);
        mNewsModel.getNews(1,apartmentInfo.getSid(),type,noticeType);//这个model的实现类和View的实现类应该同时出现在p里面进行数据和逻辑的交互
        return mView;
    }
    public void setRecycleView(){
        adapter = new NewsFragmentAdapter(appContext);
        adapter.setList(newsList);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=  new Intent(getActivity(),NewsDetailActivity.class);
                intent.putExtra("id",newsList.get(position).getId());
                intent.putExtra("type","1");
                intent.putExtra("noticeType",noticeType);
                startActivity(intent);

            }
        });
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setOnRefreshListener(() -> {
            pageIndex=1;
            mNewsModel.getNews(pageIndex,apartmentInfo.getSid(),type,noticeType);

        });

        recycleView.setOnLoadMoreListener(() -> {
            pageIndex++;
            mNewsModel.getNews(pageIndex,apartmentInfo.getSid(),type,noticeType);

        });


    }


    @Override
    public void getNewsList(List<NewsTo> mList) {
        loadingDismiss();
        recycleView.refreshComplete(20);
        if (mList != null && mList.size() > 0) {
            if (pageIndex == 1)
                newsList.clear();

                newsList.addAll(mList);
            if (mList.size() < 20)
                recycleView.setNoMore(true);
            adapter.setList(newsList);
            adapter.notifyDataSetChanged();
        }
    }

}

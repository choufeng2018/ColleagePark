package com.nacity.college.recruit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.college.common_libs.domain.recruit.RecruitItemTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.banner.BannerUtil;
import com.nacity.college.base.utils.StatuBarUtil;
import com.nacity.college.recruit.adapter.RecruitAdapter;
import com.nacity.college.recruit.model.RecruitModel;
import com.nacity.college.recruit.presenter.RecruitPresent;
import com.nacity.college.recruit.view.RecruitView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by usb on 2017/9/7.
 */

public class RecruitActivity extends BaseActivity implements RecruitView {
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;

    RecruitAdapter adapter;
    ConvenientBanner row;

    private RecruitModel mRecruitPresent;
    private List<RecruitItemTo> mRecruitList = new ArrayList<>();

    private int pageIndex;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit);
        StatuBarUtil.setStatueBarTextBlack(getWindow());

        ButterKnife.bind(this);
        setTitle(getIntent().getStringExtra("Title"));
        mRecruitPresent = new RecruitPresent(this);
        mRecruitPresent.getRecruitList(1, "20");
        setRecycleView();
    }

    public void setRecycleView() {

        View view= LayoutInflater.from(this).inflate(R.layout.recruit_head_view,null);

        row =  (ConvenientBanner)view.findViewById(R.id.autoRow);
//        setAutoRow();
        adapter = new RecruitAdapter(appContext);
        adapter.setList(mRecruitList);
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        lRecyclerViewAdapter.addHeaderView(view);
//        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Intent intent =new Intent(RecruitActivity.this, RecruitDetailActivity.class);
//                intent.putExtra("mode",mRecruitList.get(position));
//                startActivity(new Intent());
//            }
//        });
        recycleView.setAdapter(lRecyclerViewAdapter);

        recycleView.setOnRefreshListener(() -> {
            pageIndex = 1;
            mRecruitPresent.getRecruitList(pageIndex, "20");

        });
        recycleView.setOnLoadMoreListener(() -> {
            pageIndex++;
            mRecruitPresent.getRecruitList(pageIndex, "20");
        });

    }

    public void setAutoRow() {

//        List<String> networkImages = new ArrayList<>();
//        networkImages.add("http://t1.niutuku.com/960/1312/0816/0816-niutuku.com-12589.jpg");
//        networkImages.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3672657692,1484676976&fm=27&gp=0.jpg");
//        networkImages.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3691250280,4227974827&fm=27&gp=0.jpg");
//
//        row.setPages(BannerHolderView::new, networkImages).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
//
//        row.setOnItemClickListener(position -> {
//
//            Toast.makeText(appContext, "点击了" + position, Toast.LENGTH_SHORT).show();
//        });
    }

    @Override
    public void getNewsList(List<RecruitItemTo> mList) {
        loadingDismiss();


        recycleView.refreshComplete(20);
        if (mList != null && mList.size() > 0) {
            if (pageIndex == 1)
                mRecruitList = mList;
            else
                mRecruitList.addAll(mList);
            if (mList.size() < 20)
                recycleView.setNoMore(true);
            adapter.setList(mRecruitList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getAdSuccess(List<AdvertiseTo> advertiseList) {
        BannerUtil.setBanner(row, advertiseList,R.drawable.park_rent_house_load);

    }
}
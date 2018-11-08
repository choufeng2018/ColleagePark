package com.nacity.college.guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.college.common_libs.domain.guide.EnterpriseTo;
import com.college.common_libs.domain.recruit.RecruitItemTo;
import com.nacity.college.R;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.ExpandableTextView;
import com.nacity.college.recruit.RecruitDetailActivity;
import com.nacity.college.recruit.adapter.RecruitAdapter;
import com.nacity.college.recruit.model.RecruitModel;
import com.nacity.college.recruit.presenter.RecruitPresent;
import com.nacity.college.recruit.view.RecruitView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by usb on 2017/9/21.
 */

public class EnterpriseActivity extends BaseActivity implements RecruitView {
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    private ImageView  enterpriseImage;
    private ImageView  companyImage;
    RecruitAdapter adapter;
    private RecruitModel mRecruitPresent;
    private int                pageIndex;
    private EnterpriseTo       enterpriseTo;
    private ExpandableTextView tv;
    private RelativeLayout     recruitInfo;
    private TextView           companyName;
    private TextView           companyAddress;
    private TextView           contactPeople;
    private TextView           contactPhone;
    private List<RecruitItemTo> mNewsTo        =new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise);
        ButterKnife.bind(this);
        setRecycleView();

        initData();

        mRecruitPresent = new RecruitPresent(this);
        mRecruitPresent.getRecruitListByLicenseId(enterpriseTo.getId(), 1);


    }
    public void setRecycleView() {
        adapter = new RecruitAdapter(appContext);
        adapter.setList(mNewsTo);
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent=new Intent(EnterpriseActivity.this,RecruitDetailActivity.class);
            intent.putExtra("mode",mNewsTo.get(position));
            startActivity(intent);
        });
        View view =View.inflate(this, R.layout.head_enterprise, null);
        tv=(ExpandableTextView)view.findViewById(R.id.tv);
        enterpriseImage =(ImageView)view.findViewById(R.id.company_image);
        companyImage =(ImageView)view.findViewById(R.id.companyImage);
        companyName =(TextView)view.findViewById(R.id.company_name);
        companyAddress =(TextView)view.findViewById(R.id.company_address);
        contactPeople =(TextView)view.findViewById(R.id.contact_people);
        contactPhone =(TextView)view.findViewById(R.id.contact_phone);
        recruitInfo =(RelativeLayout) view.findViewById(R.id.recruit_info);
        lRecyclerViewAdapter.addHeaderView(view);
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setOnRefreshListener(() -> {
            pageIndex = 1;
            mRecruitPresent.getRecruitListByLicenseId(enterpriseTo.getId(), pageIndex);

        });
        recycleView.setOnLoadMoreListener(() -> {
            pageIndex++;
            mRecruitPresent.getRecruitListByLicenseId(enterpriseTo.getId(), pageIndex);
        });

    }
    private void initData() {
        setTitle("企业详情");
        enterpriseTo=(EnterpriseTo)getIntent().getSerializableExtra("mode");
        Log.i("11111", "initData: "+enterpriseTo.toString());
        Glide.with(MainApp.mContext).load(enterpriseTo.getLogoPic()).into(enterpriseImage);
        if(!TextUtils.isEmpty(enterpriseTo.getCompanyDesc()))
            tv.setText(enterpriseTo.getCompanyDesc());
        if(!TextUtils.isEmpty(enterpriseTo.getHomePic()))
            Glide.with(MainApp.mContext).load(enterpriseTo.getHomePic()).into(companyImage);

        if(!TextUtils.isEmpty(enterpriseTo.getName()))
            companyName.setText(enterpriseTo.getName());
        if(!TextUtils.isEmpty(enterpriseTo.getFullAddress()))
            companyAddress.setText(enterpriseTo.getFullAddress());
        if(!TextUtils.isEmpty(enterpriseTo.getLinkman()))
            contactPeople.setText("联系人："+enterpriseTo.getLinkman());
        if(!TextUtils.isEmpty(enterpriseTo.getLinkman()))
            contactPhone.setText("联系电话："+enterpriseTo.getPhone());
//        for (int i = 0; i <10 ; i++) {
//            RecruitItemTo newsTo=new RecruitItemTo();
//            newsTo.setCompany("客服5人-世纪新城"+i);
//            newsTo.setAddress("浙江于都网络有限科技公司"+i);
//            newsTo.setCompanyDesc("2000-500"+i+"元/月");
//            mNewsTo.add(newsTo);
//        }

//
//        View view = LayoutInflater.from(this).inflate(R.layout.head_enterprise,false);


    }

    @Override
    public void getNewsList(List<RecruitItemTo> mList) {
        loadingDismiss();


        recycleView.refreshComplete(20);
        if (mList != null && mList.size() > 0) {
            if (pageIndex == 1)
                mNewsTo = mList;
            else
                mNewsTo.addAll(mList);
            if (mList.size() < 20)
                recycleView.setNoMore(true);
            adapter.setList(mNewsTo);
            adapter.notifyDataSetChanged();
        }
        else {
            recruitInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public void getAdSuccess(List<AdvertiseTo> advertiseList) {

    }
}

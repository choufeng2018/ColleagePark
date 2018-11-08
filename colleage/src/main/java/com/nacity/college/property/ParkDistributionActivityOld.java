package com.nacity.college.property;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.college.common_libs.domain.property.ServiceTypeTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.base.DoubleUtil;
import com.nacity.college.base.banner.BannerUtil;
import com.nacity.college.property.adapter.DistributionLeftAdapter;
import com.nacity.college.property.adapter.DistributionRightOtherAdapter;
import com.nacity.college.property.presenter.DistributionPresent;
import com.nacity.college.property.view.DistributionView;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *  Created by usb on 2017/10/26.
 */

public class ParkDistributionActivityOld extends BaseActivity implements DistributionView {
    @BindView(R.id.price_left)
    TextView    priceLeftTv;
    @BindView(R.id.price_right)
    TextView priceRightTv;
    @BindView(R.id.tv_count)
    TextView tvCount;
    private int count;//总数

    private double total;    //总价

    private LRecyclerView recycleViewLeft;

    private LRecyclerView                 recycleViewRight;
    private DistributionPresent model;
    private DistributionLeftAdapter adapterLeft;
    private DistributionRightOtherAdapter adapterRight;
    private List<ServiceTypeTo> listLeft = new ArrayList<>();
    private List<ServiceTypeTo> listRight = new ArrayList<>();
    private List<ServiceTypeTo> listMode = new ArrayList<>();
    @BindView(R.id.row)
    ConvenientBanner row;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribution);
        ButterKnife.bind(this);
        setTitle(Constant.PARK_DISTRIBUTION);
       model = new DistributionPresent(this);
      loadingShow();
        initDate();
      setRecycleView();

    }

    private void setRecycleView() {
        tvCount.setText("共"+count+"件商品");

        adapterLeft = new DistributionLeftAdapter(appContext);
        adapterLeft.setList(listLeft);
//        recycleViewLeft.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleViewLeft.setLayoutManager(linearLayoutManager);
        LRecyclerViewAdapter lRecyclerViewAdapterLeft = new LRecyclerViewAdapter(adapterLeft);

        recycleViewLeft.setAdapter(lRecyclerViewAdapterLeft);
        recycleViewLeft.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycleViewLeft.setFooterViewColor(R.color.app_green,R.color.app_green,R.color.transparent);

        recycleViewRight.setLayoutManager(new LinearLayoutManager(this));

        recycleViewRight.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycleViewRight.setFooterViewColor(R.color.app_green,R.color.app_green,R.color.transparent);
        model.getWaterCategory();
        recycleViewRight.setPullRefreshEnabled(false);
        recycleViewLeft.setPullRefreshEnabled(false);
        adapterLeft.setOnItemClickListener(new DistributionLeftAdapter.OnItemClickListener() {
            @Override
            public void itemClick(ServiceTypeTo mode, int position) {
                listLeft.get(position).setChangeColor("1");

                adapterLeft.notifyDataSetChanged();
//                adapterRight.setSelection();

            }
        });
//        lRecyclerViewAdapterLeft.setOnItemClickListener((view, position) -> {
//            adapterRight.setSelection(position);
//            });


        }


    @Override
    public void getServiceTypeSuccess(List<ServiceTypeTo> serviceList) {

//      recycleViewRight.addItemDecoration(new TitleItemDecoration(this, serviceList));
        Log.i("2222", "serviceList: " + serviceList.toString());
        serviceList.get(0).setChangeColor("1");
        loadingDismiss();
        listLeft = serviceList;
        for (ServiceTypeTo serviceTo : listLeft) {
            listRight.addAll(serviceTo.getChild());
        }
        adapterRight = new DistributionRightOtherAdapter(appContext,serviceList);
        LRecyclerViewAdapter lRecyclerViewAdapterRight = new LRecyclerViewAdapter(adapterRight);
        recycleViewRight.setAdapter(lRecyclerViewAdapterRight);
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapterRight);
        recycleViewRight.addItemDecoration(headersDecor);
        adapterRight.setOnItemIncreaseClickListener((mode, position) -> {
            listRight.get(position).setClick(listRight.get(position).getClick()+1);
            count=count+1;
            tvCount.setText("共"+count+"件商品");
            total= DoubleUtil.add(total,mode.getUnitPrice());
            String price=Double.toString(total);
            String [] priceList=price.split("\\.");
            priceLeftTv.setText(priceList[0]+".");
            priceRightTv.setText(priceList[1]+"0");

        });
        adapterRight.setOnItemReduceClickListener((mode, position) -> {
            listRight.get(position).setClick(listRight.get(position).getClick()-1);
            count=count-1;
            tvCount.setText("共"+count+"件商品");
            total= DoubleUtil.reduce(total,mode.getUnitPrice());
            String price=Double.toString(total);
            String [] priceList=price.split("\\.");
            priceLeftTv.setText(priceList[0]+".");
            priceRightTv.setText(priceList[1]+"0");
//            adapterRight.notifyDataSetChanged();
        });
        adapterLeft.setList(listLeft);
        adapterLeft.notifyDataSetChanged();
//        adapterRight.setCategoryList(serviceList);
//        adapterRight.notifyDataSetChanged();

    }

    @Override
    public void getAdSuccess(List<AdvertiseTo> advertiseList) {
        BannerUtil.setBanner(row, advertiseList,R.drawable.park_rent_house_load);
    }
    @OnClick({R.id.record,R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.record:
                //申请
                Intent intent = new Intent(appContext, DistributionRecordActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.tv_submit:
                //申请
                listMode.clear();
                for (ServiceTypeTo i:listRight){
                    if(i.getClick()>0){
                        listMode.add(i) ;
                    }
                }
                if(listMode.size()==0){
                    Toast.makeText(this,"请选择商品",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent1 = new Intent(appContext, ParkDistributionOrderActivity.class);
                intent1.putExtra("mode", (Serializable)listMode);
                startActivity(intent1);
                goToAnimation(1);
                break;
        }
    }
    private void initDate() {
        recycleViewLeft = (LRecyclerView) findViewById(R.id.recycleView_left);
        recycleViewRight = (LRecyclerView) findViewById(R.id.recycleView_right);
    }
}

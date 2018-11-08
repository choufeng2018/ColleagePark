package com.nacity.college.property;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.college.common_libs.domain.property.ServiceTypeTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.base.DoubleUtil;
import com.nacity.college.base.banner.BannerUtil;
import com.nacity.college.base.utils.StatuBarUtil;
import com.nacity.college.property.adapter.CategoryAdapter;
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
import rx.Observable;

/**
 *  Created by usb on 2017/10/26.
 */

public class ParkDistributionActivity extends BaseActivity implements DistributionView, CategoryAdapter.OnItemClickListener {
    @BindView(R.id.price_left)
    TextView priceLeftTv;
    @BindView(R.id.price_right)
    TextView priceRightTv;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.recycleView_left)
    RecyclerView recyclerviewCategory;
    @BindView(R.id.recycleView_right)
    RecyclerView recyclerviewTeams;
    private int count;//总数

    private double total;    //总价
    private boolean isclick;    //总价
    private int clickPositon;    //总价


    private DistributionPresent model;

   private List<ServiceTypeTo> listLeft = new ArrayList<>();
    private List<ServiceTypeTo> listRight = new ArrayList<>();
    private List<ServiceTypeTo> Right = new ArrayList<>();
    private List<ServiceTypeTo> listMode = new ArrayList<>();
    private LinearLayoutManager mTeamsLayoutManager;
    private LinearLayoutManager mCategoryLayoutManager;
    private CategoryAdapter categoryAdapter;
    private int oldSelectedPosition = 0;
    //private TeamsAndHeaderAdapter teamsAndHeaderAdapter;
    private DistributionRightOtherAdapter rightOtherAdapter;
    @BindView(R.id.row)
    ConvenientBanner row;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribution);
        StatuBarUtil.setStatueBarTextBlack(getWindow());

        ButterKnife.bind(this);
        setTitle(Constant.PARK_DISTRIBUTION);
        model = new DistributionPresent(this);
        model.getWaterCategory();
        loadingShow();


    }


    @Override
    public void getServiceTypeSuccess(List<ServiceTypeTo> serviceList) {
        loadingDismiss();
        Observable.from(serviceList).filter(serviceTypeTo -> {
            return serviceTypeTo.getChild() != null && serviceTypeTo.getChild().size() > 0;
        }).subscribe(serviceTypeTo -> {
            listRight.add(serviceTypeTo);
        });
//        listRight = serviceList;
        Right.clear();
        for (ServiceTypeTo i:listRight) {
            Right.addAll(i.getChild());
        }
        Log.i("666", "listRight: "+listRight.toString());
        mTeamsLayoutManager = new LinearLayoutManager(this);
        mCategoryLayoutManager = new LinearLayoutManager(this);
        recyclerviewCategory.setLayoutManager(mCategoryLayoutManager);
        recyclerviewTeams.setLayoutManager(mTeamsLayoutManager);
        categoryAdapter = new CategoryAdapter(this, listRight);
        categoryAdapter.setOnItemClickListener(this);
        recyclerviewCategory.setAdapter(categoryAdapter);
        rightOtherAdapter = new DistributionRightOtherAdapter(this, listRight);
        recyclerviewTeams.setAdapter(rightOtherAdapter);
        rightOtherAdapter.setOnItemIncreaseClickListener((mode, position) -> {
            Right.get(position).setClick(Right.get(position).getClick()+1);
            count=count+1;
            tvCount.setText("共"+count+"件商品");
            total= DoubleUtil.add(total,mode.getUnitPrice());
            String price=Double.toString(total);
            String [] priceList=price.split("\\.");
            priceLeftTv.setText(priceList[0]+".");
            priceRightTv.setText(priceList[1]+"0");

        });
        rightOtherAdapter.setOnItemReduceClickListener((mode, position) -> {
            Right.get(position).setClick(Right.get(position).getClick()-1);
            count=count-1;
            tvCount.setText("共"+count+"件商品");
            total= DoubleUtil.reduce(total,mode.getUnitPrice());
            String price=Double.toString(total);
            String [] priceList=price.split("\\.");
            priceLeftTv.setText(priceList[0]+".");
            priceRightTv.setText(priceList[1]+"0");
//            adapterRight.notifyDataSetChanged();
        });
        // Add the sticky headers decoration,给球队添加标题
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(rightOtherAdapter);
        recyclerviewTeams.addItemDecoration(headersDecor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerviewTeams.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (needMove) {
                        needMove = false;
                        //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                        int n = movePosition - mTeamsLayoutManager.findFirstVisibleItemPosition();
                        if (0 <= n && n < recyclerviewTeams.getChildCount()) {
                            //获取要置顶的项顶部离RecyclerView顶部的距离
                            int top = recyclerviewTeams.getChildAt(n).getTop() - dip2px(appContext, 28);
                            //最后的移动
                            recyclerviewTeams.scrollBy(0, top);
                        }
                    }
                    //第一个完全显示的item和最后一个item。
                    if (!isChangeByCategoryClick) {
                        int firstVisibleItem = mTeamsLayoutManager.findFirstCompletelyVisibleItemPosition();
                        int sort = rightOtherAdapter.getSortType((firstVisibleItem==0||isclick)?firstVisibleItem:firstVisibleItem-1);
                        changeSelected(sort);
                        isclick=false;
                    } else {
                        isChangeByCategoryClick = false;
                    }
                }
            });
        } else {
            recyclerviewTeams.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (needMove) {
                        needMove = false;
                        //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                        int n = movePosition - mTeamsLayoutManager.findFirstVisibleItemPosition();
                        if (0 <= n && n < recyclerviewTeams.getChildCount()) {
                            //获取要置顶的项顶部离RecyclerView顶部的距离
                            int top = recyclerviewTeams.getChildAt(n).getTop() - dip2px(appContext, 28);
                            //最后的移动
                            recyclerviewTeams.scrollBy(0, top);
                        }
                    }
                    //第一个完全显示的item和最后一个item。
                    if (!isChangeByCategoryClick) {
                        int firstVisibleItem = mTeamsLayoutManager.findFirstCompletelyVisibleItemPosition();
                        int sort = rightOtherAdapter.getSortType((firstVisibleItem==0||isclick)?firstVisibleItem:firstVisibleItem-1);
                        changeSelected(sort);
                        isclick=false;

                    } else {
                        isChangeByCategoryClick = false;
                    }
                }
            });
        }
        categoryAdapter.notifyDataSetChanged();
        rightOtherAdapter.notifyDataSetChanged();

    }

    @Override
    public void getAdSuccess(List<AdvertiseTo> advertiseList) {
        BannerUtil.setBanner(row, advertiseList, R.drawable.park_rent_house_load);
        row.setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused}).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        row.startTurning(3000);
    }

    @OnClick({R.id.record, R.id.tv_submit})
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
                for (ServiceTypeTo i : Right) {
                    if (i.getClick() > 0) {
                        listMode.add(i);
                    }
                }
                if (listMode.size() == 0) {
                    Toast.makeText(this, "请选择商品", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent1 = new Intent(appContext, ParkDistributionOrderActivity.class);
                intent1.putExtra("mode", (Serializable) listMode);
                startActivity(intent1);
                goToAnimation(1);
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        clickPositon=position;
        isclick=true;
        changeSelected(position);
        moveToThisSortFirstItem(position);
        isChangeByCategoryClick = true;
    }

    private boolean isChangeByCategoryClick = false;
    private boolean needMove = false;
    private int movePosition;

    private void changeSelected(int position) {
        listRight.get(oldSelectedPosition).setSeleted(false);
        listRight.get(isclick?clickPositon:position).setSeleted(true);
        //增加左侧联动
        recyclerviewCategory.scrollToPosition(position);
        oldSelectedPosition = isclick?clickPositon:position;
        categoryAdapter.notifyDataSetChanged();
    }

    private void moveToThisSortFirstItem(int position) {
        movePosition = 0;
        for (int i = 0; i < position; i++) {
            movePosition += rightOtherAdapter.getCategoryList().get(i).getChild().size();
        }
        moveToPosition(movePosition);
    }

    private void moveToPosition(int n) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = mTeamsLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mTeamsLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            recyclerviewTeams.scrollToPosition(n);
        } else if (n <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            int top = recyclerviewTeams.getChildAt(n - firstItem).getTop();
            recyclerviewTeams.scrollBy(0, top - dip2px(this, 28));
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            recyclerviewTeams.scrollToPosition(n);
            movePosition = n;
            needMove = true;
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

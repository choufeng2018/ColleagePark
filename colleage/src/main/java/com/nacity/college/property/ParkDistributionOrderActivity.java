package com.nacity.college.property;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.college.common_libs.domain.property.ServiceTypeTo;
import com.college.common_libs.domain.property.WaterDistributionTo;
import com.nacity.college.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.CommonDialog;
import com.nacity.college.base.Constant;
import com.nacity.college.base.DoubleUtil;
import com.nacity.college.base.Event;
import com.nacity.college.base.utils.wheelview.WheelView;
import com.nacity.college.base.utils.wheelview.adapter.ArrayWheelAdapter;
import com.nacity.college.property.model.DistributionOrderModel;
import com.nacity.college.property.presenter.DistributionOrderPresenter;
import com.nacity.college.property.view.DistributionOrderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 *  Created by usb on 2017/10/30.
 */

public class ParkDistributionOrderActivity extends BaseActivity implements DistributionOrderView {
    @BindView(R.id.phone_number)
    TextView   phoneNumber;
    @BindView(R.id.price_left)
    TextView   priceLeftTv;
    @BindView(R.id.price_right)
    TextView   priceRightTv;
    @BindView(R.id.tv_count)
    TextView   tvCount;
    @BindView(R.id.grid_view)
    GridLayout gridView;
    @BindView(R.id.service_description)
    EditText   serviceDescription;
    @BindView(R.id.contact)
    TextView   contact;
    @BindView(R.id.repair_address)
    TextView   repairAddress;
    @BindView(R.id.repair_time)
    TextView   repairTime;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private DistributionOrderModel model;
    private List<ServiceTypeTo> mode = new ArrayList<>();
    private int count;//总数
    private double total;    //总价
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribution_order);
        ButterKnife.bind(this);
        setTitle(Constant.PARK_ORDER);
        model = new DistributionOrderPresenter(this);
        EventBus.getDefault().register(this);
//        model = new DistributionPresent(this);
//        loadingShow();
//        initDate();
//        setRecycleView();
        initView();
    }
    private void initView() {
        phoneNumber.setText(userInfo.getUserInfoTo().getUserMobile());
        repairAddress.setText(userInfo.getUserInfoTo().getRoomNo());
        contact.setText(userInfo.getUserInfoTo().getRealName());
        mode=  (List<ServiceTypeTo>)getIntent().getSerializableExtra("mode");
        Log.i("2222", "mode: "+mode.size());
        Log.i("2222", "getUserMobile: "+userInfo.getUserInfoTo().getUserMobile());
        Log.i("2222", "getRoomNo: "+userInfo.getUserInfoTo().getRoomNo());
        Log.i("2222", "getName: "+userInfo.getUserInfoTo().getName());
        Log.i("2222", "getName: "+userInfo.getUserInfoTo().toString());

        for (int i = 0; i <mode.size() ; i++) {
            total= DoubleUtil.add(total, DoubleUtil.mul(mode.get(i).getClick(),mode.get(i).getUnitPrice()));
            View view = LayoutInflater.from(this).inflate(R.layout.item_order_disrisbution, null, false);
            RoundedImageView image=(RoundedImageView)view.findViewById(R.id.water_image);
            TextView name=(TextView)view.findViewById(R.id.name);
            TextView explain=(TextView)view.findViewById(R.id.explain);
            TextView priceLeft=(TextView)view.findViewById(R.id.price_left);
            TextView priceRight=(TextView)view.findViewById(R.id.price_right);
            TextView goodsNumber=(TextView)view.findViewById(R.id.goods_number);
            ImageView increase=(ImageView)view.findViewById(R.id.increase);
            ImageView reduce=(ImageView)view.findViewById(R.id.reduce);
            int num=mode.get(i).getClick();
            count=count+num;
            view.setTag(mode.get(i));
            goodsNumber.setText(num+"");
            increase.setTag(mode.get(i));
            reduce.setTag(mode.get(i));
            increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int number= Integer.parseInt(goodsNumber.getText().toString())+1;
                    count=count+1;
                    tvCount.setText("共"+count+"件商品");
                    ServiceTypeTo item=(ServiceTypeTo)v.getTag();
                    item.setClick(number);
                    goodsNumber.setText(number+"");
                    total= DoubleUtil.add(total,item.getUnitPrice());
                    String price=Double.toString(total);
                    String [] priceList=price.split("\\.");
                    priceLeftTv.setText(priceList[0]+".");
                    priceRightTv.setText(priceList[1]+"0");

                }
            });
            reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int number= Integer.parseInt(goodsNumber.getText().toString())-1;
                    ServiceTypeTo item=(ServiceTypeTo)v.getTag();
                    if(Integer.parseInt(goodsNumber.getText().toString())>1){
                        goodsNumber.setText(number+"");
                        count=count-1;
                        tvCount.setText("共"+count+"件商品");
                        item.setClick(number);
//                        mode.get((int)v.getTag()).setClick(number);
//                        total=total-mode.get((int)v.getTag()).getUnitPrice();
                        total= DoubleUtil.reduce(total,item.getUnitPrice());
                        String price=Double.toString(total);
                        String [] priceList=price.split("\\.");
                        priceLeftTv.setText(priceList[0]+".");
                        priceRightTv.setText(priceList[1]+"0");
                    }else{
                dialogDeleteShow(view,item);
                    }
                }
            });
            name.setText(mode.get(i).getName());
            explain.setText(mode.get(i).getContent());
            String price=Double.toString(mode.get(i).getUnitPrice());
            String [] priceList=price.split("\\.");
            priceLeft.setText(priceList[0]+".");
            priceRight.setText(priceList[1]+"0");
            if(mode.get(i).getImg()!=null)
               Glide.with(MainApp.mContext).load(mode.get(i).getImg()).into(image);
            else
                Glide.with(MainApp.mContext).load(R.drawable.distribution_load).into(image);


            gridView.addView(view);
        }
        tvCount.setText("共"+count+"件商品");
        String price=Double.toString(total);
        String [] priceList=price.split("\\.");
        priceLeftTv.setText(priceList[0]+".");
        priceRightTv.setText(priceList[1]+"0");
        scrollView.smoothScrollTo(0,20);

    }

    private void dialogDeleteShow(View view,ServiceTypeTo item) {
        final CommonDialog dialog = new CommonDialog(this, R.layout.dailog_delete_goods, R.style.myDialogTheme);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        Button confirm = (Button) dialog.findViewById(R.id.confirm);
        confirm.setOnClickListener(v -> {
            dialog.dismiss();
            gridView.removeView(view);
//            total=total-i;
            total= DoubleUtil.reduce(total,item.getUnitPrice());
        mode.remove(item);
            String price=Double.toString(total);
            String [] priceList=price.split("\\.");
            priceLeftTv.setText(priceList[0]+".");
            priceRightTv.setText(priceList[1]+"0");
            count=count-1;
            tvCount.setText("共"+count+"件商品");
            if(gridView.getChildCount()==0){
                finish();
                goToAnimation(2);
            }
        });
        cancel.setOnClickListener(v ->
            dialog.dismiss()
        );
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @OnClick({R.id.contact_phone_layout, R.id.contact_layout, R.id.repair_time_layout,R.id.repair_address_layout,R.id.tv_submit})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.contact_phone_layout:
                intent = new Intent(appContext, ChangeContactAndPhoneActivity.class);
                intent.putExtra("Title", Constant.MODIFY_CONTACT_PHONE);
                intent.putExtra("content", phoneNumber.getText().toString());
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.contact_layout:
                intent = new Intent(appContext, ChangeContactAndPhoneActivity.class);
                intent.putExtra("Title", Constant.MODIFY_CONTACT);
                intent.putExtra("Type", 1);
                intent.putExtra("content", contact.getText().toString());
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.repair_time_layout:
                model.getServiceTime(1);
                loadingShow();
                break;
            case R.id.repair_address_layout:
                intent = new Intent(appContext, ChangeContactAndPhoneActivity.class);
                intent.putExtra("Title", Constant.MODIFY_ADDRESS);
                intent.putExtra("Type", 2);
                startActivity(intent);
                goToAnimation(1);
                break;

             case R.id.tv_submit:
                 if (!check())
                     return;
                 List<WaterDistributionTo> waterDistributionList = new ArrayList<>();
                 for (ServiceTypeTo item:mode){
                     WaterDistributionTo waterDistributionTo=new WaterDistributionTo();
                     waterDistributionTo.setServiceTypeId(item.getId());
                     waterDistributionTo.setWaterSeviceNum(item.getClick());
                     waterDistributionList.add(waterDistributionTo);
                 }
                 Log.i("111", "mode: "+mode.toString());
                 Log.i("111", "waterDistributionList: "+waterDistributionList.toString());
                 model.submit(mode.get(0), count+"", contact.getText().toString(), phoneNumber.getText().toString(), repairTime.getText().toString(), serviceDescription.getText().toString(), repairAddress.getText().toString(), waterDistributionList);
                 break;

        }
    }
    //检查提交条件是否完善
    public Boolean check() {

        if (TextUtils.isEmpty(contact.getText().toString())) {
            showMessage(Constant.PLEASE_INPUT_CONTACT);
            return false;
        }
        if (TextUtils.isEmpty(repairAddress.getText().toString())) {
            showMessage("请填写送货地址");
            return false;
        }
        if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
            showMessage("请填写联系电话");
            return false;
        }
        if (TextUtils.isEmpty(repairTime.getText().toString())) {
            showMessage("请选择送货时间");
            return false;
        }
//        if (TextUtils.isEmpty(serviceContent.getText().toString())) {
//            showMessage(Constant.PLEASE_INPUT_SERVICE_CONTENT);
//            return false;
//        }

        return true;
    }

    @Override
    public void setServiceTime(String[] dayList, String[] dateList, String[][] timeList) {
        loadingDismiss();
        showSelectDateTimeDialog(dayList, dateList, timeList);
    }

    @Override
    public void submitSuccess(Boolean mainTo) {
        loadingDismiss();
        final CommonDialog dialog = new CommonDialog(this, R.layout.dailog_decoratian_input, R.style.myDialogTheme);
        Button btnAdd = (Button) dialog.findViewById(R.id.confirm);
        TextView title = (TextView) dialog.findViewById(R.id.title);
        title.setText("你的服务已提交");
        btnAdd.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(this, ParkDistributionActivity.class);
           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             startActivity(intent);
            finish();
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    //选择时间的弹框

    @SuppressLint("SetTextI18n")
    private void showSelectDateTimeDialog(final String[] left, final String[] dateList, final String[][] right) {
        final CommonDialog dialog = new CommonDialog(this, R.layout.dialog_wheel_time, R.style.myDialogTheme);

        Button btnAdd = (Button) dialog.findViewById(R.id.btn_add);
        RelativeLayout mLayout = (RelativeLayout) dialog.findViewById(R.id.layout);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        final WheelView wheelLeft = (WheelView) dialog.findViewById(R.id.wheelLeft);
        final WheelView wheelRight = (WheelView) dialog.findViewById(R.id.wheelRight);
        mLayout.setOnClickListener(v -> dialog.dismiss());
        btnAdd.setOnClickListener(v -> {
            int leftPosition = wheelLeft.getCurrentItem();
            String vLeft = left[leftPosition];
            String vRight = right[leftPosition][wheelRight.getCurrentItem()];

            repairTime.setText(vLeft + vRight);
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        wheelLeft.setVisibleItems(5);
        wheelLeft.setCyclic(false);
        wheelLeft.setViewAdapter(new ArrayWheelAdapter<>(appContext, left, 15));
        String mLeftPosition = "1";
        setWheelCurrent(mLeftPosition, wheelLeft, left);
        wheelRight.setVisibleItems(5);
        wheelRight.setCyclic(false);
        wheelRight.setViewAdapter(new ArrayWheelAdapter<>(appContext, right[1], 15));
        final String mRightPosition = "0";

        setWheelCurrent(mRightPosition, wheelRight, right[0]);

        LinearLayout.LayoutParams paramsLeft = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1);
        paramsLeft.gravity = Gravity.START;
        LinearLayout.LayoutParams paramsRight = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1);
        paramsRight.gravity = Gravity.END;
        wheelLeft.addChangingListener((wheel, oldValue, newValue) -> {
            wheelRight.setViewAdapter(new ArrayWheelAdapter<>(appContext, right[newValue]));
            if (!TextUtils.isEmpty(mRightPosition)) {
                int position = Integer.parseInt(mRightPosition);
                if (position > right[newValue].length) {
                    wheelRight.setCurrentItem(0);
                }

            }


        });


        if (dialog.isShowing()) {
            dialog.dismiss();
        }


        dialog.show();
    }
    private void setWheelCurrent(String rightPosition, WheelView wheelView, String[] right) {
        if (TextUtils.isEmpty(rightPosition)) {
            wheelView.setCurrentItem(0);
        } else {
            int position = Integer.parseInt(rightPosition);
            if (position <= right.length) {
                wheelView.setCurrentItem(position);
            } else {
                wheelView.setCurrentItem(0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    //获取联系人或地址修改信息
    @Subscribe
    public void getChangeInfo(Event<String> event) {
        if ("ModifyContact".equals(event.getType())) {

            contact.setText(event.getMode());

        } else if ("ModifyPhone".equals(event.getType())) {

            phoneNumber.setText(event.getMode());
        }
        else if ("ModifyRepairAddress".equals(event.getType())) {
            repairAddress.setText(event.getMode());
        }
    }

}

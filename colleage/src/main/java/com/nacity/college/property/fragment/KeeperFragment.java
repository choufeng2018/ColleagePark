package com.nacity.college.property.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.college.common_libs.domain.property.ServiceTypeTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseFragment;
import com.nacity.college.base.CommonDialog;
import com.nacity.college.base.Constant;
import com.nacity.college.base.Event;
import com.nacity.college.base.utils.wheelview.WheelView;
import com.nacity.college.base.utils.wheelview.adapter.ArrayWheelAdapter;
import com.nacity.college.property.ChangeContactAndPhoneActivity;
import com.nacity.college.property.HouseKeepingActivity;
import com.nacity.college.property.model.RepairModel;
import com.nacity.college.property.presenter.RepairPresenter;
import com.nacity.college.property.view.RepairView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by xzz on 2017/9/4.
 **/

public class KeeperFragment extends BaseFragment implements RepairView {
    @BindView(R.id.service_icon)
    ImageView serviceIcon;
    @BindView(R.id.service_name)
    TextView serviceName;
    @BindView(R.id.phone_number)
    TextView phoneNumber;
    @BindView(R.id.repair_address)
    TextView repairAddress;
    @BindView(R.id.repair_time)
    TextView repairTime;
    @BindView(R.id.number_add)
    View numberAdd;
    @BindView(R.id.service_number)
    TextView serviceNumber;
    @BindView(R.id.number_reduce)
    View numberReduce;
    @BindView(R.id.service_description)
    EditText serviceDescription;
    @BindView(R.id.submit)
    TextView submit;
    Unbinder unbinder;
    @BindView(R.id.pay_money)
    TextView payMoney;
    @BindView(R.id.service_content)
    TextView serviceContent;
    @BindView(R.id.contact)
    TextView contact;
    @BindView(R.id.service_type_name)
    TextView serviceTypeName;
    @BindView(R.id.service_type_unit)
    TextView serviceTypeUnit;
    private RepairModel model;
    private String mChoseDate = "";
    private ServiceTypeTo selectServiceType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_keeper, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        initView();
        EventBus.getDefault().register(this);
        model = new RepairPresenter(this);
        return mRootView;
    }

    private void initView() {
        selectServiceType= (ServiceTypeTo) getActivity().getIntent().getSerializableExtra("ServiceList");
        phoneNumber.setText(userInfo.getPhone());
        contact.setText(userInfo.getUserInfoTo().getName());
        repairAddress.setText(userInfo.getUserInfoTo().getNo());
//        serviceContent.setText(selectServiceType.getTypeDescription());
//        serviceName.setText(selectServiceType.getTypeName());
//        serviceTypeUnit.setText(selectServiceType.getTypeQtyUnit());
//        serviceTypeName.setText(selectServiceType.getTypeQtyTitle());
//        serviceNumber.setText(selectServiceType.getTypeQtyDefault()+"");


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.submit, R.id.contact_phone_layout, R.id.contact_layout, R.id.repair_time_layout, R.id.service_category_layout})
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
            case R.id.service_category_layout:
                intent = new Intent(appContext, HouseKeepingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.submit:
                model.getUpImageToken();
                break;
        }
    }



    //获取服务种类
    @Subscribe
    public void getServiceCategory(Event<ServiceTypeTo> event) {
        if ("ServiceCategory".equals(event.getType())) {
            selectServiceType = event.getMode();
//            serviceName.setText(event.getMode().getTypeName());
//            serviceContent.setText(event.getMode().getTypeDescription());
//            Glide.with(MainApp.mContext).load(MainApp.getImagePath(event.getMode().getTypeImage())).into(serviceIcon);
        }
    }

    //获取联系人或地址修改信息
    @Subscribe
    public void getChangeInfo(Event<String> event) {
        if ("ModifyContact".equals(event.getType())) {

            contact.setText(event.getMode());

        } else if ("ModifyPhone".equals(event.getType())) {

            phoneNumber.setText(event.getMode());
        }
    }

//选择时间的弹框

    @SuppressLint("SetTextI18n")
    private void showSelectDateTimeDialog(final String[] left, final String[] dateList, final String[][] right) {

        final CommonDialog dialog = new CommonDialog(getActivity(), R.layout.dialog_wheel_time, R.style.myDialogTheme);

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
            mChoseDate = dateList[leftPosition] + vRight;
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
        wheelRight.setViewAdapter(new ArrayWheelAdapter<>(appContext, right[0], 15));
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

    //设置服务时间
    @Override
    public void setServiceTime(String[] dayList, String[] dateList, String[][] timeList) {
        loadingDismiss();
        showSelectDateTimeDialog(dayList, dateList, timeList);
    }

    @Override
    public void getTokenSuccess(String token) {

    }


    //提交服务工单
    @Override
    public void submit() {

        model.submit(selectServiceType, serviceNumber.getText().toString(), contact.getText().toString(), phoneNumber.getText().toString(), repairTime.getText().toString(), serviceDescription.getText().toString(), null);
    }

    //提交服务成功
    @Override
    public void submitSuccess(Boolean mainTo) {
        loadingDismiss();
        successShow(mainTo + "mainTo");
    }
}

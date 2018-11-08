package com.nacity.college.property.fragment;

import android.Manifest;
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
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.college.common_libs.domain.property.ServiceTypeTo;
import com.nacity.college.R;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.PhotoPreviewActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.lidong.photopicker.intent.PhotoPreviewIntent;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseFragment;
import com.nacity.college.base.CommonDialog;
import com.nacity.college.base.Constant;
import com.nacity.college.base.Event;
import com.nacity.college.base.impl.FragmentPermissionListener;
import com.nacity.college.base.utils.image.Bimp;
import com.nacity.college.base.utils.wheelview.WheelView;
import com.nacity.college.base.utils.wheelview.adapter.ArrayWheelAdapter;
import com.nacity.college.property.ChangeContactAndPhoneActivity;
import com.nacity.college.property.RoomRepairActivity;
import com.nacity.college.property.SelectServiceCategoryActivity;
import com.nacity.college.property.model.RepairModel;
import com.nacity.college.property.presenter.RepairPresenter;
import com.nacity.college.property.view.RepairView;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

import static android.app.Activity.RESULT_OK;

/**
 * Created by xzz on 2017/9/4.
 **/

public class AppointmentFragment extends BaseFragment implements FragmentPermissionListener, RepairView {
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
    @BindView(R.id.service_image)
    GridLayout serviceImage;
    @BindView(R.id.service_description)
    EditText serviceDescription;
    @BindView(R.id.submit)
    TextView submit;
    Unbinder unbinder;
    @BindView(R.id.pay_money)
    TextView payMoney;

    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    @BindView(R.id.select_service)
    TextView selectService;
    @BindView(R.id.service_content)
    TextView serviceContent;
    @BindView(R.id.contact)
    TextView contact;
    @BindView(R.id.service_unit)
    TextView serviceUnit;
    @BindView(R.id.service_unit_tip)
    TextView serviceUnitTip;
    @BindView(R.id.repair_describe)
    TextView repairDescribe;
    @BindView(R.id.remark_layout)
    AutoRelativeLayout remarkLayout;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private RepairModel model;

    private ServiceTypeTo selectServiceType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_property_appointment, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        initView();
        EventBus.getDefault().register(this);
        model = new RepairPresenter(this);
        return mRootView;
    }

    private void initView() {
        imagePaths.add("000000");//000000为添加图片背景设置的路径
        setImageLayout(imagePaths);
        phoneNumber.setText(userInfo.getUserInfoTo().getUserMobile());
        serviceContent.setFocusable(false);
        contact.setText(TextUtils.isEmpty(userInfo.getUserInfoTo().getRealName())?"":userInfo.getUserInfoTo().getRealName());
        repairAddress.setText(userInfo.getUserInfoTo().getRoomNo());

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
                intent = new Intent(appContext, SelectServiceCategoryActivity.class);
                intent.putExtra("Title", Constant.ROOM_REPAIR);
                intent.putExtra("CategorySid", "0");
                startActivity(intent);
                goToAnimation(1);
                break;

            case R.id.submit:
                if (!check())
                    return;
                if (imagePaths.size() > 1)
                    model.getUpImageToken();
                else
                    model.submit(selectServiceType, serviceNumber.getText().toString(), contact.getText().toString(), phoneNumber.getText().toString(), repairTime.getText().toString(), serviceDescription.getText().toString(), repairAddress.getText().toString());
                loadingShow();
                break;
        }
    }

    //设置上传图片的布局
    private void setImageLayout(ArrayList<String> imagePaths) {
        serviceImage.removeAllViews();
        for (int i = 0; i < imagePaths.size(); i++) {
            PhotoView photoView = new PhotoView(appContext);
            photoView.setScaleType(ImageView.ScaleType.CENTER);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.height = (int) (getScreenWidth() * 0.21333);
            layoutParams.width = (int) (getScreenWidth() * 0.21333);
            layoutParams.setMargins(0, 0, i == 3 ? 0 : (int) (getScreenWidth() * 0.022222), 0);
            if ("000000".equals(imagePaths.get(i)))
                photoView.setBackgroundResource(R.drawable.selector_capture);
            else
                Glide.with(appContext).load(imagePaths.get(i)).into(photoView);
            photoView.setLayoutParams(layoutParams);
            photoView.setTag(imagePaths.get(i));
            photoView.setOnClickListener(v -> getPermissionPhoto(Manifest.permission.CAMERA, v, this));
            serviceImage.addView(photoView);
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:

//                    imagePaths = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);

                    ArrayList<String> handlerImageList=new ArrayList<>();
                    imagePaths = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
//                    if (imagePaths.size() < 4)
//                        imagePaths.add("000000");
                    for (String mPath:imagePaths){
                        try{
                            Bimp.getImageUri(mPath, false);
                            handlerImageList.add(mPath);
                        }catch (Exception e){
                            System.out.println();
                        }
                    }

                    imagePaths=handlerImageList;
                    if (imagePaths.size() < 4)
                        imagePaths.add("000000");
                    if (imagePaths.size()>0)
                        setImageLayout(imagePaths);

                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    imagePaths = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    if (imagePaths.size() < 4)
                        imagePaths.add("000000");
                    setImageLayout(imagePaths);
                    break;
            }
        }

    }


    @Override
    public void refuse(String permission) {

    }

    @Override
    public void accept(String permission, View view) {
        if (Manifest.permission.CAMERA.equals(permission))
            getPermissionPhoto(Manifest.permission.READ_EXTERNAL_STORAGE, view, this);
        if (Manifest.permission.READ_EXTERNAL_STORAGE.equals(permission)) {
            if ("000000".equals(view.getTag())) {
                PhotoPickerIntent intent = new PhotoPickerIntent(appContext);
                intent.setSelectModel(SelectModel.MULTI);
                intent.setShowCarema(true); // 是否显示拍照
                intent.setMaxTotal(4); // 最多选择照片数量，默认为6
                intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
            } else {
                PhotoPreviewIntent intent = new PhotoPreviewIntent(appContext);
                intent.setCurrentItem(0);
                intent.setPhotoPaths(imagePaths);
                if ("000000".equals(imagePaths.get(imagePaths.size() - 1))) {
                    imagePaths.remove(imagePaths.get(imagePaths.size() - 1));
                }
                startActivityForResult(intent, REQUEST_PREVIEW_CODE);
            }
        }
    }

    //获取服务种类
    @Subscribe
    public void getServiceCategory(Event<ServiceTypeTo> event) {
        if ("ServiceCategory".equals(event.getType())) {
            selectServiceType = event.getMode();
            serviceName.setVisibility(View.VISIBLE);
            serviceContent.setVisibility(View.VISIBLE);
            serviceName.setText(event.getMode().getName());
            selectService.setVisibility(View.GONE);
            serviceContent.setText(event.getMode().getContent());
            serviceNumber.setText((event.getMode().getQtyDefault() > 0 ? event.getMode().getQtyDefault() : 1) + "");
            serviceUnit.setText(event.getMode().getQtyUnit());
            serviceUnitTip.setText(event.getMode().getQtyTitle());
            payMoney.setText("¥" + Integer.valueOf(serviceNumber.getText().toString()) * event.getMode().getUnitPrice());
            setServiceNumber(event.getMode().getQtyDefault(), event.getMode().getUnitPrice());
            remarkLayout.setVisibility(TextUtils.isEmpty(event.getMode().getRemark())?View.GONE:View.VISIBLE);
            repairDescribe.setText(event.getMode().getRemark());
            Glide.with(MainApp.mContext).load(MainApp.getImagePath(event.getMode().getImg())).error(R.drawable.service_icon).into(serviceIcon);

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

    //设置服务时间
    @Override
    public void setServiceTime(String[] dayList, String[] dateList, String[][] timeList) {
        loadingDismiss();
        showSelectDateTimeDialog(dayList, dateList, timeList);
    }

    //获取上传图片token成功

    @Override
    public void getTokenSuccess(String token) {
        loadingShow();
        model.uploadImage(imagePaths, token);
    }

    //提交服务工单
    @Override
    public void submit() {
        if (!check())
            return;
        loadingShow();
        model.submit(selectServiceType, serviceNumber.getText().toString(), contact.getText().toString(), phoneNumber.getText().toString(), repairTime.getText().toString(), serviceDescription.getText().toString(), repairAddress.getText().toString());
    }

    //提交服务成功
    @Override
    public void submitSuccess(Boolean mainTo) {
        loadingDismiss();
        Intent intent = new Intent(appContext, RoomRepairActivity.class);
        intent.putExtra("Type", 0);
        intent.putExtra("CategorySid", "0");
        intent.putExtra("Index", 1);
        startActivity(intent);
        getActivity().finish();
        goToAnimation(1);
    }

    //设置服务数量加减
    public void setServiceNumber(int minNumber, double price) {
        numberAdd.setOnClickListener(v -> {
            serviceNumber.setText(Integer.valueOf(serviceNumber.getText().toString()) + 1 + "");
            payMoney.setText("¥" + Integer.valueOf(serviceNumber.getText().toString()) * price);
        });
        numberReduce.setOnClickListener(v -> {
            if (minNumber < Integer.valueOf(serviceNumber.getText().toString())) {
                serviceNumber.setText(Integer.valueOf(serviceNumber.getText().toString()) - 1 + "");
                payMoney.setText("¥" + Integer.valueOf(serviceNumber.getText().toString()) * price);
            }
        });
    }

    //检查提交条件是否完善
    public Boolean check() {
        if (selectServiceType == null) {
            showMessage(Constant.PLEASE_SELECT_CATEGORY);
            return false;
        }
        if (TextUtils.isEmpty(contact.getText().toString())) {
            showMessage(Constant.PLEASE_INPUT_CONTACT);
            return false;
        }
        if (TextUtils.isEmpty(repairTime.getText().toString())) {
            showMessage(Constant.PLEASE_SELECT_REPAIR_TIME);
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        scrollView.smoothScrollBy(0,0);
    }
}

package com.nacity.college.property.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.college.common_libs.domain.property.ServiceTypeTo;
import com.nacity.college.R;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.PhotoPreviewActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.lidong.photopicker.intent.PhotoPreviewIntent;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseFragment;
import com.nacity.college.base.Constant;
import com.nacity.college.base.Event;
import com.nacity.college.base.impl.FragmentPermissionListener;
import com.nacity.college.base.impl.PermissionListener;
import com.nacity.college.base.utils.image.Bimp;
import com.nacity.college.property.PropertyComplaintActivity;
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

public class PropertyComplaintFragment extends BaseFragment implements FragmentPermissionListener, RepairView, PermissionListener {


    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    @BindView(R.id.service_icon)
    ImageView serviceIcon;
    @BindView(R.id.select_service)
    TextView selectService;
    @BindView(R.id.service_name)
    TextView serviceName;
    @BindView(R.id.service_content)
    TextView serviceContent;
    @BindView(R.id.service_image)
    GridLayout serviceImage;
    @BindView(R.id.service_description)
    EditText serviceDescription;
    @BindView(R.id.complaint_describe)
    TextView complaintDescribe;
    Unbinder unbinder;
    @BindView(R.id.remark_layout)
    AutoRelativeLayout remarkLayout;
    @BindView(R.id.complaint_phone)
    TextView complaintPhone;

    private ArrayList<String> imagePaths = new ArrayList<>();
    private RepairModel model;
    private ServiceTypeTo selectServiceType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_property_complaint, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        initView();
        EventBus.getDefault().register(this);
        model = new RepairPresenter(this);


        return mRootView;
    }

    private void initView() {
        imagePaths.add("000000");//000000为添加图片背景设置的路径
        setImageLayout(imagePaths);
        complaintDescribe.setText("物业服务中心工作时间为" + userInfo.getUserInfoTo().getCommonGarden().getToWorkTime() + "-" +  userInfo.getUserInfoTo().getCommonGarden().getOffWorkTime() + "\n如在非工作时间请拨打物业值班电话：");
        complaintPhone.setText( userInfo.getUserInfoTo().getCommonGarden().getGardenTel());
        complaintPhone.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @OnClick({R.id.submit, R.id.complaint_phone, R.id.service_category_layout})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {


            case R.id.service_category_layout:
                intent = new Intent(appContext, SelectServiceCategoryActivity.class);
                intent.putExtra("Title", Constant.COMPLAINT);
                intent.putExtra("CategorySid", "2");
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.submit:
                if (!check())
                    return;
                loadingShow();
                if (imagePaths.size() > 1)
                    model.getUpImageToken();
                else
                    model.submit(selectServiceType, "0", "", userInfo.getPhone(), null, serviceDescription.getText().toString(), null);
                break;
            case R.id.complaint_phone:
                contactDialog();
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
    public void accept(String permission) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + userInfo.getUserInfoTo().getCommonGarden().getGardenTel()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
            remarkLayout.setVisibility(View.VISIBLE);

            Glide.with(MainApp.mContext).load(MainApp.getImagePath(event.getMode().getImg())).into(serviceIcon);
        }
    }


    @Override
    public void setServiceTime(String[] dayList, String[] dateList, String[][] timeList) {

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
        model.submit(selectServiceType, "0", "", userInfo.getPhone(), null, serviceDescription.getText().toString(), null);
    }

    //提交服务成功
    @Override
    public void submitSuccess(Boolean mainTo) {
        loadingDismiss();
        Intent intent = new Intent(appContext, PropertyComplaintActivity.class);
        intent.putExtra("CategorySid", "2");
        intent.putExtra("Index", 1);
        startActivity(intent);
        getActivity().finish();
        goToAnimation(1);
    }


    //检查提交条件是否完善
    public Boolean check() {
        if (selectServiceType == null) {
            showMessage(Constant.PLEASE_SELECT_COMPLAINT_CATEGORY);
            return false;
        }

        return true;
    }

    //拨号给处理任务人员
    public void contactDialog() {
        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(getActivity());
        dialogBuilder.setContentView(R.layout.dialog_look_house);
        ((TextView) dialogBuilder.findViewById(R.id.tip_content)).setText(Constant.CONTACT_RESPONSIBLE);
        dialogBuilder.findViewById(R.id.confirm).setOnClickListener(v -> {
            dialogBuilder.dismiss();
            getPermission(Manifest.permission.CALL_PHONE, this);
        });
        dialogBuilder.findViewById(R.id.cancel).setOnClickListener(v -> dialogBuilder.dismiss());
        dialogBuilder.show();
        dialogBuilder.setCanceledOnTouchOutside(true);
    }
}

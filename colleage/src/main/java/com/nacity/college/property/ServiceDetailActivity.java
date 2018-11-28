package com.nacity.college.property;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.college.common_libs.domain.property.ServiceMainTo;
import com.nacity.college.R;
import com.nacity.college.databinding.ActivityServiceDetailBinding;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.CommonAlertDialog;
import com.nacity.college.base.Constant;
import com.nacity.college.base.Event;
import com.nacity.college.base.PostImageDetailActivity;
import com.nacity.college.base.impl.PermissionListener;
import com.nacity.college.property.model.ServiceDetailModel;
import com.nacity.college.property.presenter.ServiceDetailPresenter;
import com.nacity.college.property.view.ServiceDetailView;

import de.greenrobot.event.EventBus;
import rx.Observable;

/**
 * Created by xzz on 2017/10/11.
 *  ----> 4   家政服务
 *  ---->0    入室维修
 *  ----> 1   公共维修
 *  ---->3    园区配送
 *  ---->2    投诉
 **/

public class ServiceDetailActivity extends BaseActivity implements ServiceDetailView, PermissionListener {
    private ServiceMainTo mainTo;
    private ActivityServiceDetailBinding binding;
    private ServiceDetailModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ServiceDetailPresenter(this);
        model.getServiceDetail(getIntent().getStringExtra("ServiceId"));
        loadingShow();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_detail);
        setTitle(Constant.SERVICE_DETAIL);

    }


    private void cancelReportDialog() {
        CommonAlertDialog.show(appContext, Constant.CANCEL_REPORT, Constant.CONFIRM, Constant.CANCEL).setOnClickListener(v -> {
            loadingShow();
            model.cancelReport(mainTo.getServiceId());
            CommonAlertDialog.dismiss();
        });
    }


    @Override
    public void getServiceDetailSuccess(ServiceMainTo mainTo) {
        loadingDismiss();
        this.mainTo = mainTo;
        binding.setMode(mainTo);
        setView();
    }

    @Override
    public void cancelReportSuccess() {
        EventBus.getDefault().post(new Event<>("CancelReportRefresh", null));
        finish();
        goToAnimation(1);
    }


    private void setView() {
        Log.i("2222", "Type: "+getIntent().getStringExtra("Type"));
        if ("2".equals(getIntent().getStringExtra("Type"))){
            binding.infoLayout.setVisibility(View.GONE);
            binding.waterLayout.setVisibility(View.GONE);
            binding.complaintText.setText(Constant.MY_COMPLAINT);
        }else if ("1".equals(getIntent().getStringExtra("Type"))){
            binding.waterLayout.setVisibility(View.GONE);
            binding.repairUnitLayout.setVisibility(View.GONE);

        }else if("3".equals(getIntent().getStringExtra("Type"))){
            binding.repairUnitLayout.setVisibility(View.GONE);
            binding.repairMoneyLayout.setVisibility(View.GONE);
            binding.tv03.setText("配送地址：");
            binding.tv04.setText("配送时间：");
        }
        else if("0".equals(getIntent().getStringExtra("Type"))){
            binding.waterLayout.setVisibility(View.GONE);
        }
        binding.createTime.setText(mainTo.getCreateTime());
        binding.repairMoney.setText(mainTo.getSeviceNum() * mainTo.getUnitPrice() + "");
        binding.serviceUnitName.setText(mainTo.getQtyTitle() + "：");
        binding.serviceUnit.setText(mainTo.getSeviceNum() + mainTo.getQtyUnit());
        if (!TextUtils.isEmpty(mainTo.getServiceImgs()))
            setImageLayout(mainTo.getServiceImgs().replaceAll(",", ";"), binding.demandImage);
        if (TextUtils.isEmpty(mainTo.getRepairTime())&&"1".equals(getIntent().getStringExtra("Type"))) {
            binding.repairTimeLayout.setVisibility(View.GONE);
            binding.repairMoneyLayout.setVisibility(View.GONE);
        }
        binding.cancelReport.setOnClickListener(v -> cancelReportDialog());
        binding.responsiblePerson.setText(mainTo.getOperateUserName() + (TextUtils.isEmpty(mainTo.getUserJob()) ? "" : "(" + mainTo.getUserJob() + ")"));
        if ("1".equals(mainTo.getServiceStatus())) {
            binding.cancelReport.setVisibility(View.VISIBLE);
            binding.responsiblePersonLayout.setVisibility(View.GONE);
            binding.responsibleLayout.setVisibility(View.VISIBLE);
            binding.responsibleDesc.setText(Constant.SERVICE_WAIT_HANDLE);
        } else if ("2".equals(mainTo.getServiceStatus())) {
            binding.feedTime.setText(mainTo.getOperateDate());
            binding.responsibleLayout.setVisibility(View.VISIBLE);
            binding.responsibleDesc.setText(Constant.SERVICE_IS_HANDLE);
        } else if ("3".equals(mainTo.getServiceStatus())) {
            binding.evaluateBtn.setVisibility(View.VISIBLE);
            binding.feedContent.setText(mainTo.getOperateDesc());
            binding.evaluateBtn.setBackgroundResource(R.drawable.property_evaluate_icon);
            binding.evaluateBtn.setOnClickListener(v -> {

                Intent intent = new Intent(appContext, PropertyEvaluateActivity.class);
                intent.putExtra("ServiceId", getIntent().getStringExtra("ServiceId"));
                intent.putExtra("Type",getIntent().getStringExtra("Type"));
                startActivity(intent);
                goToAnimation(1);

            });
            if (!TextUtils.isEmpty(mainTo.getOperateImages()))
                setImageLayout(mainTo.getOperateImages().replaceAll(",", ";"), binding.feedImage);
        } else if ("4".equals(mainTo.getServiceStatus())) {
            binding.evaluateBtn.setVisibility(View.VISIBLE);
            binding.feedContent.setText(mainTo.getOperateDesc());
            binding.evaluateBtn.setBackgroundResource("N".equals(mainTo.getIsEvaluate()) ? R.drawable.property_evaluate_icon : R.drawable.look_evaluate_icon);
            binding.evaluateBtn.setOnClickListener(v -> {
                if ("Y".equals(mainTo.getIsEvaluate())) {
                    Intent intent = new Intent(appContext, ReadEvaluateActivity.class);
                    intent.putExtra("ServiceMainTo", mainTo);
                    startActivity(intent);
                    goToAnimation(1);
                } else {
                    Intent intent = new Intent(appContext, PropertyEvaluateActivity.class);
                    intent.putExtra("ServiceId", getIntent().getStringExtra("ServiceId"));
                    intent.putExtra("Type",getIntent().getStringExtra("Type"));

                    startActivity(intent);
                    goToAnimation(1);
                }
            });
            if (!TextUtils.isEmpty(mainTo.getOperateImages()))
                setImageLayout(mainTo.getOperateImages().replaceAll(",", ";"), binding.feedImage);
        }
        binding.responsiblePhone.setOnClickListener(v -> contactDialog());

    }

    private void setImageLayout(String images, GridLayout imageLayout) {
        String[] imageList = images.split(";");
        Observable.from(imageList).subscribe(imageUrl -> {
            ImageView imageView = new ImageView(appContext);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width = (int) (getScreenWidth() * 0.21833);
            layoutParams.height = (int) (getScreenWidth() * 0.21833);
            layoutParams.leftMargin = (int) (getScreenWidth() * 0.0168889);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(MainApp.mContext).load(MainApp.getImagePath(imageUrl)).into(imageView);
            imageView.setTag(imageUrl);
            imageView.setOnClickListener(v -> {
                Intent intent = new Intent(appContext, PostImageDetailActivity.class);
                intent.putExtra("CurrentPath", (String) v.getTag());
                intent.putExtra("PathList", images);
                startActivity(intent);
            });
            imageLayout.addView(imageView);
        });
    }

    //拨号给处理任务人员
    public void contactDialog() {
        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(appContext);
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

    @Override
    public void accept(String permission) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mainTo.getOperateUserPhone()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void refuse(String permission) {

    }
}

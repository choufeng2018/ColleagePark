package com.nacity.college.property;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nacity.college.R;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.CommonDialog;
import com.nacity.college.base.Constant;
import com.nacity.college.base.utils.wheelview.WheelView;
import com.nacity.college.base.utils.wheelview.adapter.ArrayWheelAdapter;
import com.nacity.college.property.model.DecorationModel;
import com.nacity.college.property.presenter.DecorationPresenter;
import com.nacity.college.property.view.DecorationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 *  Created by usb on 2017/10/16.
 */

public class DecorationApplyActivity extends BaseActivity implements DecorationView {

    @BindView(R.id.image_construction)
    ImageView haveImage;
    @BindView(R.id.phoneNo)
    EditText  phoneNo;
    @BindView(R.id.content)
    EditText  explains;
    @BindView(R.id.name)
    EditText  name;
    @BindView(R.id.company_name)
    EditText  companyName;
    @BindView(R.id.no_image_construction)
    ImageView noImage;
    @BindView(R.id.have_image)
    TextView  have_image;
    @BindView(R.id.tv_apartment_name)
    TextView  roomNo;
    @BindView(R.id.tip)
    TextView  mTip;
    @BindView(R.id.no_image)
    TextView  no_image;
    private String havePicture="2";
    private DecorationModel model;
    private String roomNum;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoration_apply);
        setTitle(Constant.DECORATION_APPLY);
        ButterKnife.bind(this);
        no_image.setClickable(false);
         model = new DecorationPresenter(this);
        init();

//        KeeperModel model = new KeeperPresenter(this);
//        loadingShow();
//        model.getKeeperCategory();
    }

    private void init() {
        explains.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTip.setText("还可以输入" + (100 - s.length()) + "字");
                if (s.length() == 100) {
                    Toast.makeText(DecorationApplyActivity.this, "你只能输入100字哦", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.have_image,R.id.no_image,R.id.manual_input,R.id.tv_apartment_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.have_image:
                Glide.with(MainApp.mContext).load(R.drawable.circle_select).into(haveImage);
                Glide.with(MainApp.mContext).load(R.drawable.circle_unselect).into(noImage);
                havePicture="1";
                have_image.setClickable(false);
                no_image.setClickable(true);

                break;
            case R.id.tv_apartment_name:
                model.getRoomNo();
                loadingShow();
                break;
            case R.id.no_image:
                Glide.with(MainApp.mContext).load(R.drawable.circle_select).into(noImage);
                Glide.with(MainApp.mContext).load(R.drawable.circle_unselect).into(haveImage);
                havePicture="2";
                no_image.setClickable(false);
                have_image.setClickable(true);
                break;
            case R.id.manual_input:
                if (checking())
                    return;
                submitData();
                break;
        }

}

    private void submitData() {
        model.submitDecoration(name.getText().toString(),phoneNo.getText().toString(),companyName.getText().toString(),roomNum,havePicture,explains.getText().toString());
        loadingShow();

    }


    private boolean checking() {
        if (TextUtils.isEmpty(name.getText())) {
            Toasty.info(this, "请输入姓名").show();
            return true;
        }else if (TextUtils.isEmpty(phoneNo.getText())) {
            Toasty.info(this, "请输入联系方式").show();
            return true;
        }
        else if (TextUtils.isEmpty(companyName.getText())) {
            Toasty.info(this, "请输入公司名称").show();
            return true;
        }
        else if (TextUtils.isEmpty(roomNo.getText())) {
            Toasty.info(this, "请选择房号").show();
            return true;
        }
        return false;
    }

    @Override
    public void submit() {
        //弹框
        loadingDismiss();
        final CommonDialog dialog = new CommonDialog(this, R.layout.dailog_decoratian_input, R.style.myDialogTheme);
        Button btnAdd = (Button) dialog.findViewById(R.id.confirm);
        btnAdd.setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    @Override
    public void getRoomNum(String[]var, String[] roomId) {
        loadingDismiss();
        showSelectDateTimeDialog(var,roomId);
    }

    @SuppressLint("SetTextI18n")
    private void showSelectDateTimeDialog(final String[] left,final String[] roomId) {

        final CommonDialog dialog = new CommonDialog(this, R.layout.dialog_wheel_room_number, R.style.myDialogTheme);

        Button btnAdd = (Button) dialog.findViewById(R.id.btn_add);
        RelativeLayout mLayout = (RelativeLayout) dialog.findViewById(R.id.layout);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        final WheelView wheelLeft = (WheelView) dialog.findViewById(R.id.wheel);
        mLayout.setOnClickListener(v -> dialog.dismiss());
        btnAdd.setOnClickListener(v -> {
            int leftPosition = wheelLeft.getCurrentItem();
            String vLeft = left[leftPosition];
            String Id = roomId[leftPosition];

            roomNo.setText(vLeft);
            roomNum=Id;
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        wheelLeft.setCyclic(false);
        wheelLeft.setViewAdapter(new ArrayWheelAdapter<>(appContext, left, 15));
        String mLeftPosition = "1";
        setWheelCurrent(mLeftPosition, wheelLeft, left);
        LinearLayout.LayoutParams paramsLeft = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1);
        paramsLeft.gravity = Gravity.START;
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
}


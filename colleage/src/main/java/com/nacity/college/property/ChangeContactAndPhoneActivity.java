package com.nacity.college.property;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.base.Event;
import com.nacity.college.base.utils.CheckPhoneUtil;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by xzz on 2017/9/4.
 **/

public class ChangeContactAndPhoneActivity extends BaseActivity {
    @BindView(R.id.modify_content)
    EditText modifyContent;

    @BindView(R.id.layout_checked)
    AutoRelativeLayout layoutChecked;
    @BindView(R.id.checked_man)
    ImageButton checkedMan;
    @BindView(R.id.checked_woman)
    ImageButton checkedWoman;


    private boolean selectWoman;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_and_contact);
        ButterKnife.bind(this);
        setTitle(getIntent().getStringExtra("Title"));
        initView();
    }

    private void initView() {
        layoutChecked.setVisibility(getIntent().getIntExtra("Type", 0) == 1 ? View.VISIBLE : View.GONE);
        modifyContent.setHint(getIntent().getIntExtra("Type", 0) == 1 ? userInfo.getUserInfoTo().getName() :getIntent().getIntExtra("Type", 0) == 2? Constant.PLEASE_INPUT_REPAIR_ADDRESS : userInfo.getPhone());
        modifyContent.setInputType(getIntent().getIntExtra("Type", 0) != 0 ? InputType.TYPE_CLASS_TEXT : InputType.TYPE_CLASS_PHONE);

    }

    @OnClick({R.id.choice_man, R.id.choice_woman, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.choice_man:
                selectWoman = false;
                checkedMan.setBackgroundResource(R.drawable.shape_concentric_circles);
                checkedWoman.setBackgroundResource(R.drawable.shape_over);
                break;
            case R.id.choice_woman:
                selectWoman = true;
                checkedWoman.setBackgroundResource(R.drawable.shape_concentric_circles);
                checkedMan.setBackgroundResource(R.drawable.shape_over);
                break;
            case R.id.submit:
                hideInputWindow();

                if (getIntent().getIntExtra("Type", 0) == 1) {
                    if (TextUtils.isEmpty(modifyContent.getText().toString())) {
                        showWarnInfo(Constant.REPAIR_CONTACT_NO_EMPTY);
                        return;
                    }
                    EventBus.getDefault().post(new Event<>("ModifyContact", TextUtils.isEmpty(modifyContent.getText().toString()) ? (userInfo.getUserInfoTo().getName() + (selectWoman ? Constant.LADY : Constant.SIR)) : (modifyContent.getText().toString() + (selectWoman ? Constant.LADY : Constant.SIR))));
                } else if (getIntent().getIntExtra("Type", 0) == 0){
                    if (CheckPhoneUtil.checkPhoneNumber(modifyContent.getText().toString(), appContext))
                        return;
                    EventBus.getDefault().post(new Event<>("ModifyPhone", TextUtils.isEmpty(modifyContent.getText().toString()) ? userInfo.getPhone() : modifyContent.getText().toString()));

                }else if (getIntent().getIntExtra("Type", 0) == 2){
                    if (TextUtils.isEmpty(modifyContent.getText().toString())) {
                        showWarnInfo(Constant.REPAIR_ADDRESS_NO_EMPTY);
                        return;
                    }
                    EventBus.getDefault().post(new Event<>("ModifyRepairAddress",modifyContent.getText().toString()));
                }
                finish();
                goToAnimation(2);
                break;
        }
    }

    public void hideInputWindow() {

        final View v = getWindow().peekDecorView();
        if (v != null && v.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

}

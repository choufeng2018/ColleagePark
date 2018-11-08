package com.nacity.college.main.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bumptech.glide.Glide;
import com.hzblzx.miaodou.sdk.MiaodouKeyAgent;
import com.hzblzx.miaodou.sdk.core.bluetooth.MDAction;
import com.hzblzx.miaodou.sdk.core.bluetooth.MDActionListener;
import com.hzblzx.miaodou.sdk.core.model.BigSurprise;
import com.hzblzx.miaodou.sdk.core.model.MDVirtualKey;
import com.izhihuicheng.api.lling.LLingOpenDoorConfig;
import com.izhihuicheng.api.lling.LLingOpenDoorHandler;
import com.izhihuicheng.api.lling.LLingOpenDoorStateListener;
import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.college.common_libs.domain.door.OpenDoorListTo;
import com.college.common_libs.domain.door.OpenDoorTypeTo;
import com.college.common_libs.domain.user.MainMenuTo;
import com.nacity.college.R;
import com.nacity.college.databinding.MainServiceItemBinding;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseFragment;
import com.nacity.college.base.CommonDialog;
import com.nacity.college.base.Constant;
import com.nacity.college.base.banner.BannerUtil;
import com.nacity.college.base.impl.PermissionListener;
import com.nacity.college.base.info.ApartmentInfoHelper;
import com.nacity.college.base.utils.Cockroach;
import com.nacity.college.base.utils.DateUtil;
import com.nacity.college.base.utils.OutAnimationDrawable;
import com.nacity.college.base.utils.SpUtil;
import com.nacity.college.door.ApplyDoorActivity;
import com.nacity.college.door.presenter.OpenDoorPresenter;
import com.nacity.college.door.util.BluetoothManager;
import com.nacity.college.door.util.QRCode;
import com.nacity.college.door.view.OpenDoorView;
import com.nacity.college.guide.ParkGuideActivity;
import com.nacity.college.main.adapter.PropertyServiceBannerView;
import com.nacity.college.main.model.MainHomeModel;
import com.nacity.college.main.presenter.MainHomePresenter;
import com.nacity.college.main.view.MainHomeView;
import com.nacity.college.news.EntrepreneurshipServiceActivity;
import com.nacity.college.news.FinancingActivity;
import com.nacity.college.news.LegalServiceActivity;
import com.nacity.college.news.NewsActivity;
import com.nacity.college.news.NewsDetailActivity;
import com.nacity.college.news.ParkActivitiesActivity;
import com.nacity.college.news.ParkInnovateActivity;
import com.nacity.college.news.PolicyServiceActivity;
import com.nacity.college.property.DecorationApplyActivity;
import com.nacity.college.property.ParkDistributionActivity;
import com.nacity.college.property.ParkingApplyActivity;
import com.nacity.college.property.PropertyComplaintActivity;
import com.nacity.college.property.PropertyPraiseActivity;
import com.nacity.college.property.PropertySuggestActivity;
import com.nacity.college.property.PublicRepairActivity;
import com.nacity.college.property.RoomRepairActivity;
import com.nacity.college.recruit.RecruitActivity;
import com.nacity.college.rent.HouseListActivity;
import com.zhidian.cloudintercomlibrary.CloudIntercomLibrary;
import com.zhidian.cloudintercomlibrary.entity.EasemobBean;
import com.zhidian.cloudintercomlibrary.entity.UserBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;

import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by xzz on 2017/8/30.
 **/

public class MainHomeFragment2 extends BaseFragment implements MainHomeView, OpenDoorView, MDActionListener, PermissionListener {
    @BindView(R.id.banner)
    ConvenientBanner<String> banner;
    @BindView(R.id.park_service_layout)
    GridLayout parkServiceLayout;
    @BindView(R.id.property_service_banner)
    ConvenientBanner<List<MainMenuTo>> propertyServiceBanner;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.service_layout)
    GridLayout serviceLayout;
    Unbinder unbinder;
    @BindView(R.id.property_service_text)
    TextView propertyServiceText;
    public MainHomeModel model = new MainHomePresenter(this);
    private OpenDoorPresenter openDoorPresenter = new OpenDoorPresenter(this);
    private List<AdvertiseTo> adList = new ArrayList<>();
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//蓝牙管理
    private int doorType = 0;//1令令，2庙兜，3，指点，4 令令+庙兜，5 庙兜+指点 6令令+指点 ，7令令+2庙兜+指点
    private Handler handler = new Handler();
    private List<OpenDoorListTo> doorList = new ArrayList<>();
    private CommonDialog openDoorDialog;
    private ImageView openDoorCode;
    private TextView codeDoorName;
    private RelativeLayout codeLayout;
    private ImageView animationImage;
    private View parentLayout;
    private View doorListLayout;
    private SensorManager sensorManager;
    private Vibrator vibrator;
    private boolean canShake = true;//防止连续摇动
    private String lingId = "03ed006464";//令令二维码开门上传用户id
    private boolean canClickOpenDoor = true;// 防止多次点击开门
    private boolean isClickLogin;//判断是点击获取门禁登录，还是直接进入的时候登录云对讲


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_home, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        model.getApartmentAd(apartmentInfo.getSid());
        location.setText(apartmentInfo.getGardenName());
        CloudIntercomLibrary.getInstance().init(appContext);
        openDoorPresenter.sendlogInNet(userInfo.getSid());
        install();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("5555", "onResume1: " + ApartmentInfoHelper.getInstance(MainApp.mContext).getGardenName());
        Log.i("5555", "onResume2-: " + apartmentInfo.getGardenName());
        location.setText(ApartmentInfoHelper.getInstance(MainApp.mContext).getGardenName());
        model.getParkService();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private String isFirst = "111";

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i("55555", "setUserVisibleHint: " + isVisibleToUser);
        Log.i("55555", "isFirst: " + isFirst);
        if (isVisibleToUser) {
            if ("222".equals(isFirst)) {
                location.setText((ApartmentInfoHelper.getInstance(MainApp.mContext).getGardenName()));
            }
            isFirst = "222";

        }

    }

    //跳转到对应物业服务的activity
    private void jumpToServiceActivity(String type, View v) {
        Intent intent;
        switch (type) {
            case "0001":
                intent = new Intent(appContext, NewsActivity.class);
                intent.putExtra("Title", (String) v.getTag(R.id.park_service_name));
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0002":
                intent = new Intent(appContext, ParkGuideActivity.class);
                intent.putExtra("Title", (String) v.getTag(R.id.park_service_name));
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0003":
                intent = new Intent(appContext, RecruitActivity.class);
                intent.putExtra("Title", (String) v.getTag(R.id.park_service_name));
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0004":
                //创业沙龙
                intent = new Intent(appContext, ParkActivitiesActivity.class);
                startActivity(intent);
                goToAnimation(1);
//                toExpectActivity(Constant.ETREPRENURAL_SALON);
                break;
            case "0005":
                intent = new Intent(appContext, HouseListActivity.class);
                intent.putExtra("Title", (String) v.getTag(R.id.park_service_name));
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0011":
                startActivity(new Intent(appContext, ParkingApplyActivity.class));
                goToAnimation(1);
//                toExpectActivity(Constant.PARKING_LOT_APPLY);
                break;
            case "0007":
                intent = new Intent(appContext, ParkInnovateActivity.class);
                intent.putExtra("Title", (String) v.getTag(R.id.park_service_name));
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0008":
                intent = new Intent(appContext, LegalServiceActivity.class);
                intent.putExtra("Title", (String) v.getTag(R.id.park_service_name));
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0009":
                intent = new Intent(appContext, PolicyServiceActivity.class);
                intent.putExtra("Title", (String) v.getTag(R.id.park_service_name));
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0010":
                startActivity(new Intent(appContext, EntrepreneurshipServiceActivity.class));
                break;
            case "0006":
                intent = new Intent(appContext, FinancingActivity.class);
                intent.putExtra("Title", (String) v.getTag(R.id.park_service_name));
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0012":
                intent = new Intent(appContext, RoomRepairActivity.class);
                intent.putExtra("Type", 0);
                intent.putExtra("CategorySid", "0");
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0013":
                intent = new Intent(appContext, PublicRepairActivity.class);
                intent.putExtra("CategorySid", "1");
                startActivity(intent);
                goToAnimation(1);
                break;

            case "0016":

                intent = new Intent(appContext, PropertyPraiseActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;

            case "0018":
                intent = new Intent(appContext, PropertySuggestActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0017":
                intent = new Intent(appContext, PropertyComplaintActivity.class);
                intent.putExtra("CategorySid", "2");
                startActivity(intent);
                goToAnimation(1);
                break;

            case "0015":
                startActivity(new Intent(appContext, ParkDistributionActivity.class));
                break;
            case "0014":
            case "0019":
                toExpectActivity((String) v.getTag(R.id.park_service_name));
                break;
            case "0020":
                intent = new Intent(appContext, DecorationApplyActivity.class);
                intent.putExtra("CategorySid", "2");
                startActivity(intent);
                goToAnimation(1);
                break;
            case "0021":
                if (canClickOpenDoor) {
                    canClickOpenDoor = false;
                    getPermission(Manifest.permission.ACCESS_COARSE_LOCATION, this);

                    handler.postDelayed(() -> canClickOpenDoor = true, 800);
                }
                break;

        }


    }


    @Override
    public void getAdSuccess(List<AdvertiseTo> advertiseList) {
        if (adList.equals(advertiseList))
            return;
        adList.clear();
        adList.addAll(advertiseList);
        BannerUtil.setBanner(banner, advertiseList, R.drawable.park_rent_house_load);
        banner.setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused}).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        banner.startTurning(5000);

    }

    @Override
    public void getParkServiceSuccess(List<MainMenuTo> data) {
        model.getPropertyService();
        parkServiceLayout.removeAllViews();
        if (data.size() == 0)
            return;
        Observable.from(data).subscribe(mainMenuTo -> {
            View parkServiceView = View.inflate(appContext, R.layout.park_service_item, null);
            ImageView parkServiceImage = (ImageView) parkServiceView.findViewById(R.id.park_service_image);
            TextView parkServiceName = (TextView) parkServiceView.findViewById(R.id.park_service_name);
            parkServiceName.setText(mainMenuTo.getShowName());
            Glide.with(MainApp.mContext).load(MainApp.getImagePath(mainMenuTo.getPicUrl1())).into(parkServiceImage);
            parkServiceView.setTag(mainMenuTo.getCode());
            parkServiceView.setTag(R.id.park_service_name, mainMenuTo.getShowName());
            parkServiceView.setOnClickListener(v -> jumpToServiceActivity((String) v.getTag(), v));
            parkServiceLayout.addView(parkServiceView);
        });


    }

    @Override
    public void getPropertySuccess(List<MainMenuTo> data) {
        model.getServiceList();
        propertyServiceText.setVisibility(View.VISIBLE);
        propertyServiceBanner.setVisibility(View.VISIBLE);
        if (data.size() == 0) {
            propertyServiceText.setVisibility(View.GONE);
            propertyServiceBanner.setVisibility(View.GONE);
            return;
        }
        List<List<MainMenuTo>> localImages = new ArrayList<>();
        for (int i = 0; i < (data.size() % 8 == 0 ? data.size() / 8 : data.size() / 8 + 1); i++) {
            List<MainMenuTo> mainMenuTos = new ArrayList<>();
            for (int j = 0; j < 8 && (i * 8 + j < data.size()); j++) {
                mainMenuTos.add(data.get(i * 8 + j));
            }
            localImages.add(mainMenuTos);
        }

        PropertyServiceBannerView propertyServiceBannerView = new PropertyServiceBannerView();
        propertyServiceBanner.setMinimumHeight((int) (getScreenWidthPixels(appContext) * 0.5333));
        int[] pageIndicator = {R.drawable.wisdom_dot_un_select, R.drawable.wisdom_dot_select};

        propertyServiceBanner.setPages(
                () -> propertyServiceBannerView, localImages);
        if (localImages.size() > 1)
            propertyServiceBanner.setPageIndicator(pageIndicator);
        else
            propertyServiceBanner.setCanLoop(false);
        propertyServiceBannerView.setOnPropertyServiceOnClickListener(this::jumpToServiceActivity);
        setCloudCanLogin(data);
    }

    @Override
    public void getServiceListSuccess(List<MainMenuTo> data) {
        serviceLayout.removeAllViews();
        Observable.from(data).subscribe(mainMenuTo -> {
            View mView = View.inflate(appContext, R.layout.main_service_item, null);
            MainServiceItemBinding bind = DataBindingUtil.bind(mView);
            bind.setMode(mainMenuTo);
            if (TextUtils.isEmpty(mainMenuTo.getPicUrl()))
                bind.serviceImage.setVisibility(View.GONE);
            Glide.with(MainApp.mContext).load(MainApp.getImagePath(mainMenuTo.getPicUrl3())).into(bind.serviceIcon);
            Glide.with(MainApp.mContext).load(MainApp.getImagePath(mainMenuTo.getPicUrl())).into(bind.serviceImage);
            if (!TextUtils.isEmpty(mainMenuTo.getLastModTime()))
                bind.serviceTime.setText(DateUtil.formatDateString(DateUtil.mDateFormatString, mainMenuTo.getLastModTime()));
            serviceLayout.addView(mView);
            mView.setTag(R.id.park_service_type, mainMenuTo.getArticleType());
            mView.setTag(R.id.park_service_id, mainMenuTo.getArticleId());
            mView.setTag(R.id.park_service_phone, mainMenuTo.getPhone());
            mView.setTag(R.id.park_service_title, mainMenuTo.getTitle());
            mView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("id", (String) v.getTag(R.id.park_service_id));
                intent.putExtra("phone", (String) v.getTag(R.id.park_service_phone));
                intent.putExtra("type", "7");
                intent.putExtra("typeOther", (String) v.getTag(R.id.park_service_type));
                intent.putExtra("title", (String) v.getTag(R.id.park_service_title));
//                Log.i("2222", "getServiceListSuccess: "+(String)v.getTag(R.id.park_service_type));

//                intent.putExtra("Url",MainApp.DefaultValue.NEWS_DETAIL_URI+ v.getTag());
//                intent.putExtra("Title",(String)v.getTag(R.id.park_service_name));
                startActivity(intent);
            });
        });
    }
//            mView.setTag(mainMenuTo.getArticleId());
//            mView.setTag(R.id.park_service_name, mainMenuTo.getTitle());
//            mView.setOnClickListener(v -> {
//                Intent intent = new Intent(getActivity(), AdWebActivity.class);
//                intent.putExtra("Url", MainApp.DefaultValue.NEWS_DETAIL_URI + v.getTag());
//                intent.putExtra("Title", (String) v.getTag(R.id.park_service_name));
//                startActivity(intent);
//            });
//

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (banner != null)
            banner.stopTurning();
        Cockroach.uninstall();
    }

    public void getData() {

        model.getApartmentAd(apartmentInfo.getSid());
        model.getParkService();
    }

    /**
     * 获取小区门禁开放状态和门类型
     */
    @Override
    public void getApartmentDoorStatueSuccess(OpenDoorTypeTo doorTypeTo) {

        if (doorTypeTo != null && doorTypeTo.getOpenSupplier() != null && doorTypeTo.getOpenSupplier().size() > 0)
            setDoorType(doorTypeTo.getOpenSupplier());
        else {
            loadingDismiss();
            toExpectActivity(Constant.ONE_KEY_OPEN_DOOR);
        }
    }

    @Override
    public void getDoorTypeFail() {

    }


    /**
     * 转换开门类型并开始获取开门列表
     */
    public void setDoorType(List<Integer> supplierList) {
        if (supplierList == null || supplierList.size() == 0)
            return;

        if (supplierList.size() == 1 && supplierList.contains(1)) {
            doorType = 1;
        } else if (supplierList.size() == 1 && supplierList.contains(2)) {
            doorType = 2;
        } else if (supplierList.size() == 1 && supplierList.contains(3)) {
            doorType = 3;
        } else if (supplierList.size() == 2 && supplierList.contains(1) && supplierList.contains(2)) {
            doorType = 4;
        } else if (supplierList.size() == 2 && supplierList.contains(1) && supplierList.contains(3)) {
            doorType = 5;
        } else if (supplierList.size() == 2 && supplierList.contains(3) && supplierList.contains(2)) {
            doorType = 6;
        } else if (supplierList.size() == 3) {
            doorType = 7;
        }

        openDoorPresenter.getOpenDoorList(apartmentInfo.getSid(), userInfo.getSid());
        if (doorType == 1 || doorType == 2 || doorType == 4) {
            sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
            if (sensorManager != null) {// 注册监听器
                vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
                sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
                // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
            }
        }
    }


    /**
     * 去申请门禁对话框
     */
    private void toApplyDoorDialog() {
        CommonDialog dialog = new CommonDialog(getActivity(), R.layout.change_name_dialog, R.style.myDialogTheme);
        ((TextView) dialog.findViewById(R.id.title)).setText(Constant.TO_APPLY_DOOR);
        dialog.findViewById(R.id.btn_add).setOnClickListener(v -> {
            startActivity(new Intent(appContext, ApplyDoorActivity.class));
            goToAnimation(1);
            dialog.dismiss();
        });
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
        dialog.setCancelable(true);

    }


    /**
     * 获取门禁列表成功
     */
    @Override
    public void getDoorListSuccess(List<OpenDoorListTo> doorList) {
        loadingDismiss();
        if (!isClickLogin){
            setCloudLogin(doorList);
            isClickLogin=true;
        }else {
            if (doorList == null || doorList.size() == 0) {
                toApplyDoorDialog();
                return;
            }

            setOpenDoor(doorList);
        }

    }

    @Override
    public void toApplyDoor() {
        loadingDismiss();
        toApplyDoorDialog();
    }

    @Override
    public void getDoorListFail() {

    }

    /**
     * 设置开门
     */
    private void setOpenDoor(List<OpenDoorListTo> doorList) {
        setCloudLogin(doorList);
        if (doorType == 1 || doorType == 4 || doorType == 7) {
            Observable.from(doorList).filter(openDoorListTo -> "1".equals(openDoorListTo.getSupplierId())).subscribe(openDoorListTo -> lingId = openDoorListTo.getLingLingId());
        }
        if (doorType == 2 || doorType == 4 || doorType == 7 || doorType == 5) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter.isEnabled()) {

                MiaodouKeyAgent.init(getActivity());
                MiaodouKeyAgent.registerBluetooth(getActivity());
                MiaodouKeyAgent.setMDActionListener(this);
                MiaodouKeyAgent.disableToast();

                showOpenDoor(doorList);
            } else {

                BluetoothManager.turnOnBluetooth();
                new Thread(() -> {

                    for (int i = 0; i < 50; i++) {
                        SystemClock.sleep(100);
                        if (mBluetoothAdapter.isEnabled()) {
                            getActivity().runOnUiThread(() -> {
                                MiaodouKeyAgent.init(getActivity());
                                MiaodouKeyAgent.registerBluetooth(getActivity());
                                MiaodouKeyAgent.setMDActionListener(this);
                                MiaodouKeyAgent.disableToast();
                                if (openDoorDialog == null || !openDoorDialog.isShowing())
                                    showOpenDoor(doorList);
                            });
                            break;
                        }
                    }
                }).start();
            }
        } else
            showOpenDoor(doorList);


    }


    /**
     * 开门对话框展示
     */


    public void showOpenDoor(List<OpenDoorListTo> openDoorList) {
        doorList.clear();
        doorList = openDoorList;

        if (doorType != 1)
            openDoorDialog = new CommonDialog(getActivity(), R.layout.open_door_dialog, R.style.lightDialogTheme);
        else
            openDoorDialog = new CommonDialog(getActivity(), R.layout.open_ling_door_dialog, R.style.lightDialogTheme);

        GridLayout doorLayout = (GridLayout) openDoorDialog.findViewById(R.id.door_layout);
        openDoorCode = (ImageView) openDoorDialog.findViewById(R.id.open_door_code);
        codeDoorName = (TextView) openDoorDialog.findViewById(R.id.codeDoorName);
        animationImage = (ImageView) openDoorDialog.findViewById(R.id.animation_image);
        //   animationImage.setImageResource(R.drawable.open_door_animation);
        codeLayout = (RelativeLayout) openDoorDialog.findViewById(R.id.code_layout);

        Button oneKeyOpen = (Button) openDoorDialog.findViewById(R.id.oneKeyOpen);
        LinearLayout oneKeyButtonLayout = (LinearLayout) openDoorDialog.findViewById(R.id.button_layout);
        Button openShake = (Button) openDoorDialog.findViewById(R.id.openShake);
        openShake.setText(SpUtil.getBoolean("CanShakeOpenDoor") ? Constant.SHAKE_ALREADY_OPEN : Constant.SHAKE_ALREADY_CLOSE);
        openShake.setBackgroundResource(SpUtil.getBoolean("CanShakeOpenDoor") ? R.drawable.open_door_dialog_left_button : R.drawable.open_door_dialog_left_button_un_select);
        openDoorDialog.show();

        if (codeLayout != null) {
            codeLayout.setOnClickListener(v -> System.out.println());
            openDoorDialog.setOnKeyListener((dialog, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK && codeLayout.getVisibility() == View.VISIBLE && doorType != 1) {
                    codeLayout.setVisibility(View.GONE);
                    return true;
                }

                return false;
            });
        }
        openDoorDialog.findViewById(R.id.parent_layout).setOnClickListener(v -> {
            if (codeLayout != null && codeLayout.getVisibility() == View.VISIBLE && doorType != 1) {
                codeLayout.setVisibility(View.GONE);
            } else
                openDoorDialog.dismiss();
        });
        if (doorList == null || doorList.size() == 0)
            return;
        setDoorLayout(openDoorList, doorLayout);

        if (doorType == 1) {
            generateLingCode(generateLingKeyList(openDoorList), "");
        } else if (doorType == 2) {
            generateKeyList(openDoorList);
            openDoorAnimation();
            MiaodouKeyAgent.scanDevices();
        } else if (doorType == 4) {

            generateKeyList(openDoorList);
        } else {

            openDoorDialog.findViewById(R.id.one_key_layout_line).setVisibility(View.VISIBLE);
            oneKeyButtonLayout.setVisibility(View.GONE);
        }


        //一键开门点击
        oneKeyOpen.setOnClickListener(v -> {
            if (doorType == 2) {
                MiaodouKeyAgent.scanDevices();
                openDoorAnimation();
            } else {
                MiaodouKeyAgent.scanDevices();
                openDoorAnimation();
                List<String> lingKeyList = generateLingKeyList(openDoorList);
                String[] keyList = new String[lingKeyList.size()];
                for (int i = 0; i < lingKeyList.size(); i++) {
                    keyList[i] = lingKeyList.get(i);
                }

                openLingDoor(keyList);
            }
        });

        openShake.setOnClickListener(v -> {
            SpUtil.put("CanShakeOpenDoor", !SpUtil.getBoolean("CanShakeOpenDoor"));
            openShake.setText(SpUtil.getBoolean("CanShakeOpenDoor") ? Constant.SHAKE_ALREADY_OPEN : Constant.SHAKE_ALREADY_CLOSE);
            openShake.setBackgroundResource(SpUtil.getBoolean("CanShakeOpenDoor") ? R.drawable.open_door_dialog_left_button : R.drawable.open_door_dialog_left_button_un_select);
        });

    }

    /**
     * 开门界面布局
     */
    @SuppressLint("SetTextI18n")
    private void setDoorLayout(List<OpenDoorListTo> doorList, GridLayout doorLayout) {

        for (int i = 0; i < doorList.size(); i++) {
            OpenDoorListTo doorListTo = doorList.get(i);
            View mView = View.inflate(appContext, R.layout.main_open_door_item, null);
            TextView doorName = (TextView) mView.findViewById(R.id.door_name);
            ((TextView) (mView.findViewById(R.id.open_number))).setText(Constant.OPEN_NUMBER + doorList.get(i).getOpenDoorCount());
            ((TextView) (mView.findViewById(R.id.open_time))).setText(TextUtils.isEmpty(doorList.get(i).getLastOpenTime()) ? "上次开门：暂无" : "上次开门：" + doorList.get(i).getLastOpenTime());

            doorName.setText(doorList.get(i).getDoorName());
            mView.setTag(i);
            mView.setOnClickListener(v -> {
//                openDoorId = doorList.get((Integer) mView.getTag()).getDoorId();
//                supplyId = doorList.get((Integer) mView.getTag()).getSupplierId();

                if ("3".equals(doorList.get((Integer) mView.getTag()).getSupplierId())) {
                    openDoorAnimation();
                    openTalkDoor(doorList.get((Integer) mView.getTag()).getDeviceRegisterId(), doorList.get((Integer) mView.getTag()).getDoorId());

                } else if ("1".equals(doorList.get((Integer) mView.getTag()).getSupplierId())) {
                    List<String> keyList = new ArrayList<>();
                    keyList.add(doorList.get((Integer) mView.getTag()).getSdkKey());
                    generateLingCode(keyList, doorList.get((Integer) mView.getTag()).getDoorName());
                } else if ("2".equals(doorList.get((Integer) mView.getTag()).getSupplierId())) {
                    if (doorListTo.getDevicePid() != null && doorListTo.getMiaodouApartmentId() != null && doorListTo.getMiaodouKey() != null) {
                        MiaodouKeyAgent.openDoor(appContext, "15168234205", doorListTo.getDevicePid(), doorListTo.getMiaodouApartmentId(), doorListTo.getMiaodouKey());
                        openDoorAnimation();
                    }
                }


            });
            if (doorLayout != null)
                doorLayout.addView(mView);

        }


    }

    /**
     * 生成妙都开门钥匙
     */
    public void generateKeyList(List<OpenDoorListTo> infos) {
        ArrayList<MDVirtualKey> keyList = new ArrayList<>();
        for (int i = 0; i < infos.size(); i++)
            if ("2".equals(infos.get(i).getSupplierId()))
                keyList.add(MiaodouKeyAgent.makeVirtualKey(appContext, userInfo.getUserInfoTo().getPhone(), infos.get(i).getDevicePid(), infos.get(i).getMiaodouApartmentId(), infos.get(i).getMiaodouKey()));
        MiaodouKeyAgent.keyList = keyList;

    }


    /**
     * 生成令令开门keyList
     */


    public List<String> generateLingKeyList(List<OpenDoorListTo> openDoorList) {
        //解决摇一摇不能使用冲突,和直接点击开门
        if (doorType == 1) {
            MiaodouKeyAgent.init(getActivity());
            MiaodouKeyAgent.setMDActionListener(this);
            MiaodouKeyAgent.disableToast();
        }
        List<String> keyListTos = new ArrayList<>();

        Observable.from(openDoorList).filter(openDoorListTo -> "1".equals(openDoorListTo.getSupplierId())).subscribe(openDoorListTo -> keyListTos.add(openDoorListTo.getSdkKey()));

        return keyListTos;
    }

    /**
     * 开门动画
     */
    private void openDoorAnimation() {
        parentLayout = openDoorDialog.findViewById(R.id.parent_layout);
        parentLayout.setBackgroundColor(Color.parseColor("#99FFFFFF"));
        doorListLayout = openDoorDialog.findViewById(R.id.door_list_layout);
        doorListLayout.setVisibility(View.GONE);
        animationImage.setVisibility(View.VISIBLE);
//        OutAnimationDrawable animationDrawable = (AnimationDrawable) animationImage.getDrawable();
//        animationDrawable.start();
        OutAnimationDrawable.animateRawManuallyFromXML(R.drawable.open_door_animation, animationImage, null, null);
        canShake = true;
        handler.postDelayed(() -> getActivity().runOnUiThread(() -> {
            disMissOpenDoorAnimation();
            animationImage.clearAnimation();
        }), 3500);

    }

    /**
     * 开门动画消失
     */
    private void disMissOpenDoorAnimation() {
        doorListLayout.setVisibility(View.VISIBLE);
        animationImage.setVisibility(View.GONE);
        parentLayout.setBackgroundColor(Color.parseColor("#80000000"));
    }

    /**
     * 生成令令二维码
     */
    private void generateLingCode(List<String> keys, String doorName) {
        codeLayout.setVisibility(View.VISIBLE);
        if (codeDoorName != null)
            codeDoorName.setText(doorName);
        String qrStr = LLingOpenDoorHandler.getSingle(getActivity()).createDoorControlQR(lingId, keys, 2, 5, 1, "123456AA");
        openDoorCode.setImageBitmap(QRCode.createQRCode(qrStr));

    }

    /**
     * 令令一键开门
     */
    private void openLingDoor(String[] keys) {
        openDoorAnimation();
        LLingOpenDoorConfig config = new LLingOpenDoorConfig(1, keys);
        LLingOpenDoorHandler handler = LLingOpenDoorHandler.getSingle(getActivity());
        handler.doOpenDoor(config, lingListener);
    }

    /**
     * 设置云对讲能否登录
     */
    public void setCloudLogin(List<OpenDoorListTo> doorList) {
        Boolean canLogin = false;
        for (OpenDoorListTo doorTo : doorList) {
            if ("3".equals(doorTo.getSupplierId())) {
                canLogin = true;
                break;

            }
        }
        if (canLogin) {
            try {
                talkLogin();
            } catch (NullPointerException e) {
                System.out.println();
            }

        } else
            CloudIntercomLibrary.getInstance().logout(appContext, null);
    }

    /**
     * 云对讲登录
     */
    private void talkLogin() {


        new Thread() {
            @Override
            public void run() {
                super.run();
                CloudIntercomLibrary.getInstance().login(userInfo.getUserInfoTo().getUserMobile(), "yuedu2017@", getActivity(), new CloudIntercomLibrary.LoginCallBack<UserBean>() {
                    @Override
                    public void onSuccess(UserBean result) {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }


                });
            }
        }.start();
    }

    /**
     * 云对讲开门
     */
    void openTalkDoor(String doorId, String logSid) {
        CloudIntercomLibrary.getInstance().openLock(doorId, appContext, new CloudIntercomLibrary.getDataCallBack<EasemobBean>() {
            @Override
            public void onSuccess(EasemobBean result) {
                playVoice(0);
                openDoorPresenter.sendOpenDoorLog(apartmentInfo.getSid(), userInfo.getSid(), "0", "3", logSid, 1, DateUtil.getDateString(), Constant.ONE_KEY_OPEN_DOOR);
            }

            @Override
            public void onError(Throwable e) {
                openDoorPresenter.sendOpenDoorLog(apartmentInfo.getSid(), userInfo.getSid(), "0", "3", logSid, 0, DateUtil.getDateString(), Constant.ONE_KEY_OPEN_DOOR);
            }
        });

    }

    public void playVoice(int type) {
        //type==1就是摇一摇声音，0为开门成功声音
        getActivity().runOnUiThread(() -> {
            MediaPlayer mp = MediaPlayer.create(appContext, type == 0 ? R.raw.door : R.raw.shake_open_door);
            mp.start();
        });


    }


    //  庙兜开门监听
    @Override
    public void scaningDevices() {
    }

    @Override
    public void findAvaliableKey(MDVirtualKey mdVirtualKey) {
        MiaodouKeyAgent.openDoor(mdVirtualKey, true);
    }

    @Override
    public void onComplete(int i, MDVirtualKey mdVirtualKey) {
        animationImage.setVisibility(View.GONE);
        doorListLayout.setVisibility(View.VISIBLE);
        parentLayout.setBackgroundColor(Color.parseColor("#80000000"));
        switch (i) {
            case MDAction.ACTION_OPEN_DOOR:
                playVoice(0);

                Observable.from(doorList).filter(openDoorListTo -> mdVirtualKey.pid.equals(openDoorListTo.getDevicePid())).subscribe(openDoorListTo -> openDoorPresenter.sendOpenDoorLog(apartmentInfo.getSid(), userInfo.getSid(), "0", "2", openDoorListTo.getDoorId(), 1, DateUtil.getDateString(), null));
                if (doorType == 2)
                    openDoorDialog.dismiss();
                break;
        }
    }

    @Override
    public void onError(int i, int i1) {
    }

    @Override
    public void onError(int i, int i1, MDVirtualKey mdVirtualKey) {

        if (mdVirtualKey != null && mdVirtualKey.pid != null)
            Observable.from(doorList).filter(openDoorListTo -> openDoorListTo.getDevicePid() != null && mdVirtualKey.pid.equals(openDoorListTo.getDevicePid())).subscribe(openDoorListTo -> openDoorPresenter.sendOpenDoorLog(apartmentInfo.getSid(), userInfo.getSid(), "0", "2", openDoorListTo.getDoorId(), 0, DateUtil.getDateString(), null));

    }

    @Override
    public void onOpendoorGetSurpirsed(List<BigSurprise> list) {
    }

    @Override
    public void onDisconnect() {
    }

    private LLingOpenDoorStateListener lingListener = new LLingOpenDoorStateListener() {
        public void onOpenSuccess(String deviceKey, String sn, int openType) {
            getActivity().runOnUiThread(() -> {
                playVoice(0);
                showMessage("开门成功");
                Observable.from(doorList).filter(openDoorListTo -> deviceKey.equals(openDoorListTo.getSdkKey())).subscribe(openDoorListTo -> openDoorPresenter.sendOpenDoorLog(apartmentInfo.getSid(), userInfo.getSid(), "0", "1", openDoorListTo.getDoorId(), 1, DateUtil.getDateString(), null));
            });

        }

        public void onOpenFaild(int errCode, int openType, String deviceKey, String sn, String desc) {
            if (doorType == 4) {
                getActivity().runOnUiThread(() -> {
                            if (deviceKey != null)
                                Observable.from(doorList).filter(openDoorListTo -> openDoorListTo.getSdkKey() != null && deviceKey.equals(openDoorListTo.getSdkKey())).subscribe(openDoorListTo -> openDoorPresenter.sendOpenDoorLog(apartmentInfo.getSid(), userInfo.getSid(), "0", "1", openDoorListTo.getDoorId(), 0, DateUtil.getDateString(), null));

                        }
                );

            }

        }

    };
    private SensorEventListener sensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            // 传感器信息改变时执行该方法
            float[] values = event.values;
            float x = values[0]; // x轴方向的重力加速度，向右为正
            float y = values[1]; // y轴方向的重力加速度，向前为正
            float z = values[2]; // z轴方向的重力加速度，向上为正
            // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
            int medumValue = 19;// 三星 i9250怎么晃都不会超过20，没办法，只设置19了

            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
                if (openDoorDialog != null && openDoorDialog.isShowing() && (doorType == 2 || doorType == 1 || doorType == 4) && animationImage.getVisibility() == View.GONE && SpUtil.getBoolean("CanShakeOpenDoor") && canShake) {
                    playVoice(1);
                    canShake = false;
                    vibrator.vibrate(500);

                    if (doorType == 2) {
                        MiaodouKeyAgent.scanDevices();
                        openDoorAnimation();
                    } else if (doorType == 1) {
                        List<String> lingKeyList = generateLingKeyList(doorList);
                        String[] keyList = new String[lingKeyList.size()];
                        for (int i = 0; i < lingKeyList.size(); i++) {
                            keyList[i] = lingKeyList.get(i);
                        }

                        openLingDoor(keyList);
                    } else {
                        MiaodouKeyAgent.scanDevices();
                        openDoorAnimation();
                        List<String> lingKeyList = generateLingKeyList(doorList);
                        String[] keyList = new String[lingKeyList.size()];
                        for (int i = 0; i < lingKeyList.size(); i++) {
                            keyList[i] = lingKeyList.get(i);
                        }
                        openLingDoor(keyList);
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onPause() {
        super.onPause();
        if (sensorManager != null) {// 取消监听器
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    @Override
    public void accept(String permission) {
        openDoorPresenter.getOpenDoorType(apartmentInfo.getSid(), userInfo.getSid());
        loadingShow();
    }

    @Override
    public void refuse(String permission) {

    }

    private void install() {
        Cockroach.install((thread, throwable) -> new Handler(Looper.getMainLooper()).post(() -> {
            try {
            } catch (Throwable e) {
            }
        }));

    }

/**
 * 设置云对讲是否可以登录,在获取物业服务成功后，如果有一键开门
 */
public void setCloudCanLogin(List<MainMenuTo> data){
      Observable.from(data).filter(mainMenuTo ->"0021".equals(mainMenuTo.getCode()) ).subscribe(mainMenuTo -> {
          openDoorPresenter.getOpenDoorList(apartmentInfo.getSid(),userInfo.getSid());
      });
    }
//门禁分割线================================================================================================
}

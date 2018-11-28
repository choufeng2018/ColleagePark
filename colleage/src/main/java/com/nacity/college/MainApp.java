package com.nacity.college;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.DoorApiClient;
import com.college.common_libs.api.RemoteOpenDoorApiClient;
import com.nacity.college.base.utils.FileUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by xzz on 2017/8/25.
 **/

public class MainApp extends Application {
    public static Context mContext;
    public static MainApp mInstance = null;
    public static final String MAIN_IMAGE_URI = "http://picjoy.joyhomenet.com/";

    //private RefWatcher refWatcher;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = this.getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        ApiClient.getInstance().init(mContext);
        DoorApiClient.getInstance().init(mContext);
        RemoteOpenDoorApiClient.getInstance().init(mContext);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static class KeyValue {
        public final static String KEY_HOME_DATA = "datas";

    }

    public static class DefaultValue {
        public static final String IMAGE_URI = "http://picjoy.joyhomenet.com/";
        public static final String NEWS_DETAIL_URI = "http://prodatacenter.joyhomenet.com:8081/tech/api/article/showArticle/";
        public static final String ACTIVITY_DETAIL_URI = "http://prodatacenter.joyhomenet.com:8081/tech/api/salon/showArticle/";

    }

    public static String getImagePath(String path) {
        if (path == null)
            return "";
        if (path.contains("http"))
            return path;
        return DefaultValue.IMAGE_URI + path;

    }

    public static String getCacheImagePath() {
        return FileUtil.getSdCardPath();
    }
}

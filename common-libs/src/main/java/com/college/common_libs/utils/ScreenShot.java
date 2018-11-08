package com.college.common_libs.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Admin on 2015-11-10
 */
public class ScreenShot {
    private static ScreenShot instance = null;

    public static ScreenShot getInstance() {
        if (instance == null) {
            synchronized (ScreenShot.class){
                if (instance == null) {
                    instance = new ScreenShot();
                }
            }
        }
        return instance;
    }

    @SuppressWarnings("deprecation")
    public  Bitmap takeScreenShot(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        // 将标题 和底部按钮 高度 dp  转化为pix  像素
        float  density = activity.getResources().getDisplayMetrics().density;
        int mButtonHeight = (int)(49 * density +0.5f);
        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top +mButtonHeight ;//计算状态栏 与标题的高度

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight() -mButtonHeight;// 显示屏高度 减去 底部的按钮button 的高度
        // 去掉标题栏
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight );
        view.destroyDrawingCache();
        return b;
    }

    public String saveBitmap(Bitmap bitmap) {
        String path = "";
        String fileName = String.valueOf(System.currentTimeMillis());

        File f = new File(Environment.getExternalStorageDirectory() + "/" + "ScreenShots/",fileName +".png");
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        File file = new File(Environment.getExternalStorageDirectory() + "/" + "ScreenShots/",fileName + ".png");

                try {
                    path = file.getAbsolutePath();
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 95, fos);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        return path;
    }

}

package com.nacity.college.base.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;

import com.nacity.college.base.utils.DateUtil;
import com.nacity.college.base.utils.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Admin on 2015-03-09
 */
public class UploadImage {
    private static UploadImage instance;
    private Context mContext;
    public static String SDPATH = Environment.getExternalStorageDirectory()
            + "/" +"JoyParkImage"+"/";

    private UploadImage(Context context) {
        mContext = context;
    }

    public static UploadImage getInstance(Context context) {
        if (instance == null) {
            instance = new UploadImage(context);
        }
        instance.mContext = context;
        return instance;
    }

    public  static String getImageUri(String filePath) {
        String path = "";
        Bitmap bitmap = null;
        try {
            int degree = readPictureDegree(filePath);
            File file = new File(filePath);
            bitmap = decodeFile(file);
            bitmap =rotatingImageView(degree, bitmap);

            String fileName = String.valueOf(System.currentTimeMillis());
            File file1 = new File(SDPATH +fileName+".jpg");

            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            File saveFile = new File(SDPATH +fileName+".jpg");
            saveFile.createNewFile();
            path = saveFile.getAbsolutePath();
            FileOutputStream out = null;
            out = new FileOutputStream(saveFile);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
        return path;
    }

    public static  Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            int REQUIRED_SIZE = 720;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale > REQUIRED_SIZE ) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String saveBitmap(Bitmap bitmap) {
        String path = "";
        if (FileUtil.IsExistsSDCard()) {
            File file = new File(getCacheImagePath(),
                    DateUtil.getDateString("yyyyMMddHHmmss") + ".jpg");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    path = file.getAbsolutePath();
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                    fos.flush();
                    fos.close();

                    if (!bitmap.isRecycled()) {
                        bitmap.recycle();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                file.delete();
            }
        }
        return path;
    }

    public static String getCacheImagePath() {
        return FileUtil.getSdCardPath();
    }




    /**
     * 获取旋转图片角度
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 图片旋转
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap rotatingImageView(int angle , Bitmap bitmap) {
        //旋转图片 动作

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Log.d("angle2=", angle + "");
        // 创建新的图片

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}

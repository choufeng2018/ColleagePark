package com.nacity.college.base.utils.image;

/**
 * Created by xzz on 2016/1/12.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nacity.college.R;
import com.nacity.college.MainApp;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * Created by xz on 2016/1/8.
 */
public class ImageLoadleUtil {
    private static Bitmap bitmap;
    static String type="j";
    private static ImageLoader loader;
    private static DisplayImageOptions option;

    public  static ImageLoaderConfiguration config(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader/Cache/neighbor");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                        // .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null) // Can slow ImageLoader, use it carefully (Better don't use it)/设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100) //缓存的文件数量
                .discCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();//开始构建
        return config;
    }
    public static DisplayImageOptions getImageloaderOption(){
        DisplayImageOptions   options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.neighbor_load_bg)         // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.neighbor_load_bg)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.neighbor_load_bg)      // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(0))  // 设置成圆角图片
                .build();
        return  options;
    }
    public static void getImageLoaderOption2(int round,Context context,String url,ImageView imageView){
        ImageLoader.getInstance().init(ImageLoadleUtil.config(context));
        DisplayImageOptions options= new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_launcher)         // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(round))  // 设置成圆角图片
                .build();
         ImageLoader loader=ImageLoader.getInstance();
        loader.displayImage(url,imageView,options);
    }
    public static DisplayImageOptions getImageLoaderOptionRound(int round){

        DisplayImageOptions options= new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_launcher)         // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(5,5))  // 设置成圆角图片

                .build();
      return options;

    }
    public static ImageLoader getLoader(Context context){
        ImageLoader.getInstance().init(ImageLoadleUtil.config(context));
        DisplayImageOptions options= new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_launcher)         // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(0))  // 设置成圆角图片
                .build();
        ImageLoader loader=ImageLoader.getInstance();
       return loader;
    }

    public static String getImageType(String filePath) {
        new Thread(() -> {

        }).start();
        FileInputStream is = null;
        String value = null;
        try {
            is = new FileInputStream(filePath);
            byte[] b = new byte[3];
            is.read(b, 0, b.length);
            value = bytesToHexString(b);
        } catch (Exception e) {
        } finally {
            if(null != is) {
                try {
                    is.close();
                } catch (IOException e) {}
            }
        }
        if("FFD8FF".equals(value)){
            return "jpg";
        } else if("FFD8FF".equals(value)){
            return "jpg";
        } else if("47494638".equals(value)||"474946".equals(value)){
            return "gif";
        } else if("424D".equals(value)){
            return "bmp";
        }
        return value;
    }
    private static String bytesToHexString(byte[] src){
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }

    public static ImageLoader getImageLoaderOption5(Context context){

        ImageLoader.getInstance().init(ImageLoadleUtil.config(context));
        DisplayImageOptions options= new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.neighbor_load_bg)         // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.neighbor_load_bg)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.neighbor_load_bg)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                .build();
        ImageLoader loader=ImageLoader.getInstance();



        return loader;
    }
    public static void initLoader(Context appContext){
        ImageLoaderConfiguration config = ImageLoadleUtil.config(appContext);
        ImageLoader.getInstance().init(config);

        option = ImageLoadleUtil.getImageloaderOption();
        loader = ImageLoadleUtil.getImageLoaderOption5(appContext);

    }

    public static void getNeighborImageType(Context appContext,String url, ImageView postImage, TextView postImageType){

        loader.displayImage(url, postImage, option, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {


                if (loader.getDiscCache() != null && loader.getDiscCache().get(url) != null) {
                    String path = loader.getDiscCache().get(url).getPath();
                    type= ImageLoadleUtil.getImageType(path);
                    if (appContext==null||postImage==null||imageUri==null)
                        return;

                    if (type.equals("gif")){
                        postImageType.setVisibility(View.VISIBLE);
                        postImageType.setText("动图");
                        Glide.with(MainApp.mContext).load(imageUri).into(postImage);
                    }else if(loadedImage.getHeight()/loadedImage.getWidth()>=2){
                        postImageType.setVisibility(View.VISIBLE);
                        postImageType.setText("长图");
                        ImageLoadleUtil.loader.displayImage(imageUri,postImage);

                    }else {
                        ImageLoadleUtil.loader.displayImage(imageUri,postImage);
                        postImageType.setVisibility(View.GONE);
                    }


                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }
}

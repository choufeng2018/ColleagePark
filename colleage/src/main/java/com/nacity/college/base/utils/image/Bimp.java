package com.nacity.college.base.utils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;

import com.bumptech.glide.gifencoder.AnimatedGifEncoder;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bimp {
    public static int max = 0;
    public static List<Bitmap>bitmapList=new ArrayList<>();
    public final static ArrayList<ImageItem> tempSelectBitmap = new ArrayList<>();   //选择的图片的临时列表
    public static  List<String>pathList=new ArrayList<>();
    public static Bitmap revitionImageSize(String path) throws IOException {
        //FileInputStream fis = new FileInputStream(path);
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 1000) && (options.outHeight >> i <= 1000)) {
                in = new BufferedInputStream(new FileInputStream(
                        new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    @SuppressWarnings("deprecation")
    public static String getImageUri(String filePath,boolean isNeighbor) {

        String path = "";
        Bitmap bitmap = null;
   if(filePath.endsWith("gif")&&isNeighbor){
      path=filePath;


//            bitmapList.clear();
//            pathList.clear();
//            bitmapList = showGif2(filePath);
//
////				for(int i=0;i<bitmapList.size();i++){
////					try {
////						saveFile(bitmapList.get(i), "neighbor" + i);
////
////					} catch (IOException e) {
////						e.printStackTrace();
////					}
////
////
////				}
//
//
//            try {
//
//                path=createGif("joyhome",pathList.size(), ViewPagerCompat.LayoutParams.MATCH_PARENT-30, ViewPagerCompat.LayoutParams.MATCH_PARENT-40);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }else {

            try {
                int degree = readPictureDegree(filePath);
                File file = new File(filePath);
                bitmap = decodeFile(file);
                bitmap = rotaingImageView(degree, bitmap);
                String fileName = String.valueOf(System.currentTimeMillis());

                File file1 = new File(FileUtils.SDPATH + fileName + ".jpg");

                if (!file1.getParentFile().exists()) {
                    file1.getParentFile().mkdirs();
                }
                File saveFile = new File(FileUtils.SDPATH + fileName + ".jpg");
                saveFile.createNewFile();
                path = saveFile.getAbsolutePath();
                FileOutputStream out = null;
                out = new FileOutputStream(saveFile);
                if (bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
                }
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bitmap != null) {
                    bitmap.recycle();
                }
            }
        }

        return path;

    }

    public static void deleteFolder(String dir) {

        File delfolder = new File(dir);
        File oldFile[] = delfolder.listFiles();
        try {
            for (int i = 0; i < oldFile.length; i++) {
                if (oldFile[i].isDirectory()) {
                    deleteFolder(dir + oldFile[i].getName() + "//"); //递归清空子文件夹
                }
                oldFile[i].delete();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    public static Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            int REQUIRED_SIZE = 720;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale >= REQUIRED_SIZE) {
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

    /**
     * 获取旋转图片角度
     *
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
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
     *
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Log.d("angle2=", angle + "");
        // 创建新的图片

        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap getBitmap(String filePath) {

        int degree = readPictureDegree(filePath);
        File file = new File(filePath);

        Bitmap bitmap = decodeFile(file);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            if (bitmap != null) {
                bitmap = rotaingImageView(degree, bitmap);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return bitmap;
    }
    public static List<Bitmap> showGif2(String path)
    {
        GifImageDecoder gifDecoder = new GifImageDecoder();
        List<Bitmap>bitmapList=new ArrayList<>();
//            File f = new File("/sdcard/gif.gif");
//        InputStream in = null;
//        try {
//            in = new FileInputStream(f);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            System.out.println("未发现文件");
//        }
//        byte b[]=new byte[(int)f.length()];     //创建合适文件大小的数组
//        try {
//            in.read(b);    //读取文件中的内容到b[]数组
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("IO异常");
//        }
//        try {
//            gifDecoder.read(in);
//        } catch (IOException e) {
//            System.out.println("IO异常--------");
//
//        }
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path));

            gifDecoder.read(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //    in.close();

        //这是Gif图片资源

        int size =gifDecoder.getFrameCount();
        bitmapList.clear();
        System.out.println(size+"gifSize");
        for(int i=0;i<size;i++)
        {

            bitmapList.add(gifDecoder.getFrame(i));
            System.out.println(gifDecoder.getBitmap().getByteCount()/1024+"bitmapSize------------------------");
//                gifFrame.nextFrame();
        }
        return bitmapList;

    }
    public static String createGif(String filename, int fps, int width, int height) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AnimatedGifEncoder localAnimatedGifEncoder = new AnimatedGifEncoder();
        localAnimatedGifEncoder.start(baos);//start
        localAnimatedGifEncoder.setRepeat(0);//设置生成gif的开始播放时间。0为立即开始播放
        localAnimatedGifEncoder.setDelay(fps);

        if (bitmapList.size() > 0) {
            for (int i = 0; i < bitmapList.size(); i++) {

                Bitmap resizeBm = ImageUtil.resizeImage(bitmapList.get(i), width, height);
                System.out.println(resizeBm.getByteCount()/1024+"resize------------------------------------");
                localAnimatedGifEncoder.addFrame(resizeBm);

            }
        }
        localAnimatedGifEncoder.finish();//finish

        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/LiliNote");
        if (!file.exists()) file.mkdir();
        String path = Environment.getExternalStorageDirectory().getPath() + "/LiliNote/" + filename + ".gif";
        FileOutputStream fos = new FileOutputStream(path);
        baos.writeTo(fos);
        baos.flush();
        fos.flush();
        baos.close();
        fos.close();

        return path;
    }
    public static void saveFile(Bitmap bm, String fileName) throws IOException {
        String path ="/sdcard/Note1/";
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }

        File myCaptureFile = new File(path + fileName);
        myCaptureFile.createNewFile();
        pathList.add(myCaptureFile.getAbsolutePath());
        //	comp(bm);
//		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
//		bm.compress(Bitmap.CompressFormat.JPEG, 50, bos);
//		System.out.println(bm.getByteCount() / 1024 + "bm第二次---------------");
//		bos.flush();
//		bos.close();
    }
    private static Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        //此时返回bm为空
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return bitmap;//压缩好比例大小后再进行质量压缩
    }
    private static Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        //判断如果图片大于1M,进行压缩避免在生成图片
        //（BitmapFactory.decodeStream）时溢出
        if( baos.toByteArray().length / 1024>1024) {
            baos.reset();//重置baos即清空baos
            //这里压缩50%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return bitmap;//压缩好比例大小后再进行质量压缩
    }
    private static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        //循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while ( baos.toByteArray().length / 1024>100) {
            //重置baos即清空baos
            baos.reset();
            //这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;//每次都减少10
        }
        //把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        //把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }
}

package com.nacity.college.base.utils;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {
	private static final int BUFFER = 8192;

	public static final String TAG = "FileUtils";
	private String SDPATH;

	public FileUtil() {
		// 得到当前外部存储设备的目录
		if (IsExistsSDCard()) {
			SDPATH = Environment.getExternalStorageDirectory().getPath();
		} else {
			SDPATH = null;
		}

	}

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

	
	/**
	 * 获取SDCard路径
	 * 
	 * @return
	 */
	public static String getSdCardPath() {
		if (IsExistsSDCard()) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			return sdcardDir.getPath();

		} else {
			File root = Environment.getRootDirectory();
			return root.getPath();
		}
	}

	/**
	 * 是否存在SDCARD
	 * 
	 * @return
	 */
	public static boolean IsExistsSDCard() {
		String sdState = Environment.getExternalStorageState();// 获得sd卡的状态
		if (sdState.equals(Environment.MEDIA_MOUNTED)) { // 判断SD卡是否存在
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断文件或目录是否存在于外部存储器
	 * 
	 * @param file
	 *            文件或目录
	 * @return
	 */
	public static boolean isExists(File file) {
		boolean isExists = file.exists();
		DebugUtils.debug(TAG, file + " isExists:" + isExists);
		return isExists;
	}

	/**
	 * 判断文件或目录是否存在于外部存储器
	 * 
	 * @param path
	 *            完整路径名
	 * @return
	 */
	public static boolean isExists(String path) {

		File file = new File(path);
		return isExists(file);
	}

	

	/**
	 * 获取剩余空间
	 * 
	 * @return
	 * @throws IOException
	 */
	public long getSDFreeSize() throws IOException {
		StatFs sf = new StatFs(getSdCardPath());
		long blockSize = sf.getBlockSize();
		long availableBlocks = sf.getAvailableBlocks();
		DebugUtils.debug(TAG, "可用空间:" + blockSize * availableBlocks);
		return blockSize * availableBlocks;
	}

	/**
	 * 获取所有空间
	 * 
	 * @return
	 * @throws IOException
	 */
	public long getSDAllSize() throws IOException {
		StatFs sf = new StatFs(getSdCardPath());
		long blockSize ;
		long blockCount ;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
		     blockSize = sf.getBlockSizeLong();
			  blockCount = sf.getBlockCountLong();
		}else {
			 blockSize = sf.getBlockSize();
			 blockCount = sf.getBlockCount();
		}

		DebugUtils.debug(TAG, "全部空间:" + blockSize * blockCount);
		return blockSize * blockCount;
	}

	/**
	 * 创建文件到外部存储
	 * 
	 * @param path
	 *            文件路径
	 * @param fileName
	 *            文件名
	 * @return
	 * @throws IOException
	 */
	public File createFileToSD(String path, String fileName) throws IOException {

		File file = createDirToSD(path);
		file = new File(file, fileName);
		DebugUtils.debug(TAG, "Create File To SD:" + file);
		file.createNewFile();
		return file;
	}

	/**
	 * 创建文件到外部存储根目录
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 * @throws IOException
	 */
	public File creteFileToSD(String fileName) throws IOException {
		return createFileToSD(getSdCardPath(), fileName);
	}

	/**
	 * 创建目录到外部存储
	 * 
	 * @param dirName
	 *            目录名
	 * @return
	 * @throws IOException
	 */
	public File createDirToSD(String dirName) throws IOException {

		File dir = new File(getSdCardPath() + dirName);
		if (isExists(dir)) {
			return dir;
		}
		boolean isCreated = dir.mkdirs();
		DebugUtils.debug(TAG, "CreateDirToSD:" + dir + " " + isCreated);
		if (!isCreated) {
			throw new IOException("创建目录失败!");
		}
		return dir;
	}

	/**
	 * 删除外部存储中的文件或目录
	 * 
	 * @param pathName
	 */
	public void deleteFileFromSD(String pathName) throws IOException {
		File file = new File(getSdCardPath() + pathName);
		boolean b = deleteFileFromSD(file);
		DebugUtils.debug(TAG, "Delete File By SD:" + file + " " + b);
	}

	/**
	 * 删除SdCard文件
	 * 
	 * @param file
	 * @return
	 */
	public boolean deleteFileFromSD(File file) {

		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					this.deleteFileFromSD(files[i]);
				}
			}
			boolean isDeleted = file.delete();
			DebugUtils.debug(TAG, "删除文件:" + file + " " + isDeleted);
			return isDeleted;
		} else {
			DebugUtils.debug(TAG, "删除文件:" + file + " 文件不存在!");
			return true;
		}
	}

	/**
	 * 从SdCard读取文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readTextFile(File file) throws IOException {
		String text = null;
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			text = readTextInputStream(is);
			;
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return text;
	}

	/**
	 * 从SdCard读取文件
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String readTextInputStream(InputStream is) throws IOException {
		StringBuilder strbuffer = new StringBuilder();
		String line;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is));
			while ((line = reader.readLine()) != null) {
				strbuffer.append(line).append("\r\n");
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return strbuffer.toString();
	}

	public static void writeTextFile(File file, String str) throws IOException {
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(new FileOutputStream(file));
            out.write(str.getBytes());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public static void copyFile(File sourceFile, File targetFile)
			throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
			byte[] buffer = new byte[BUFFER];
			int length;
			while ((length = inBuff.read(buffer)) != -1) {
				outBuff.write(buffer, 0, length);
			}
			outBuff.flush();
		} finally {
			if (inBuff != null) {
				inBuff.close();
			}
			if (outBuff != null) {
				outBuff.close();
			}
		}
	}

	/**
	 * Java文件操作 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * Java文件操作 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getFileNameForPath(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('/');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * Java文件操作 获取文件名
	 * 
	 * @param url
	 * @return
	 */
	public static String getFileNameForUrl(String url) {
		if ((url != null) && (url.length() > 0)) {
			int dot = url.lastIndexOf('/');
			if ((dot > -1) && (dot < (url.length() - 1))) {
				return url.substring(dot + 1);
			}
		}
		return url;
	}

	/**
	 * Java文件操作 获取不带扩展名的文件名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}
}

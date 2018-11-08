package com.nacity.college.base.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class DebugUtils {
	public static final String TAG = "DebugUtil";
	private static final boolean DEBUG = true;

	public static void toastShort(Context context, String content) {
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}

	public static void toastLong(Context context, String content) {
		Toast.makeText(context, content, Toast.LENGTH_LONG).show();
	}

	public static void debug(String msg) {
		if (DEBUG) {
			Log.d(TAG, msg);
		}
	}

	public static void debug(String tag, String msg) {
		if (DEBUG) {
			Log.d(tag, msg);
		}
	}

	public static void error(String msg) {
		if (DEBUG) {
			Log.e(TAG, msg);
		}
	}

	public static void error(String tag, String msg) {
		if (DEBUG) {
			Log.e(tag, msg);
		}
	}

	public static void error(String tag, Exception e) {
		if (null == e) {
			return;
		}
		if (DEBUG) {
			Log.e(tag, e.getMessage());
			e.printStackTrace();
		}

	}

	public static void error(String tag, String errMsg, Exception e) {
		if (null == e) {
			return;
		}
		if (DEBUG) {
			Log.e(tag, errMsg + e.getMessage());
			e.printStackTrace();
		}

	}
}

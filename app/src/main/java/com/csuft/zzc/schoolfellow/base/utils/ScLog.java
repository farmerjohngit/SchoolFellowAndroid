package com.csuft.zzc.schoolfellow.base.utils;

import android.util.Log;

/**
 * Created by wangzhi on 16/3/19.
 */
public class ScLog {


    public static final String TAG = "ScLog";

    public static void i(String tag, Object msg) {
        Log.i(tag, msg == null ? "null" : msg.toString());
    }

    public static void i(Object msg) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement element = stackTrace[3];
//        Log.i(element.getClassName(), msg == null ? "null" : msg.toString());
        Log.i(TAG, msg == null ? "null" : msg.toString());
    }

    public static void e(String tag, Object msg) {
        Log.e(tag, msg == null ? "null" : msg.toString());
    }

    public static void e(Object msg) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement element = stackTrace[3];
        Log.e(element.getClassName(), msg == null ? "null" : msg.toString());
//        Log.e(TAG, msg == null ? "null" : msg.toString());
    }
}

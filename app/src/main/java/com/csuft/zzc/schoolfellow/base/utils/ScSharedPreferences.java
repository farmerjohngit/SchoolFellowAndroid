package com.csuft.zzc.schoolfellow.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by wangzhi on 16/5/6.
 */
public class ScSharedPreferences {
    private SharedPreferences sharedPreferences;
    public static final String PREFS_READ_WRITE = "PREFS_READ_WRITE";
    SharedPreferences mSharedPreferences;
    private final SharedPreferences.Editor mEditor;
    static Set<String> set;

    public ScSharedPreferences(String name) {
        mSharedPreferences = AppContextGetter.instance().get().getSharedPreferences(name, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }


    public void write(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public String get(String key, String value) {
        return mSharedPreferences.getString(key, value);
    }
}

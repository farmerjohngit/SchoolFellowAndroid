package com.csuft.zzc.schoolfellow.base.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by wangzhi on 16/5/26.
 */
public class FileUtil {
    public static final String SCHOOL_FELLOW_DIR = "SchoolFellow";
    public static final String IMG_DIR = SCHOOL_FELLOW_DIR + "/img/";

    public static void initDir() {
        File sd = Environment.getExternalStorageDirectory();
        File scName = new File(sd, SCHOOL_FELLOW_DIR);
        scName.mkdir();
        File imgName = new File(sd, IMG_DIR);
        imgName.mkdir();
    }

    public static String getImgDir() {
        File sd = Environment.getExternalStorageDirectory();
        File imgName = new File(sd, IMG_DIR);
        return imgName.getAbsolutePath();
    }
}

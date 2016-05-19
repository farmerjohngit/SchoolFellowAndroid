package com.csuft.zzc.schoolfellow.host.util;

/**
 * Created by wangzhi on 16/5/7.
 */
public class DataFormatUtil {
    public static String formatRank(int rank) {
        String format = "";
        switch (rank) {
            case 0:
                format = "在校生";
                break;
            case 1:
                format = "应届生";
                break;
            case 2:
                format = "教师";
                break;
        }
        return format;
    }
}

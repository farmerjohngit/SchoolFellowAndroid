package com.csuft.zzc.schoolfellow.regi;

import java.util.regex.Pattern;

/**
 * Created by wangzhi on 16/5/10.
 */
public class RegiCheckUtil {
    public static boolean isFormatAccountOrPwd(String str) {
        Pattern pattern = Pattern.compile("[0-9a-zA-Z]{7,12}");
        return pattern.matcher(str).matches();
    }

    public static boolean isFormatName(String str) {
        Pattern pattern = Pattern.compile("([\\u4e00-\\u9fa5]{2,10})");
        return pattern.matcher(str).matches();
    }


}

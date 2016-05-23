package com.csuft.zzc.schoolfellow.base.utils;

import java.util.List;

/**
 * Created by wangzhi on 16/5/19.
 */
public class ArrayUtil {
    public static <T> T getFirstItem(List<T> list) {
        if (list == null || list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }
}

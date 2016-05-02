package com.csuft.zzc.schoolfellow.base.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by wangzhi on 16/5/3.
 */
public class AssertUtil {

    public static void assertNotNull(Object obj) {
        if (obj == null) {
            throw new RuntimeException("obj is null");
        }
    }
}

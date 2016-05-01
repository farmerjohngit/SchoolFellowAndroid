package com.csuft.zzc.schoolfellow.base.net;

import com.csuft.zzc.schoolfellow.base.data.BaseData;

/**
 * Created by wangzhi on 16/4/26.
 */
public interface CallBack<T extends BaseData> {
    void onSuccess(T data);

    void onFailure(int code, String error);
}

package com.csuft.zzc.schoolfellow.base.net;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by wangzhi on 16/4/26.
 */
public abstract class AbsNetExecutorFactory {

    public abstract INetExecutor creatExector();
}

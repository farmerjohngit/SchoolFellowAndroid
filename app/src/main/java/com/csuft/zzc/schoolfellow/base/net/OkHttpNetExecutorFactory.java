package com.csuft.zzc.schoolfellow.base.net;

/**
 * Created by wangzhi on 16/4/26.
 */
public class OkHttpNetExecutorFactory extends AbsNetExecutorFactory {
    @Override
    public INetExecutor creatExector() {
        return OkHttpNetExecutor.getInstance();
    }
}

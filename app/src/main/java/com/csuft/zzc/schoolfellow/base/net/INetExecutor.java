package com.csuft.zzc.schoolfellow.base.net;

import com.squareup.picasso.RequestCreator;

import java.io.IOException;

/**
 * Created by wangzhi on 16/4/26.
 */
public interface INetExecutor {
    //todo 同步请求

    IRequestCreator geneRequestCreator();

    void requestData(HttpRequest apiRequest) throws IOException;

    void enqueueDataRequest(HttpRequest apiRequest);
}

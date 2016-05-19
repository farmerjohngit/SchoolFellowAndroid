package com.csuft.zzc.schoolfellow.base.net;

/**
 * Created by wangzhi on 16/4/26.
 */
public interface IRequestCreator<T> {
    T createRequest(HttpRequest httpRequest);

    T geneGetRequest(HttpRequest apiRequest);

    T genePostRequest(HttpRequest apiRequest);
}
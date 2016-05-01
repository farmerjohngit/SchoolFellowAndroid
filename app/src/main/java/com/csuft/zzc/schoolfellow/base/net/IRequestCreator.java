package com.csuft.zzc.schoolfellow.base.net;

import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * Created by wangzhi on 16/4/26.
 */
public interface IRequestCreator<T> {
    T createGetRequest(HttpRequest httpRequest);

    T geneGetRequest(HttpRequest apiRequest);

    T genePostRequest(HttpRequest apiRequest);
}
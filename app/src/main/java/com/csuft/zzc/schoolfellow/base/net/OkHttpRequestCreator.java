package com.csuft.zzc.schoolfellow.base.net;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.util.Map;

/**
 * Created by wangzhi on 16/4/26.
 */
public class OkHttpRequestCreator implements IRequestCreator {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Override
    public Object createGetRequest(HttpRequest httpRequest) {
        Request request = null;
        if (httpRequest.getMethod() == ApiRequest.GET) {
            request = geneGetRequest(httpRequest);
        } else {
            request = genePostRequest(httpRequest);
        }
        return request;
    }


    //todo 拼接url等
    public Request geneGetRequest(HttpRequest apiRequest) {
        return new Request.Builder().url(apiRequest.getUrl()).build();
    }

    //todo
    public Request genePostRequest(HttpRequest apiRequest) {
//        RequestBody body = RequestBody.create(JSON, "");
//        return new Request.Builder().url(apiRequest.getUrl()).post(body).build();
        return null;
    }
}

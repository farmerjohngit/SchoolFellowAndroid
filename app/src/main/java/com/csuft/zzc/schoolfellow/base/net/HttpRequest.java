package com.csuft.zzc.schoolfellow.base.net;

import com.csuft.zzc.schoolfellow.base.data.BaseData;

import java.util.Map;

/**
 * Created by wangzhi on 16/4/26.
 */
public class HttpRequest {
    private ApiRequest apiRequest;

    public HttpRequest(Builder builder) {
        apiRequest = builder.apiRequest;
    }


    public CallBack getCallBack() {
        return apiRequest.getCallBack();
    }

    public String getUrl() {
        return apiRequest.getUrl();
    }

    public int getMethod() {
        return apiRequest.getMethod();
    }

    public Map<String, String> getQueryParams() {
        return apiRequest.getMethod() == ApiRequest.GET ? apiRequest.getParams() : null;
    }

    public Map<String, String> getFormParams() {
        return apiRequest.getMethod() == ApiRequest.POST ? apiRequest.getParams() : null;
    }


    public Class<?> getCallBackClazz() {
        return apiRequest.getCallBackClazz();
    }

    public static class Builder {
        private ApiRequest apiRequest;

        public Builder apiRequest(ApiRequest apiRequest) {
            this.apiRequest = apiRequest;
            return this;
        }

        public HttpRequest build(){
            return new HttpRequest(this);
        }
    }
}

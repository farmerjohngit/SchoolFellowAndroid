package com.csuft.zzc.schoolfellow.base.net;

import com.csuft.zzc.schoolfellow.base.data.BaseData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhi on 16/4/26.
 */
public class ApiRequest {

    public static final int GET = 0;
    public static final int POST = 1;

    private String url;
    private int method;
    private Map<String, String> params;
    private CallBack callBack;
    private Class<? extends BaseData> callBackClazz;

    public ApiRequest(Builder builder) {
        this.url = builder.url;
        this.params = builder.params;
        this.callBack = builder.callBack;
        this.callBackClazz = builder.callBackClazz;
        this.method = builder.method;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public String getUrl() {
        return url;
    }

    public int getMethod() {
        return method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public Class<? extends BaseData> getCallBackClazz() {
        return callBackClazz;
    }

    public static class Builder {

        private String url;
        private int method;
        private CallBack callBack;
        private Map<String, String> params;
        private Class<? extends BaseData> callBackClazz;

        public Builder method(int method) {
            this.method = method;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder callback(CallBack callBack) {
            this.callBack = callBack;
            return this;
        }

        public <T extends BaseData> Builder callBackClazz(Class<T> callBackClazz) {
            this.callBackClazz = callBackClazz;
            return this;
        }

        public ApiRequest build() {
            return new ApiRequest(this);
        }
    }
}

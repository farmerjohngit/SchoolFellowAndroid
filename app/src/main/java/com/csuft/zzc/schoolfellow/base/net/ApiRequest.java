package com.csuft.zzc.schoolfellow.base.net;

import com.csuft.zzc.schoolfellow.base.data.BaseData;
import com.squareup.okhttp.MediaType;

import java.util.List;
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
    private List<String> fileParams;
    private CallBack callBack;
    private Class<? extends BaseData> callBackClazz;
    private MediaType type;

    public ApiRequest(Builder builder) {
        this.url = builder.url;
        this.params = builder.params;
        this.callBack = builder.callBack;
        this.callBackClazz = builder.callBackClazz;
        this.method = builder.method;
        this.fileParams = builder.fileParams;
        this.type = builder.type;
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

    public List<String> getFileParams() {
        return fileParams;
    }

    public MediaType getType() {
        return type;
    }

    public Class<? extends BaseData> getCallBackClazz() {
        return callBackClazz;
    }

    public static class Builder {

        private String url;
        private MediaType type;
        private List<String> fileParams;
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

        public Builder type(MediaType type) {
            this.type = type;
            return this;
        }

        public Builder fileParams(List<String> fileParams) {
            this.fileParams = fileParams;
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

package com.csuft.zzc.schoolfellow.base.net;

import com.csuft.zzc.schoolfellow.base.data.BaseData;

import java.util.Map;

/**
 * Created by wangzhi on 16/4/26.
 */
public class BaseApi {

//            public static final String HOST_URL = "http://104.224.133.227:3000";
    public static final String HOST_URL = "http://192.168.56.1:3000";
    AbsNetExecutorFactory netExecutorFactory;

    private static class SingletonHolder {
        private static final BaseApi INSTANCE = new BaseApi();
    }

    private BaseApi() {

        netExecutorFactory = getDefaultExectuorFactory();
    }


    public static BaseApi getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void setNetExecutorFactory(AbsNetExecutorFactory netExecutorFactory) {
        this.netExecutorFactory = netExecutorFactory;
    }

    public AbsNetExecutorFactory getDefaultExectuorFactory() {
        return new OkHttpNetExecutorFactory();
    }
//    String run(String url) throws IOException {


//        Response response = client.newCall(requestData).execute();
//        return response.body().string();
//    }

    public <T extends BaseData> void get(String url, Map<String, String> params, Class<T> clazz, CallBack<T> callBack) {
        ApiRequest apiRequest = new ApiRequest.Builder()
                .method(ApiRequest.GET)
                .url(url)
                .params(params)
                .callback(callBack)
                .callBackClazz(clazz)
                .build();
        request(apiRequest);
    }

    public <T extends BaseData> void post(String url, Map<String, String> params, Class<T> clazz, CallBack<T> callBack) {


    }

    public void request(ApiRequest apiRequest) {
        if (apiRequest == null) {
            throw new RuntimeException("apiRequest is null ");
        }
        requestInternal(apiRequest);
    }

    public void requestInternal(ApiRequest apiRequest) {
        enqueueRequest(apiRequest);
    }

    public void apiReqToRealReq() {
    }

    public void enqueueRequest(ApiRequest apiRequest) {
        INetExecutor netExecutor = netExecutorFactory.creatExector();
        HttpRequest httpRequest = new HttpRequest.Builder().apiRequest(apiRequest).build();
        netExecutor.enqueueDataRequest(httpRequest);
    }


}

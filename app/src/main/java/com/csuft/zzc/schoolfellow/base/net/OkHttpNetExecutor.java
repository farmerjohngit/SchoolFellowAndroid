package com.csuft.zzc.schoolfellow.base.net;

import android.os.Handler;
import android.os.Looper;

import com.csuft.zzc.schoolfellow.base.data.BaseData;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by wangzhi on 16/4/26.
 */
public class OkHttpNetExecutor implements INetExecutor {
    OkHttpClient sOkHttpClient = new OkHttpClient();
    Handler mHandler = new Handler(Looper.getMainLooper());
    Gson gson = new Gson();
    private static final String TAG = "OkHttpNetExecutor";

    private static class SingletonHolder {
        private static final OkHttpNetExecutor INSTANCE = new OkHttpNetExecutor();
    }

    public static OkHttpNetExecutor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public IRequestCreator geneRequestCreator() {
        return new OkHttpRequestCreator();
    }

    @Override
    public void requestData(final HttpRequest httpRequest) throws IOException {
        IRequestCreator requestCreator = geneRequestCreator();
        Request request = (Request) requestCreator.createRequest(httpRequest);

        sOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpRequest.getCallBack().onFailure(-1, e.getMessage());
                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {
                ScLog.i(TAG, "onRes" + Thread.currentThread().equals(Looper.getMainLooper().getThread()));
                Class clazz = httpRequest.getCallBackClazz();

                String bodyString = response.body().string();
                ScLog.i(TAG, bodyString);


                try {
                    BaseData baseData = null;
                    if (clazz != null) {
                        baseData = (BaseData) gson.fromJson(bodyString, clazz);
                    }
                    final BaseData finalBaseData = baseData;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (httpRequest.getCallBack() != null) {
                                httpRequest.getCallBack().onSuccess(finalBaseData);
                            }
                        }
                    });
                } catch (final Exception e) {
                    e.printStackTrace();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (httpRequest.getCallBack() != null) {
                                httpRequest.getCallBack().onFailure(-2, e.getMessage());
                            }
                        }
                    });
                }
            }
        });

    }


    @Override
    public void enqueueDataRequest(HttpRequest request) {
        //简单处理
        try {
            requestData(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

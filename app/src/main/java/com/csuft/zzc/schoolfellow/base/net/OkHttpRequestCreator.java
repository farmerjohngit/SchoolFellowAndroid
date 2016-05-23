package com.csuft.zzc.schoolfellow.base.net;

import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wangzhi on 16/4/26.
 */
public class OkHttpRequestCreator implements IRequestCreator {

    private static final String TAG = "OkHttpRequestCreator";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    @Override
    public Object createRequest(HttpRequest httpRequest) {
        Request request = null;
        if (httpRequest.getMethod() == ApiRequest.GET) {
            request = geneGetRequest(httpRequest);
        } else {
            request = genePostRequest(httpRequest);
        }
        return request;
    }


    public Request geneGetRequest(HttpRequest apiRequest) {
        Map<String, String> map = apiRequest.getQueryParams();
        String url = apiRequest.getUrl();
        if (map != null) {
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();

            if (iterator.hasNext()) {
                url += "?";
            }
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                String key = entry.getKey();
                String value = entry.getValue();
                if (key == null) {
                    key = "";
                }
                if (value == null) {
                    value = "";
                }
                url += key + "=" + value;
                if (iterator.hasNext()) {
                    url += "&";
                }
            }
        }

        ScLog.i(TAG, "geneGetRequest url: " + url);
        return new Request.Builder().url(url).build();
    }

    //todo
    public Request genePostRequest(HttpRequest apiRequest) {
        Map<String, String> map = apiRequest.getFormParams();
        if (map == null) {
            return new Request.Builder().url(apiRequest.getUrl()).build();
        }
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        RequestBody body = null;
        if (apiRequest.getType() != null && apiRequest.getFileFormParams() != null) {
            MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
            for (String filePath : apiRequest.getFileFormParams()) {
                builder.addFormDataPart("upload", filePath, RequestBody.create(apiRequest.getType(), new File(filePath)));
            }
            body = builder.build();
        } else {

            FormEncodingBuilder builder = new FormEncodingBuilder();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                builder.add(entry.getKey(), entry.getValue());
            }
            body = builder.build();
        }

        return new Request.Builder().url(apiRequest.getUrl()).post(body).build();
    }


}

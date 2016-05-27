package com.csuft.zzc.schoolfellow.base.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * Created by wangzhi on 16/3/10.
 */
public class WebImageView extends ImageView {
    private static final String TAG = "WebImageView";

    public WebImageView(Context context) {
        super(context);
    }

    public WebImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setImageUrl(String url) {
        setImageUrl(url, -1, -1, false);
    }

    public void setImageUrl(String url, boolean circle) {
        setImageUrl(url, -1, -1, circle);
    }

    public void setImageUrl(String url, int width, int height) {
        setImageUrl(url, width, height, false);
    }

    public void setImageUrl(String url, int width, int height, boolean circle) {
        if (TextUtils.isEmpty(url)) {
            ScLog.e("url is null");
            setImageResource(android.R.color.transparent);
            return;
        }
        RequestCreator creator = Picasso.with(getContext()).load(url);
        if (width >= 0 && height >= 0) {
            creator.resize(width, height).centerCrop();
        }
        if (circle) {
            creator.transform(new PicassoCirclTransform());
        }
        creator.into(this, new Callback() {

            @Override
            public void onSuccess() {
                ScLog.i(TAG, "onSuccess");
            }

            @Override
            public void onError() {
                ScLog.i(TAG, "onError");
            }
        });
    }
}

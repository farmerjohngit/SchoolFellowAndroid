package com.csuft.zzc.schoolfellow.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by wangzhi on 16/3/10.
 */
public class WebImageView extends ImageView {
    public WebImageView(Context context) {
        super(context);
    }

    public WebImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setImageUrl(String url) {
        Picasso.with(getContext())
                .load(url)
                .into(this);
    }


    public void setImageUrl(String url,int width,int height){
        Picasso.with(getContext())
                .load(url)
                .resize(width, height)
                .centerCrop()
                .into(this);
    }
}

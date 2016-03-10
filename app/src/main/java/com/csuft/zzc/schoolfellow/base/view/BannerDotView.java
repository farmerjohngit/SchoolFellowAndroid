package com.csuft.zzc.schoolfellow.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.csuft.zzc.schoolfellow.R;

/**
 * Created by wangzhi on 16/3/10.
 */
public class BannerDotView extends View {
    public BannerDotView(Context context) {
        super(context);
        init();
    }

    public BannerDotView(Context context, AttributeSet attrs) {
        super(context, attrs, R.style.banner_dot_style);
        init();
    }

    public BannerDotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
//        setLayoutParams(new ViewGroup.LayoutParams(5,5));
//        setBackgroundResource(R.drawable.banner_dot_normal);
    }

    public void setBannerSelected(boolean selected) {
        setBackgroundResource(selected ? R.drawable.banner_dot_focused : R.drawable.banner_dot_normal);
    }

}

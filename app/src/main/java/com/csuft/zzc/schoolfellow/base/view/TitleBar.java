package com.csuft.zzc.schoolfellow.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by wangzhi on 16/3/10.
 */
public class TitleBar extends LinearLayout {

    protected View centerView;
    protected View leftView;
    protected View RightView;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleBar(Context context) {
        super(context);
        init();
    }

    private void init() {
    }


}

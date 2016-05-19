package com.csuft.zzc.schoolfellow.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.csuft.zzc.schoolfellow.base.utils.ScreenUtil;

/**
 * 一个封装的很垃圾的topbar
 * Created by wangzhi on 16/3/10.
 */
public class TopBar extends LinearLayout {

    protected View mCenterView;
    protected View mLeftView;
    protected View mRightView;

    protected LayoutParamsFactory mLayoutParamsFactory;

    public interface LayoutParamsFactory {
        LinearLayout.LayoutParams generateParams(ViewGroup.LayoutParams oldParams, MODE mode);

    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TopBar(Context context) {
        super(context);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        try {
            mLeftView = getChildAt(0);
            mLeftView.setLayoutParams(generateParams(mLeftView.getLayoutParams(), MODE.LEFT));
            mCenterView = getChildAt(1);
            mCenterView.setLayoutParams(generateParams(mCenterView.getLayoutParams(), MODE.CENTER));
            mRightView = getChildAt(2);
            mRightView.setLayoutParams(generateParams(mRightView.getLayoutParams(), MODE.RIGHT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LayoutParams generateParams(ViewGroup.LayoutParams oldParams, MODE mode) {
        return mLayoutParamsFactory == null
                ? generateDefaultParams(oldParams, mode)
                : mLayoutParamsFactory.generateParams(oldParams, mode);
    }


    public enum MODE {
        LEFT, RIGHT, CENTER;
    }

    public LayoutParams generateDefaultParams(ViewGroup.LayoutParams oldParams, MODE mode) {

//        AssertUtil.assertNotNull(oldParams);

        LinearLayout.LayoutParams layoutParams = null;
        switch (mode) {
            case CENTER:
                layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.weight = 1;
                break;
            case RIGHT:
            case LEFT:
                if (oldParams == null) {
                    layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                } else {
                    layoutParams = new LinearLayout.LayoutParams(oldParams);
                    if (!(oldParams instanceof MarginLayoutParams)) {
                        layoutParams.leftMargin = ScreenUtil.instance().dip2px(8);
                        layoutParams.rightMargin = ScreenUtil.instance().dip2px(8);
                    }

                }

                break;

        }

        return layoutParams;
    }


    public void setCenterView(View centerView) {
        if (centerView == null) {
            return;
        }
        this.mCenterView = centerView;
        addView(mCenterView, generateParams(mCenterView.getLayoutParams(), MODE.CENTER));
    }

    public void setLeftView(View leftView) {
        if (leftView == null) {
            return;
        }
        this.mLeftView = leftView;
        addView(mLeftView, generateParams(mLeftView.getLayoutParams(), MODE.LEFT));
    }

    public void setRightView(View rightView) {
        if (rightView == null) {
            return;
        }
        this.mRightView = rightView;
        addView(mRightView, generateParams(mRightView.getLayoutParams(), MODE.RIGHT));
    }

    public View getLeftView() {
        return mLeftView;
    }

    public View getCenterView() {
        return mCenterView;
    }

    public View getRightView() {
        return mRightView;
    }

    public void setLayoutParamsFactory(LayoutParamsFactory layoutParamsFactory) {
        mLayoutParamsFactory = layoutParamsFactory;
    }

    public void setOnLeftClickListener(OnClickListener listener) {
        if (mLeftView != null) {
            mLeftView.setOnClickListener(listener);
        }
    }

    public void setOnRightClickListener(OnClickListener listener) {
        if (mRightView != null) {
            mRightView.setOnClickListener(listener);
        }
    }

    public void setOnCenterClickListener(OnClickListener listener) {
        if (mCenterView != null) {
            mCenterView.setOnClickListener(listener);
        }
    }
}

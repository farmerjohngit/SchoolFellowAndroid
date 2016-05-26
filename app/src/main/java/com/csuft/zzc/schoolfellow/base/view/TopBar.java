package com.csuft.zzc.schoolfellow.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.csuft.zzc.schoolfellow.base.utils.ScreenUtil;

/**
 * 一个封装的很垃圾的topbar
 * Created by wangzhi on 16/3/10.
 */
public class TopBar extends RelativeLayout {

    protected View mCenterView;
    protected View mLeftView;
    protected View mRightView;

    protected LayoutParamsFactory mLayoutParamsFactory;

    public interface LayoutParamsFactory {
        RelativeLayout.LayoutParams generateParams(ViewGroup.LayoutParams oldParams, MODE mode);

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

        LayoutParams layoutParams = null;
        if (oldParams == null) {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            layoutParams = new LayoutParams((MarginLayoutParams) oldParams);
            if (!(oldParams instanceof MarginLayoutParams)) {
                layoutParams.leftMargin = ScreenUtil.instance().dip2px(8);
                layoutParams.rightMargin = ScreenUtil.instance().dip2px(8);
            }

        }
        switch (mode) {
            case CENTER:
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                break;
            case RIGHT:
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                break;
            case LEFT:
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
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

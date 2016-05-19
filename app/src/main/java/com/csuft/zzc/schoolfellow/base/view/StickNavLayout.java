package com.csuft.zzc.schoolfellow.base.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;

/**
 * Created by wangzhi on 15/12/22.
 */
public class StickNavLayout extends LinearLayout implements AbsPullToRefresh.IRefreshAble {

    private static final String TAG = "StickNavLayout";
    private ViewGroup mTop;

    private ViewGroup mNav;

    private ViewPager mViewPager;

    private ViewGroup mInnerScroollView;

    private OverScroller mScroller;

    private int mTouchSlop;
    private int mMaxSpeed;
    private int mMiniSpeed;
    private boolean mControl;

    int mWidth = -1;
    int mHeight = -1;

    private VelocityTracker mVelocity;

    private float mLastY;
    private float mLastX;
    boolean cancelFlag = false;

    public StickNavLayout(Context context) {
        super(context);
        init();
    }

    public StickNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StickNavLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init() {
        setOrientation(VERTICAL);
        mScroller = new OverScroller(getContext());
        mMaxSpeed = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
        mMiniSpeed = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mTouchSlop = 0;
        mVelocity = VelocityTracker.obtain();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTop = (ViewGroup) findViewById(R.id.id_stick_top);
        mNav = (ViewGroup) findViewById(R.id.id_stick_nav);
        mViewPager = (ViewPager) findViewById(R.id.id_stick_pager);

    }


    private void getInnerScrollView() {
        if (mViewPager.getAdapter() instanceof FragmentPagerAdapter) {
            FragmentPagerAdapter pagerAdapter = (FragmentPagerAdapter) mViewPager.getAdapter();
            Fragment fragment = (Fragment) pagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
            mInnerScroollView = (ViewGroup) fragment.getView().findViewById(R.id.id_stick_inner_scroll);
        }
    }


    private boolean isInnerScrollViewInTop() {
        getInnerScrollView();
        if (mInnerScroollView instanceof ScrollView) {
            return (mInnerScroollView).getScrollY() == 0;
        } else if (mInnerScroollView instanceof RecyclerView) {
            RecyclerView rv = (RecyclerView) mInnerScroollView;
            LinearLayoutManager lm = (LinearLayoutManager) rv.getLayoutManager();
            View topView = lm.findViewByPosition(lm.findFirstVisibleItemPosition());
            int topMargin = 0;
            if (topView.getLayoutParams() instanceof MarginLayoutParams) {
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) topView.getLayoutParams();
                topMargin = marginLayoutParams.topMargin;
            }
            return topView == null ? true : (topView.getTop() == topMargin && lm.findFirstVisibleItemPosition() == 0);
        }
        return false;
    }

    boolean measureFlag = false;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams navParams = mNav.getLayoutParams();
        navParams.height = mNav.getChildAt(0).getMeasuredHeight();
        ViewGroup.LayoutParams layoutParams = mViewPager.getLayoutParams();
        int pagerHeight = getMeasuredHeight() - mNav.getMeasuredHeight();
        if (measureFlag) {
            pagerHeight = mHeight - mNav.getMeasuredHeight();
        }
        layoutParams.height = pagerHeight;
        Log.e(TAG, "onMeasure   height:  " + MeasureSpec.getSize(heightMeasureSpec)
                + " getMeasuredHeight: " + getMeasuredHeight()
                + " nav getMeasuredHeight:  " + mNav.getMeasuredHeight()
                + " pagerHeight " + pagerHeight);

        if (!measureFlag) {
            mWidth = MeasureSpec.getSize(widthMeasureSpec);
            mHeight = MeasureSpec.getSize(heightMeasureSpec);
            setMeasuredDimension(mWidth, mHeight);
        }

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getY();
                mLastX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = ev.getY() - mLastY;
                if (!mControl && isInnerScrollViewInTop() && dy > 0) {
                    Log.i(TAG, "dispatchTouchEvent  cancel ");
                    //自己接管
                    MotionEvent event1 = MotionEvent.obtain(ev);
                    event1.setAction(MotionEvent.ACTION_DOWN);
                    dispatchTouchEvent(event1);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;

            case MotionEvent.ACTION_CANCEL:
                cancelFlag = true;
                break;
        }

        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean flag = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getY();
                mLastX = ev.getX();
                mControl = true;
                measureFlag = true;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = ev.getY() - mLastY;
                float dx = ev.getX() - mLastX;
                if (mTouchSlop < Math.abs(dy) && Math.abs(dy) > Math.abs(dx)) {
                    if (getScrollY() < mNav.getTop() || (isInnerScrollViewInTop() && dy >= 0)) {
                        flag = true;
                        mControl = true;
                        mLastY = ev.getY();
                        mLastX = ev.getX();
                    } else {
                        mControl = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mControl = false;
                break;
        }

        Log.i(TAG, "onInterceptTouchEvent    " + flag + ev.getAction());
        return flag;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initVelocityTrackerIfNotExists();
        mVelocity.addMovement(event);
        float nowY = event.getY();
        float dy = nowY - mLastY;
        Log.i(TAG, "onTouchEvent    " + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = event.getY();
                mLastX = event.getX();
                return true;
            case MotionEvent.ACTION_MOVE:
                if (mTouchSlop < Math.abs(dy)) {
                    scrollByY((int) -dy);
                    mLastY = event.getY();
                    mLastX = event.getX();
                    Log.i(TAG, "move" + -dy);
                    ScLog.i(TAG, "isInnerScrollViewInTop " + isInnerScrollViewInTop());
                    if (getScrollY() < mNav.getTop() || (isInnerScrollViewInTop() && dy > 0)) {
                    } else {
                        //给子view接管
                        Log.i(TAG, "cancel " + getScrollY() + "  " + mNav.getTop());
                        MotionEvent event1 = MotionEvent.obtain(event);
                        event1.setAction(MotionEvent.ACTION_DOWN);
                        dispatchTouchEvent(event1);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mVelocity.computeCurrentVelocity(1000, mMaxSpeed);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return super.onTouchEvent(event);
    }

    private void scrollByY(int dy) {
        scrollToY(dy + getScrollY());
    }

    private void scrollToY(int toY) {
        ScLog.i(TAG, "scrollToY " + toY);
        if (toY > mTop.getHeight()) {
            scrollTo(0, mTop.getHeight());
        } else if (toY >= 0) {
            scrollTo(0, toY);
        } else if (toY < 0) {
            scrollTo(0, 0);
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocity == null) {
            mVelocity = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocity != null) {
            mVelocity.recycle();
            mVelocity = null;
        }
    }


    @Override
    public boolean onTop() {
        ScLog.i(TAG, "onTop " + getScrollY());
        return getScrollY() <= 0;
    }
}

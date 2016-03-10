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
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;

import com.csuft.zzc.schoolfellow.R;

/**
 * Created by wangzhi on 15/12/22.
 */
public class StickNavLayout extends LinearLayout {

    public static final String TAG = "MyStickNavLayout";
    private ViewGroup mTop;

    private ViewGroup mNav;

    private ViewPager mViewPager;

    private ViewGroup mInnerScroollView;

    private OverScroller mScroller;

    private int mTouchSlop;
    private int mMaxSpeed;
    private int mMiniSpeed;
    private boolean mControl;


    private VelocityTracker mVelocity;


    private float mLastY;

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
            LinearLayoutManager lm= (LinearLayoutManager) rv.getLayoutManager();
            return lm.findViewByPosition(lm.findFirstVisibleItemPosition()).getTop()==0 && lm.findFirstVisibleItemPosition()==0;
        }
        return false;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        ViewGroup.LayoutParams navParams = mNav.getLayoutParams();
        navParams.height = mNav.getChildAt(0).getMeasuredHeight();

        ViewGroup.LayoutParams layoutParams = mViewPager.getLayoutParams();
        int navHeight = getMeasuredHeight() - mNav.getMeasuredHeight();
        layoutParams.height = navHeight;

        Log.e(TAG, "ONMEASURE h     " + navHeight);

//        setMeasuredDimension();

    }


    boolean cancelFlag = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = ev.getY() - mLastY;

                if (!mControl && isInnerScrollViewInTop() && dy > 0) {
//                        Log.i(TAG, "onInterceptTouchEvent false "+(getScrollY() < mNav.getTop())+"  "+dy);

                    Log.i(TAG, "dispatchTouchEvent  cancel ");
                    //自己接管
//                    Log.i(TAG, "cancel "+getScrollY()+"  "+mNav.getTop());
                    MotionEvent event1 = MotionEvent.obtain(ev);
//                    event1.setAction(MotionEvent.ACTION_CANCEL);
//                    dispatchTouchEvent(event1);
                    event1.setAction(MotionEvent.ACTION_DOWN);
                    dispatchTouchEvent(event1);
                } else {


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
                mControl = true;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = ev.getY() - mLastY;
                if (mTouchSlop < Math.abs(dy)) {
                    if (getScrollY() < mNav.getTop() || (isInnerScrollViewInTop() && dy >= 0)) {
                        flag = true;
                        mControl = true;
                        mLastY = ev.getY();
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


    private boolean isDrag = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initVelocityTrackerIfNotExists();
        mVelocity.addMovement(event);
        float nowY = event.getY();
        float dy = nowY - mLastY;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = event.getY();
                return true;

            case MotionEvent.ACTION_MOVE:

                if (mTouchSlop < Math.abs(dy)) {
                    scrollByY((int) -dy);
                    mLastY = event.getY();
                    Log.i(TAG, "move ");
                    if (getScrollY() < mNav.getTop() || (isInnerScrollViewInTop() && dy > 0)) {
//                        Log.i(TAG, "onInterceptTouchEvent false "+(getScrollY() < mNav.getTop())+"  "+dy);

                    } else {
                        //给子view接管
                        Log.i(TAG, "cancel " + getScrollY() + "  " + mNav.getTop());
                        MotionEvent event1 = MotionEvent.obtain(event);
//                        event1.setAction(MotionEvent.ACTION_CANCEL);
//                        dispatchTouchEvent(event1);
                        event1.setAction(MotionEvent.ACTION_DOWN);
                        dispatchTouchEvent(event1);

                    }
                }


                break;

            case MotionEvent.ACTION_UP:
                isDrag = false;
                mVelocity.computeCurrentVelocity(1000, mMaxSpeed);
                int velocityY = (int) mVelocity.getYVelocity();
//                if (Math.abs(velocityY) > mMiniSpeed) {
//                    mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTop.getHeight());
//                    invalidate();
//                }
//                recycleVelocityTracker();
                break;
            case MotionEvent.ACTION_CANCEL:
                isDrag = false;
                break;

        }


        return super.onTouchEvent(event);
    }


    private void scrollByY(int dy) {
        scrollToY(dy + getScrollY());
    }

    private void scrollToY(int toY) {
        if (toY >= 0) {
            scrollTo(0, toY);
        }
        if (toY > mTop.getHeight()) {
            scrollTo(0, mTop.getHeight());
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

}

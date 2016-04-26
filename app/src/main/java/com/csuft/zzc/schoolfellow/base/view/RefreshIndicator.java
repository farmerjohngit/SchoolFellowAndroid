package com.csuft.zzc.schoolfellow.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.csuft.zzc.schoolfellow.base.utils.ScLog;

/**
 * Created by wangzhi on 16/3/19.
 */
public class RefreshIndicator extends View implements IRefreshHeader {


    private float mPercent;

    private RectF mInnerRect;

    private Paint mPaint;
    private Paint mBackPaint;

    public RefreshIndicator(Context context) {
        super(context);
        init();
    }

    public RefreshIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void init() {

        mBackPaint = new Paint();
        mBackPaint.setFlags(1);
        mBackPaint.setStyle(Paint.Style.FILL);
        mBackPaint.setAntiAlias(true);
        mBackPaint.setColor(Color.WHITE);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);                       //设置画笔为无锯齿
        mPaint.setColor(Color.BLACK);                    //设置画笔颜色
        mPaint.setStrokeWidth((float) 3.0);              //线宽
        mPaint.setStyle(Paint.Style.STROKE);

        mInnerRect = new RectF();
    }


    public void setPercent(float percent) {
        mPercent = percent;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mInnerRect.set(mPaint.getStrokeWidth() + 2, mPaint.getStrokeWidth() + 2, getWidth() - mPaint.getStrokeWidth() - 2, getHeight() - mPaint.getStrokeWidth() - 2);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mBackPaint);

        canvas.drawArc(mInnerRect, 0, mPercent * 0.7f * 360, false, mPaint);    //绘制圆弧

    }


    public void refreshAnimation() {
//        ViewCompat.animate(this).rotation(10)
//                .setInterpolator(new FastOutSlowInInterpolator())
//                .setDuration(300).start();
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,getWidth()/2,getHeight()/2);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(500);
        rotateAnimation.setRepeatCount(-1);
//        rotateAnimation.setRepeatMode();
        startAnimation(rotateAnimation);
//       startAnimation();
    }

    public void abortAnimation(){
        clearAnimation();
    }
}

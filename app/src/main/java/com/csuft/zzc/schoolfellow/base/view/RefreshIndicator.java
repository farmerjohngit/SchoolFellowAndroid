package com.csuft.zzc.schoolfellow.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

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
        mInnerRect.set(mPaint.getStrokeWidth() +2, mPaint.getStrokeWidth()+2, getWidth() - mPaint.getStrokeWidth() - 2, getHeight() - mPaint.getStrokeWidth() - 2);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mBackPaint);

        canvas.drawArc(mInnerRect, 0, mPercent * 360, false, mPaint);    //绘制圆弧

    }
}

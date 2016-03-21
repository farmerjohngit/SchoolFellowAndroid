package com.csuft.zzc.schoolfellow.base.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.csuft.zzc.schoolfellow.R;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by 6a209 on 16/3/19.
 */
public class EasyToast {
    static final String TAG = "EasyToast";

    private static final int ANIMATION_DURATION = 250;
    static  Map<String, ToastStyle> sToastStyleData = new HashMap<>();
    View mView;
    ViewGroup mParentView;
    ToastStyle mCurrentToastStyle;

    private String mCurTypeKey;
    private String mText;

    private TextView mTextView;
    private Button mButton;

    private static final int HIDE_MSG = 0;

    public static final int LENGTH_SHORT = -1;
    public static final int LENGTH_LONG = 0;

    public int mDuration;

    int mStatusHeight;



    static Handler sHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case HIDE_MSG:
                    ((EasyToast)message.obj).hide();
                    return true;
            }
            return false;
        }
    });


    private EasyToast(Context ctx, ViewGroup parentView, String text, int duration){
        mParentView = parentView;
        mView = LayoutInflater.from(ctx).inflate(R.layout.easy_toast, mParentView);
        mText = text;
        mStatusHeight = getStatusBarHeight(ctx);

        mTextView = (TextView)mView.findViewById(R.id.text);
        mButton = (Button)mView.findViewById(R.id.action);

        if(LENGTH_LONG == duration){
            mDuration = 3500;
        }else if(LENGTH_SHORT == duration){
            mDuration = 2000;
        }else {
            mDuration = duration;
        }

    }

    public static void register(String key, ToastStyle toastStyle){
       sToastStyleData.put(key, toastStyle);
    }
    public static EasyToast make(Context ctx, String text, int duration){

        ViewGroup parent = null;
        try{
            Activity act = (Activity)ctx;
            parent = (ViewGroup)act.getWindow().getDecorView();
        }catch(Exception e){
        }

        return new EasyToast(ctx, parent, text, duration);
    }


    public void show(){
        if(null == mParentView){
            return;
        }

        mCurrentToastStyle = getCurrentStyle();
        if(null == mCurrentToastStyle){
            Log.e(TAG, "current toast style is null");
            return;
        }
        if(null != mView.getParent()){
            ((ViewGroup)mView.getParent()).removeView(mView);
        }
        mParentView.addView(mView);
        mTextView.setText(mText);
        mTextView.setCompoundDrawablesWithIntrinsicBounds(mCurrentToastStyle.mTextIcon, null, null, null);
        mButton.setBackgroundDrawable(mCurrentToastStyle.mActionIcon);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        mView.setBackgroundColor(mCurrentToastStyle.mBgColor);
        animateViewIn();
    }

    public void hide(){
        animateViewOut();
    }

    private ToastStyle getCurrentStyle(){
        ToastStyle toastStyle = sToastStyleData.get(mCurTypeKey);
        return toastStyle;
    }


    private void animateViewIn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            mView.setTranslationY(-mView.getHeight());
            ViewCompat.setTranslationY(mView, -mView.getHeight());
            ViewCompat.animate(mView)
                    .translationY(mStatusHeight)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .setDuration(ANIMATION_DURATION)
                    .setListener(new ViewPropertyAnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(View view) {
                            Message msg = sHandler.obtainMessage();
                            msg.what = HIDE_MSG;
                            msg.obj = this;
                            sHandler.sendMessageDelayed(msg, mCurrentToastStyle.mShowDuration);
                        }
                    })
                    .start();
        }
    }

    private void animateViewOut() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ViewCompat.animate(mView)
                    .translationY(-mView.getHeight())
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .setDuration(ANIMATION_DURATION).
                    start();
        }
    }


    public static class ToastStyle{
        private int mBgColor;
        private Drawable mTextIcon;
        private Drawable mActionIcon;
        private int mTextSiz;
        private int mShowDuration;
    }

    /**
     * 获取状态栏高度
     * @return
     */
    static int sHeight = 0;
    private int getStatusBarHeight(Context context) {
        if(sHeight != 0){
            return sHeight;
        }
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        sHeight = sbar;
        return sbar;
    }
}

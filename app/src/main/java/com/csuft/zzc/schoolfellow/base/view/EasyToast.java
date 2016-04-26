package com.csuft.zzc.schoolfellow.base.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    static Map<String, ToastStyle> sToastStyleData = new HashMap<>();
    View mView;
    ViewGroup mParentView;
    ToastStyle mCurrentToastStyle;

    private String mCurStyleKey;
    private String mText;

    private TextView mTextView;
    private ImageView mActionButton;

    private static final int HIDE_MSG = 0;

    public static final int LENGTH_SHORT = -1;
    public static final int LENGTH_LONG = 0;


    public int mDuration;

    int mStatusHeight;


    static Handler sHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case HIDE_MSG:
                    ((EasyToast) message.obj).hide();
                    return true;
            }
            return false;
        }
    });


    private EasyToast(Context ctx, ViewGroup parentView, String styleKey, String text, int duration) {
        mParentView = parentView;
        mView = LayoutInflater.from(ctx).inflate(R.layout.easy_toast, mParentView, false);
        mText = text;
        mStatusHeight = getStatusBarHeight(ctx);
        mCurStyleKey = styleKey;

        mTextView = (TextView) mView.findViewById(R.id.text);
        mActionButton = (ImageView) mView.findViewById(R.id.action);
        if (LENGTH_LONG == duration) {
            mDuration = 3500;
        } else if (LENGTH_SHORT == duration) {
            mDuration = 2000;
        } else {
            mDuration = duration;
        }
        Object o = new Object();
        o.hashCode();
    }

    public static void register(String key, ToastStyle toastStyle) {
        sToastStyleData.put(key, toastStyle);
    }

    public static EasyToast make(View view, String text, String styleKey, int duration) {
        ViewGroup parent = findSuitableParent(view);
        return new EasyToast(view.getContext(), parent, styleKey, text, duration);
    }

    public static EasyToast make(Activity activity, String text, String styleKey, int duration) {
        ViewGroup parent = null;
        try {
            parent = (ViewGroup) activity.getWindow().getDecorView();
        } catch (Exception e) {
        }
        return new EasyToast(activity, parent, styleKey, text, duration);
    }


    public void show() {
        if (null == mParentView) {
            return;
        }
        mCurrentToastStyle = getCurrentStyle();
        if (null == mCurrentToastStyle) {
            Log.e(TAG, "current toast style is null");
            return;
        }
        if (null != mView.getParent()) {
            ((ViewGroup) mView.getParent()).removeView(mView);
        }
        mParentView.addView(mView);
        mTextView.setTextSize(mCurrentToastStyle.textSize);
        mTextView.setTextColor(mCurrentToastStyle.textColor);
        mTextView.setGravity(mCurrentToastStyle.textGravity);
        setTextViewMaxLen(mCurrentToastStyle.maxTextLen);
        mTextView.setText(mText);
        mTextView.setCompoundDrawablesWithIntrinsicBounds(mCurrentToastStyle.textIcon, null, null, null);
        mActionButton.setImageDrawable(mCurrentToastStyle.actionIcon);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("wangzhi", "onClick");
                hide();
            }
        });
        mView.setBackgroundColor(mCurrentToastStyle.bgColor);
        animateViewIn();
    }

    public EasyToast setTextViewMaxLen(int maxLength) {
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        mTextView.setFilters(fArray);
        return this;
    }

    public void hide() {
        animateViewOut();
    }

    private ToastStyle getCurrentStyle() {
        ToastStyle toastStyle = sToastStyleData.get(mCurStyleKey);
        return toastStyle;
    }


    private static ViewGroup findSuitableParent(View view) {
        if (view == null) {
            return null;
        }
        ViewGroup fallback = null;

        do {
            String className = view.getClass().getName();
            if (className.endsWith("PhoneWindow$DecorView") && className.startsWith("com.android") && view instanceof ViewGroup) {
                fallback = (ViewGroup) view;
            }

            if (view != null) {
                ViewParent parent = view.getParent();
                Log.i("wangzhi", "view " + view.getId() + "  <> " + view.getClass());
                view = parent instanceof View ? (View) parent : null;
            }

        } while (view != null);

        return fallback;
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
                            msg.obj = EasyToast.this;
                            sHandler.sendMessageDelayed(msg, mDuration);
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
                    .setDuration(ANIMATION_DURATION).setListener(new ViewPropertyAnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(View view) {
                    if (mParentView != null) {
                        mParentView.removeView(mView);
                        Log.i("wangzhi", "animation end");

                    }
                }


            }).start();
        }
    }


    public static class ToastStyle {
        public Drawable textIcon;
        public Drawable actionIcon;
        public int bgColor = Color.WHITE;
        public int textColor = Color.BLACK;
        public int textSize = 16;
        public int textGravity = 16;
        public int maxTextLen = 10;

        public ToastStyle() {
        }

        public ToastStyle(Drawable textIcon, Drawable actionIcon, int bgColor, int textColor, int textSize, int textGravity, int maxTextLen) {
            this.textIcon = textIcon;
            this.actionIcon = actionIcon;
            this.bgColor = bgColor;
            this.textColor = textColor;
            this.textSize = textSize;
            this.textGravity = textGravity;
            this.maxTextLen = maxTextLen;
        }
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    static int sHeight = 0;

    private int getStatusBarHeight(Context context) {
        if (sHeight != 0) {
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

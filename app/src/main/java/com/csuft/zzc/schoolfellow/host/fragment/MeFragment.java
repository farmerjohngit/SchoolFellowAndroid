package com.csuft.zzc.schoolfellow.host.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.fragment.BaseFragment;
import com.csuft.zzc.schoolfellow.base.view.EasyToast;

import java.util.Random;

/**
 * Created by wangzhi on 16/3/10.
 */
public class MeFragment extends BaseFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Drawable textDrawable = null;
        Drawable actionDrawable = null;

        textDrawable = ResourcesCompat.getDrawable(getActivity().getResources(), R.mipmap.net, null);
        actionDrawable = ResourcesCompat.getDrawable(getActivity().getResources(), R.mipmap.ok, null);

        EasyToast.ToastStyle toastStyle = new EasyToast.ToastStyle();
        toastStyle.textSize = 16;
        toastStyle.bgColor = Color.WHITE;
//        toastStyle.textIcon = textDrawable;
        toastStyle.actionIcon = actionDrawable;
        toastStyle.maxTextLen = 10;
//        toastStyle.textGravity= Gravity.C;
        EasyToast.register("default", toastStyle);

        textDrawable = ResourcesCompat.getDrawable(getActivity().getResources(), R.mipmap.ok, null);
        actionDrawable = ResourcesCompat.getDrawable(getActivity().getResources(), R.mipmap.ok, null);
//        toastStyle = new EasyToast.ToastStyle(Color.WHITE, textDrawable
//                , actionDrawable, 16,0);
        //        EasyToast.register("default1", toastStyle);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected View createContentView(LayoutInflater inflater, @Nullable ViewGroup container) {

        return inflater.inflate(R.layout.me_fra, container, false);
    }

    int i = 0;

    @Override
    protected void initView() {
        final Button button = (Button) mContentView.findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar snackbar =
//                        Snackbar.make(
//                                mContentView,
//                                "Snack Bar",
//                                Snackbar.LENGTH_SHORT);
//
//                snackbar.show();
//                Random random = new Random();
                EasyToast.make(button, "h"+i++, "default", 2600).show();

//                Toast.makeText(getActivity(), "hello" + i++, Toast.LENGTH_SHORT).show();
//                int time = 1000 + random.nextInt(2000);
//                if (time >= 2000) {
//                    EasyToast.make(MeFragment.this.getActivity(), "default" + time, "default", time).show();
//                } else {
//                    EasyToast.make(MeFragment.this.getActivity(), "default" + time, "default1", time).show();
//                }
            }
        });
    }
}

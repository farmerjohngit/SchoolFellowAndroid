package com.csuft.zzc.schoolfellow.base.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wangzhi on 16/3/9.
 */
public abstract class BaseFragment extends Fragment {


    protected View mContentView;
    protected boolean mIsReuse;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (null != this.mContentView) {
            this.mIsReuse = true;
            return this.mContentView;
        } else {
            this.mIsReuse = false;
            mContentView = this.createContentView(inflater, container);
            this.initView();
            return this.mContentView;
        }
    }

    protected abstract View createContentView(LayoutInflater inflater, @Nullable ViewGroup container);

    protected abstract void initView();
}

package com.csuft.zzc.schoolfellow.base.fragment;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csuft.zzc.schoolfellow.R;

import java.util.List;

/**
 * Created by wangzhi on 16/5/4.
 */
public abstract class RecyclerViewFragment<T> extends BaseFragment {

    protected RecyclerView mRecyclerView;
    protected List<T> dataList;

    @Override
    protected final View createContentView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        return mRecyclerView;
    }

    @Override
    protected void initView() {

    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
//    public void setAdapter(RecyclerView.Adapter adapter) {
//        if (mContentView != null) {
//            RecyclerView recyclerView = (RecyclerView) mContentView;
//            recyclerView.setAdapter(adapter);
//        }
//    }
}

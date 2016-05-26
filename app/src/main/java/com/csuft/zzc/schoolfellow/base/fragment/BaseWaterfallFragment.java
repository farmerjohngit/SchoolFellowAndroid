package com.csuft.zzc.schoolfellow.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.view.WaterFallRecyclerView;

/**
 * Created by wangzhi on 16/3/11.
 */
public abstract class BaseWaterfallFragment extends BaseFragment {

    private static final String TAG = "BaseWaterfallFragment";
    protected RecyclerView.Adapter mAdapter;
    protected WaterFallRecyclerView mRecyclerView;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ScLog.i("onSaveInstanceState");
//        outState.put
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        ScLog.i("onViewStateRestored");
    }

//    public static BaseWaterfallFragment create(RecyclerView.Adapter adapter) {
//        BaseWaterfallFragment waterBasefallFragment = new BaseWaterfallFragment();
//        waterBasefallFragment.mAdapter = adapter;
//        return waterBasefallFragment;
//    }


    @Override
    protected View createContentView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mAdapter = obtainAdapter();
        return inflater.inflate(R.layout.sclist_fra, container, false);
    }

    @Override
    protected void initView() {
        mRecyclerView = (WaterFallRecyclerView) mContentView.findViewById(R.id.id_stick_inner_scroll);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        reqData();
    }

    public void reqData() {
        ScLog.i("-----initData-------");

    }

    protected abstract RecyclerView.Adapter obtainAdapter();
}

package com.csuft.zzc.schoolfellow.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.fragment.BaseFragment;
import com.csuft.zzc.schoolfellow.base.net.BaseApi;
import com.csuft.zzc.schoolfellow.base.net.CallBack;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.view.WaterFallRecyclerView;
import com.csuft.zzc.schoolfellow.circle.adapter.NewsWaterFallAdapter;
import com.csuft.zzc.schoolfellow.circle.data.NewsData;

/**
 * Created by wangzhi on 16/3/11.
 */
public abstract class WaterBasefallFragment extends BaseFragment {

    private static final String TAG = "WaterBasefallFragment";
    RecyclerView.Adapter mAdapter;

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

//    public static WaterBasefallFragment create(RecyclerView.Adapter adapter) {
//        WaterBasefallFragment waterBasefallFragment = new WaterBasefallFragment();
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
        WaterFallRecyclerView recyclerView = (WaterFallRecyclerView) mContentView.findViewById(R.id.id_stick_inner_scroll);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        reqData();
    }

    public void reqData() {
        ScLog.i("-----reqData-------");
        BaseApi.getInstance().get(BaseApi.HOST_URL + "/news", null, NewsData.class, new CallBack<NewsData>() {
            @Override
            public void onSuccess(NewsData data) {
                ScLog.i("req news onSuccess " + mAdapter);
                NewsWaterFallAdapter newsWaterFallAdapter = (NewsWaterFallAdapter) mAdapter;
                newsWaterFallAdapter.setItemDataList(data.result.newsList);
                newsWaterFallAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int code, String error) {
                ScLog.i("req news onFailure");
            }
        });
    }

    protected abstract RecyclerView.Adapter obtainAdapter();
}

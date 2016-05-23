package com.csuft.zzc.schoolfellow.circle.fragment;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;

import com.csuft.zzc.schoolfellow.base.fragment.BaseWaterfallFragment;
import com.csuft.zzc.schoolfellow.base.net.BaseApi;
import com.csuft.zzc.schoolfellow.base.net.CallBack;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.view.DividerItemDecoration;
import com.csuft.zzc.schoolfellow.circle.adapter.DynamicWaterFallAdapter;
import com.csuft.zzc.schoolfellow.circle.data.DynamicData;

/**
 * Created by wangzhi on 16/5/18.
 */
public class DynamicWaterFallFragment extends BaseWaterfallFragment {
    @Override
    protected RecyclerView.Adapter obtainAdapter() {
        return new DynamicWaterFallAdapter(getContext());
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView.setBackgroundColor(Color.parseColor("#e8e8e8"));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void reqData() {
        super.reqData();
        BaseApi.getInstance().get(BaseApi.HOST_URL + "/dynamic", null, DynamicData.class, new CallBack<DynamicData>() {
            @Override
            public void onSuccess(DynamicData data) {
                ScLog.i("req news onSuccess " + mAdapter);
                DynamicWaterFallAdapter newsWaterFallAdapter = (DynamicWaterFallAdapter) mAdapter;
                newsWaterFallAdapter.setItemDataList(data.result.dynamicList);
                newsWaterFallAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int code, String error) {
                ScLog.i("req news onFailure");
            }
        });
    }
}

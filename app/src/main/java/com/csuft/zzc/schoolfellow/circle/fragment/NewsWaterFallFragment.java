package com.csuft.zzc.schoolfellow.circle.fragment;

import android.support.v7.widget.RecyclerView;

import com.csuft.zzc.schoolfellow.base.fragment.BaseWaterfallFragment;
import com.csuft.zzc.schoolfellow.base.net.BaseApi;
import com.csuft.zzc.schoolfellow.base.net.CallBack;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.circle.adapter.NewsWaterFallAdapter;
import com.csuft.zzc.schoolfellow.circle.data.NewsData;

/**
 * Created by wangzhi on 16/5/2.
 */
public class NewsWaterFallFragment extends BaseWaterfallFragment {
    @Override
    protected RecyclerView.Adapter obtainAdapter() {
        return new NewsWaterFallAdapter(getContext());
    }

    @Override
    public void reqData() {
        super.reqData();
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
}

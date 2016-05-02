package com.csuft.zzc.schoolfellow.circle.fragment;

import android.support.v7.widget.RecyclerView;

import com.csuft.zzc.schoolfellow.base.fragment.WaterBasefallFragment;
import com.csuft.zzc.schoolfellow.circle.adapter.NewsWaterFallAdapter;

/**
 * Created by wangzhi on 16/5/2.
 */
public class NewsWaterFallFragment extends WaterBasefallFragment {
    @Override
    protected RecyclerView.Adapter obtainAdapter() {
        return new NewsWaterFallAdapter(getContext());
    }
}

package com.csuft.zzc.schoolfellow.host.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.utils.ScreenUtil;
import com.csuft.zzc.schoolfellow.base.view.WaterFallRecyclerView;
import com.csuft.zzc.schoolfellow.base.view.WebImageView;
import com.csuft.zzc.schoolfellow.host.data.SchoolCirclePagerData;

import java.util.List;

/**
 * Created by wangzhi on 16/3/11.
 */
public class WaterfallFragment extends Fragment {

    private List<SchoolCirclePagerData.Info> mItemDataList;
    private LayoutInflater inflater;

    public static WaterfallFragment create(List<SchoolCirclePagerData.Info> itemDataList) {
        WaterfallFragment waterfallFragment = new WaterfallFragment();
        waterfallFragment.mItemDataList = itemDataList;
        return waterfallFragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.sclist_fra, container, false);
        WaterFallRecyclerView recyclerView = (WaterFallRecyclerView) root.findViewById(R.id.id_stick_inner_scroll);
        recyclerView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                ViewHolder viewHolder = new ViewHolder(inflater.inflate(R.layout.sclist_item, parent, false));
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                SchoolCirclePagerData.Info info = mItemDataList.get(position);
                holder.nameTxt.setText(info.name);
                holder.contentTxt.setText(info.content);
                holder.timeTxt.setText(info.time);
                holder.headImg.setImageUrl(info.headImg, ScreenUtil.instance().dip2px(40), ScreenUtil.instance().dip2px(40));
            }


            @Override
            public int getItemCount() {
                return mItemDataList == null ? 0 : mItemDataList.size();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        return root;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            headImg = (WebImageView) itemView.findViewById(R.id.head_img);
            nameTxt = (TextView) itemView.findViewById(R.id.name_txt);
            timeTxt = (TextView) itemView.findViewById(R.id.time_txt);
            contentTxt = (TextView) itemView.findViewById(R.id.content_txt);
        }

        public WebImageView headImg;
        public TextView nameTxt;
        public TextView timeTxt;
        public TextView contentTxt;
    }
}

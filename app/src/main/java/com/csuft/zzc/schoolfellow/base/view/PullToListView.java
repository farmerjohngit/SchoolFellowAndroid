package com.csuft.zzc.schoolfellow.base.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.data.DataFactory;
import com.csuft.zzc.schoolfellow.host.data.ImItemData;

import java.util.List;

/**
 * Created by wangzhi on 16/3/12.
 */
public class PullToListView extends AbsPullToRefresh<RefreshIndicator, ListView> {

    protected List<ImItemData> imItemDataList;

    public PullToListView(Context context) {
        super(context);
    }

    public PullToListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected RefreshIndicator createHeaderView() {
//        LinearLayout linearLayout = new LinearLayout(getContext());
//        linearLayout.setBackgroundResource(R.drawable.pulltorefresh_header);
//        linearLayout.setLayoutParams(new LinearLayoutCompat.LayoutParams(mScreenUtil.dip2px(20), mScreenUtil.dip2px(20)));
//        return linearLayout;
        RefreshIndicator refreshIndicator = new RefreshIndicator(getContext());
        refreshIndicator.setLayoutParams(new LinearLayoutCompat.LayoutParams(mScreenUtil.dip2px(26), mScreenUtil.dip2px(26)));

        return refreshIndicator;
    }

    @Override
    protected ListView createRefreshView() {

        ListView listView = new ListView(getContext());
        listView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        imItemDataList = DataFactory.createImItemData(30);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return imItemDataList == null ? 0 : imItemDataList.size();
            }

            @Override
            public Object getItem(int position) {
                return imItemDataList == null ? null : imItemDataList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImItemViewHolder holder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.im_item_fra, parent, false);
                    holder = new ImItemViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ImItemViewHolder) convertView.getTag();
                }
                ImItemData imItemData = imItemDataList.get(position);
                holder.headImg.setImageUrl(imItemData.imgUrl);
                holder.timeTxt.setText(imItemData.time);
                holder.nameTxt.setText(imItemData.name);
                holder.contentTxt.setText(imItemData.content);


                return convertView;
            }
        });

        return listView;
    }
//        RecyclerView recyclerView = new RecyclerView(getContext());
//        recyclerView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setBackgroundColor(Color.RED);
//        imItemDataList= DataFactory.createImItemData(13);
//
//        recyclerView.setAdapter(new RecyclerView.Adapter<ImItemViewHolder>() {
//
//
//            @Override
//            public ImItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                return new ImItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.im_item_fra, parent, false));
//            }
//
//            @Override
//            public void onBindViewHolder(ImItemViewHolder holder, int position) {
//                ImItemData imItemData = imItemDataList.get(position);
//                holder.headImg.setImageUrl(imItemData.imgUrl);
//                holder.timeTxt.setText(imItemData.time);
//                holder.nameTxt.setText(imItemData.name);
//                holder.contentTxt.setText(imItemData.content);
//            }
//
//            @Override
//            public int getItemCount() {
//                return imItemDataList==null?0:imItemDataList.size();
//            }
//        });
//        return recyclerView;
//    }

    class ImItemViewHolder extends RecyclerView.ViewHolder {
        public ImItemViewHolder(View itemView) {
            super(itemView);
            nameTxt = (TextView) itemView.findViewById(R.id.name_txt);
            contentTxt = (TextView) itemView.findViewById(R.id.content_txt);
            timeTxt = (TextView) itemView.findViewById(R.id.time_txt);
            headImg = (WebImageView) itemView.findViewById(R.id.head_img);
        }

        public WebImageView headImg;
        public TextView nameTxt;
        public TextView contentTxt;
        public TextView timeTxt;
    }
}

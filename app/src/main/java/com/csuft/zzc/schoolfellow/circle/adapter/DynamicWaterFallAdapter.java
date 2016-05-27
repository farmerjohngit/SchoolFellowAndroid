package com.csuft.zzc.schoolfellow.circle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.utils.ArrayUtil;
import com.csuft.zzc.schoolfellow.base.utils.DateUtil;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.utils.ScreenUtil;
import com.csuft.zzc.schoolfellow.base.view.WebImageView;
import com.csuft.zzc.schoolfellow.circle.data.DynamicData;

import java.util.List;

/**
 * Created by wangzhi on 16/5/18.
 */
public class DynamicWaterFallAdapter extends RecyclerView.Adapter<DynamicViewHolder> {
    private static final String TAG = "DynamicWaterFallAdapter";
    Context mContext;
    private List<DynamicData.DynamicItem> mItemDataList;

    public DynamicWaterFallAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public DynamicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.dynamic_item, parent, false);
        DynamicViewHolder viewHolder = new DynamicViewHolder(root);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DynamicViewHolder holder, int position) {
        final DynamicData.DynamicItem info = mItemDataList.get(position);
        holder.contentTxt.setText(info.text);
        holder.nameTxt.setText(info.name);
        holder.timeTxt.setText(DateUtil.dataFormatByDefault(info.time));
        holder.headImg.setImageUrl(info.avatar, true);
        String url = ArrayUtil.getFirstItem(info.imgs);
        ScLog.i(TAG, "url:   " + ArrayUtil.getFirstItem(info.imgs));
        if (TextUtils.isEmpty(url)) {
            holder.publishImg.setVisibility(View.GONE);
        } else {
            holder.publishImg.setVisibility(View.VISIBLE);
            holder.publishImg.setImageUrl(ArrayUtil.getFirstItem(info.imgs));
        }
//
//        TextPaint tp = holder.titleTxt.getPaint();
//        tp.setFakeBoldText(true);
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, NewsDetailAct.class);
//                intent.putExtra("_id", info._id);
//                mContext.startActivity(intent);
//            }
//        });

    }


    @Override
    public int getItemCount() {
        return mItemDataList == null ? 0 : mItemDataList.size();
    }

    public void setItemDataList(List<DynamicData.DynamicItem> itemDataList) {
        mItemDataList = itemDataList;
    }
}

class DynamicViewHolder extends RecyclerView.ViewHolder {

    public DynamicViewHolder(View itemView) {
        super(itemView);
        headImg = (WebImageView) itemView.findViewById(R.id.head_img);
        publishImg = (WebImageView) itemView.findViewById(R.id.publishImg);
        nameTxt = (TextView) itemView.findViewById(R.id.name_txt);
        timeTxt = (TextView) itemView.findViewById(R.id.time_txt);
        contentTxt = (TextView) itemView.findViewById(R.id.text);
    }

    public WebImageView headImg;
    public WebImageView publishImg;
    public TextView nameTxt;
    public TextView timeTxt;
    public TextView contentTxt;
}
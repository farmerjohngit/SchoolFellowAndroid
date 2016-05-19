package com.csuft.zzc.schoolfellow.circle.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.utils.ScreenUtil;
import com.csuft.zzc.schoolfellow.base.view.WebImageView;
import com.csuft.zzc.schoolfellow.circle.act.NewsDetailAct;
import com.csuft.zzc.schoolfellow.circle.data.NewsData;

import java.util.List;

/**
 * Created by wangzhi on 16/4/28.
 */
public class NewsWaterFallAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    Context mContext;
    private List<NewsData.NewsItem> mItemDataList;

    public NewsWaterFallAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.news_item, parent, false);
        NewsViewHolder viewHolder = new NewsViewHolder(root);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        final NewsData.NewsItem info = mItemDataList.get(position);
        holder.titleTxt.setText(info.title);
        holder.contentTxt.setText(info.getFirstForSpecType(NewsData.Paragraph.TEXT_TYPE));
        holder.timeTxt.setText(info.time);
        ScLog.i("temp","<<"+info.getFirstForSpecType(NewsData.Paragraph.TEXT_TYPE));
        if(info.title.contains("大讨论活动工作布置会上的讲话")){
            ScLog.i("temp","onBindViewHolder-> "+info.getFirstForSpecType(NewsData.Paragraph.IMG_TYPE));
        }
        holder.headImg.setImageUrl(info.getFirstForSpecType(NewsData.Paragraph.IMG_TYPE), ScreenUtil.instance().dip2px(160), ScreenUtil.instance().dip2px(90));

        TextPaint tp = holder.titleTxt.getPaint();
        tp.setFakeBoldText(true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewsDetailAct.class);
                intent.putExtra("_id", info._id);
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mItemDataList == null ? 0 : mItemDataList.size();
    }

    public void setItemDataList(List<NewsData.NewsItem> itemDataList) {
        mItemDataList = itemDataList;
    }


}

class NewsViewHolder extends RecyclerView.ViewHolder {

    public NewsViewHolder(View itemView) {
        super(itemView);
        headImg = (WebImageView) itemView.findViewById(R.id.news_pic);
        titleTxt = (TextView) itemView.findViewById(R.id.news_title);
        timeTxt = (TextView) itemView.findViewById(R.id.news_time);
        contentTxt = (TextView) itemView.findViewById(R.id.news_pre);
    }

    public WebImageView headImg;
    public TextView titleTxt;
    public TextView timeTxt;
    public TextView contentTxt;
}

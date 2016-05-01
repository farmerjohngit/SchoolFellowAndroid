package com.csuft.zzc.schoolfellow.circle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.utils.ScreenUtil;
import com.csuft.zzc.schoolfellow.base.view.WebImageView;
import com.csuft.zzc.schoolfellow.circle.data.NewsData;

import java.util.List;

/**
 * Created by wangzhi on 16/4/28.
 */
public class NewsWaterFallAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context mContext;
    private List<NewsData.NewsItem> mItemDataList;

    public NewsWaterFallAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.news_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsData.NewsItem info = mItemDataList.get(position);
        holder.titleTxt.setText(info.title);
        holder.contentTxt.setText(info.getFirstForSpecType(NewsData.Paragraph.TEXT_TYPE));
        holder.timeTxt.setText(info.time);
        holder.headImg.setImageUrl(info.getFirstForSpecType(NewsData.Paragraph.IMG_TYPE), ScreenUtil.instance().dip2px(40), ScreenUtil.instance().dip2px(40));

        TextPaint tp = holder.titleTxt.getPaint();
        tp.setFakeBoldText(true);

    }


    @Override
    public int getItemCount() {
        return mItemDataList == null ? 0 : mItemDataList.size();
    }

    public void setItemDataList(List<NewsData.NewsItem> itemDataList) {
        mItemDataList = itemDataList;
    }


}

class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(View itemView) {
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

package com.csuft.zzc.schoolfellow.circle.act;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.act.BaseFragmentActivity;
import com.csuft.zzc.schoolfellow.base.net.BaseApi;
import com.csuft.zzc.schoolfellow.base.net.CallBack;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.view.WebImageView;
import com.csuft.zzc.schoolfellow.circle.data.NewsData;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wangzhi on 16/5/2.
 */
public class NewsDetailAct extends BaseFragmentActivity {
    private static final String TAG = "NewsDetailAct";
    NewsData.NewsItem mNewsItem;
    TextView mTitleTxt;
    TextView mSubTitleTxt;
    TextView mTimeTxt;
    RecyclerView mRecyclerView;
    ParaAdapter mParaAdapter;
    LayoutInflater mLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content_act);
        String _id = getIntent().getStringExtra("_id");
        ScLog.i(TAG, "get _id " + _id);
        reqData(_id);
        mLayoutInflater = LayoutInflater.from(this);
        initView();
    }

    public void initView() {
        mTitleTxt = (TextView) findViewById(R.id.news_title);
        mSubTitleTxt = (TextView) findViewById(R.id.news_subtitle);
        mTimeTxt = (TextView) findViewById(R.id.news_time);
        mRecyclerView = (RecyclerView) findViewById(R.id.news_para);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mParaAdapter = new ParaAdapter();
        mRecyclerView.setAdapter(mParaAdapter);
    }


    public void reqData(String _id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("_id", _id);
        BaseApi.getInstance().get(BaseApi.HOST_URL + "/news", hashMap, NewsData.class, new CallBack<NewsData>() {

            @Override
            public void onSuccess(NewsData data) {
                ScLog.i(TAG, "onSuccess ");
                mNewsItem = data.result.newsList.get(0);
                mTitleTxt.setText(mNewsItem.title);
                mTitleTxt.getPaint().setFakeBoldText(true);
                mSubTitleTxt.setText(mNewsItem.subTitle);
                mTimeTxt.setText(mNewsItem.time);
                mParaAdapter.setParaList(mNewsItem.paragraphs);
                mParaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int code, String error) {
                ScLog.i(TAG, "onFailure : " + error);

            }
        });
    }


    class ParaAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<NewsData.Paragraph> mParaList;

        public void setParaList(List<NewsData.Paragraph> paraList) {
            mParaList = paraList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder viewHolder = new ViewHolder(mLayoutInflater.inflate(R.layout.para1_item, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            NewsData.Paragraph para = mParaList.get(position);

            if (para.contentType == NewsData.Paragraph.IMG_TYPE) {
                holder.img.setImageUrl(para.content.get(0));
                holder.img.setVisibility(View.VISIBLE);
                holder.txt.setVisibility(View.GONE);
                ScLog.i("onBindViewHolder img " + para.content.get(0));
            } else {
                holder.txt.setVisibility(View.VISIBLE);
                holder.img.setVisibility(View.GONE);
                ScLog.i("onBindViewHolder txt " + para.content.get(0));
                holder.txt.setText(para.content.get(0));
            }

        }

        @Override
        public int getItemCount() {
            return mParaList == null ? 0 : mParaList.size();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            img = (WebImageView) itemView.findViewById(R.id.para_img);
            txt = (TextView) itemView.findViewById(R.id.para_txt);
//            timeTxt = (TextView) itemView.findViewById(R.userName.news_time);
//            contentTxt = (TextView) itemView.findViewById(R.userName.news_pre);
        }

        public WebImageView img;
        public TextView txt;
    }

}

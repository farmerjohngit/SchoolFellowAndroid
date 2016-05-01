package com.csuft.zzc.schoolfellow.im.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.fragment.BaseFragment;
import com.csuft.zzc.schoolfellow.base.net.BaseApi;
import com.csuft.zzc.schoolfellow.base.net.CallBack;
import com.csuft.zzc.schoolfellow.base.utils.DateUtil;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.view.AbsPullToRefresh;
import com.csuft.zzc.schoolfellow.base.view.PullToListView;
import com.csuft.zzc.schoolfellow.base.view.WebImageView;
import com.csuft.zzc.schoolfellow.im.data.ImMsgData;
import com.csuft.zzc.schoolfellow.im.act.P2PChatAct;

import java.util.List;

/**
 * Created by wangzhi on 16/3/9.
 */
public class ChatFragment extends BaseFragment {
    PullToListView mPullToListView;
    private static final String TAG = "ChatFragment";
    ListView mImgListView;
    private ImListAdapter mAdapter;


    @Override
    protected View createContentView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.char_fra, null);
    }

    @Override
    protected void initView() {

        mPullToListView = (PullToListView) mContentView.findViewById(R.id.pullToRefresh);

        initDataListView();
        mPullToListView.setOnRefreshListener(new AbsPullToRefresh.OnRefreshListener() {
            @Override
            public void onStartRefresh() {
                ScLog.i("onStart Refresh");
                requestImMsgData(mPullToListView);

            }

            @Override
            public void onPullDown(float per) {
                ScLog.i("onPullDown " + per);

            }

            @Override
            public void onRefreshFinish() {
                ScLog.i("onRefreshFinish ");

            }
        });
        requestImMsgDataWithCache(mPullToListView);
    }

    public void initDataListView() {
        mImgListView = mPullToListView.getRefreshView();
        mAdapter = new ImListAdapter(this.getContext());
        mImgListView.setAdapter(mAdapter);

    }


    public void requestImMsgDataWithCache(final PullToListView pullToListView) {
        requestImMsgData(pullToListView);

    }

    public void requestImMsgData(final PullToListView pullToListView) {
        BaseApi.getInstance().get(BaseApi.HOST_URL + "/im_msg", null, ImMsgData.class, new CallBack<ImMsgData>() {
            @Override
            public void onSuccess(ImMsgData data) {
                mAdapter.setImItemDataList(data.result.msgList);
                mAdapter.notifyDataSetChanged();
                pullToListView.refreshFinish();
                ScLog.i(TAG, "onSuccess " + data.result.msgList.size());
            }

            @Override
            public void onFailure(int code, String error) {
                ScLog.i(TAG, "onFailure");
                pullToListView.refreshFinish();
            }
        });

    }

    static class ImListAdapter extends BaseAdapter {
        protected List<ImMsgData.ImMsgItem> imItemDataList;
        Context context;

        ImListAdapter(Context context) {
            this.context = context;
        }

        public void setImItemDataList(List<ImMsgData.ImMsgItem> imItemDataList) {
            this.imItemDataList = imItemDataList;
        }

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
                convertView = LayoutInflater.from(context).inflate(R.layout.im_item_fra, parent, false);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, P2PChatAct.class));
                    }
                });
                holder = new ImItemViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ImItemViewHolder) convertView.getTag();
            }
            ImMsgData.ImMsgItem imMsgItem = imItemDataList.get(position);
            holder.headImg.setImageUrl(imMsgItem.avatar, true);
            holder.timeTxt.setText(DateUtil.showTime(imMsgItem.time));
            holder.nameTxt.setText(imMsgItem.userName);
            holder.contentTxt.setText(imMsgItem.msg);


            return convertView;
        }
    }


    static class ImItemViewHolder extends RecyclerView.ViewHolder {
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

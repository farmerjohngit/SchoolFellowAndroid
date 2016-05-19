package com.csuft.zzc.schoolfellow.im.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.fragment.RecyclerViewFragment;
import com.csuft.zzc.schoolfellow.base.net.BaseApi;
import com.csuft.zzc.schoolfellow.base.net.CallBack;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.view.DividerItemDecoration;
import com.csuft.zzc.schoolfellow.base.view.WebImageView;
import com.csuft.zzc.schoolfellow.host.data.UserData;
import com.csuft.zzc.schoolfellow.im.act.P2PChatAct;
import com.csuft.zzc.schoolfellow.im.data.ContactsData;
import com.csuft.zzc.schoolfellow.user.UserInfoAct;
import com.csuft.zzc.schoolfellow.user.UserManager;

import java.util.HashMap;

/**
 * Created by wangzhi on 16/5/4.
 */
public class ContactPersonFragment extends RecyclerViewFragment<UserData> {
    PersonAdapter mAdapter;

    private static final String TAG = "ContactPersonFragment";
    int type = 0;

    @Override
    protected void initView() {
        mAdapter = new PersonAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        type = getArguments().getInt("type", 0);
        ScLog.i(TAG, "type: " + type);
        reqData();
    }

    public void reqData() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("from", UserManager.getInstance().getUser().userName);
        hashMap.put("rank", type + "");
        BaseApi.getInstance().get(BaseApi.HOST_URL + "/query_friend", hashMap, ContactsData.class, new CallBack<ContactsData>() {
            @Override
            public void onSuccess(ContactsData data) {
                dataList = data.result.users;
                mAdapter.notifyDataSetChanged();
                ScLog.i(TAG, "onSuccess " + (dataList == null ? 0 : dataList.size()));
            }

            @Override
            public void onFailure(int code, String error) {
                ScLog.i(TAG, "onFailure " + error);
            }
        });
    }


    class PersonAdapter extends RecyclerView.Adapter<ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View convertView = LayoutInflater.from(getContext()).inflate(R.layout.im_item_fra, parent, false);
            ViewHolder viewHolder = new ViewHolder(convertView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final UserData userData = dataList.get(position);
            holder.headImg.setImageUrl(userData.avatar, true);
            holder.nameTxt.setText(userData.name);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), P2PChatAct.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", userData);
                    intent.putExtra("bundle", bundle);
                    startActivity(intent);
                }
            };
//            holder.headImg.setOnClickListener(clickListener);
            holder.itemView.setOnClickListener(clickListener);

        }

        @Override
        public int getItemCount() {
            return dataList == null ? 0 : dataList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            nameTxt = (TextView) itemView.findViewById(R.id.name_txt);
            headImg = (WebImageView) itemView.findViewById(R.id.head_img);
            itemView.findViewById(R.id.content_txt).setVisibility(View.GONE);
            itemView.findViewById(R.id.time_txt).setVisibility(View.GONE);
        }

        public WebImageView headImg;
        public TextView nameTxt;

    }

}

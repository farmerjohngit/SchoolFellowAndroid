package com.csuft.zzc.schoolfellow.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.fragment.RecyclerViewFragment;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.view.DividerItemDecoration;
import com.csuft.zzc.schoolfellow.base.view.WebImageView;
import com.csuft.zzc.schoolfellow.host.data.UserData;
import com.csuft.zzc.schoolfellow.user.UserInfoAct;
import com.csuft.zzc.schoolfellow.user.UserManager;

import java.util.List;

/**
 * Created by wangzhi on 16/5/4.
 */
public class SearchPersonFragment extends RecyclerViewFragment<UserData> {
    PersonAdapter mAdapter;

    private static final String TAG = "SearchPersonFragment";

    @Override
    protected void initView() {
        mAdapter = new PersonAdapter();
        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.sets
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void setDataList(List<UserData> dataList) {
        super.setDataList(dataList);
        mAdapter.notifyDataSetChanged();
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
            if(userData.userName.equals(UserManager.getInstance().getUser().userName)){
                holder.imageView.setVisibility(View.GONE);
            }
            holder.headImg.setImageUrl(userData.avatar, true);
            holder.nameTxt.setText(userData.userName);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AddFriendAct.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", userData);
                    intent.putExtra("bundle", bundle);
                    startActivity(intent);
                }
            });
            View.OnClickListener onClickListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), UserInfoAct.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", userData);
                    intent.putExtra("bundle", bundle);
                    startActivity(intent);
                }
            };
            holder.headImg.setOnClickListener(onClickListener);
            holder.nameTxt.setOnClickListener(onClickListener);


            ScLog.i(TAG, "userData.name: " + userData.name);

        }

        @Override
        public int getItemCount() {
            ScLog.i(TAG, "dataList size  " + (dataList == null ? 0 : dataList.size()));
            return dataList == null ? 0 : dataList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_img);
            nameTxt = (TextView) itemView.findViewById(R.id.name_txt);
            headImg = (WebImageView) itemView.findViewById(R.id.head_img);
            imageView.setVisibility(View.VISIBLE);
            itemView.findViewById(R.id.content_txt).setVisibility(View.GONE);
            itemView.findViewById(R.id.time_txt).setVisibility(View.GONE);
        }

        public ImageView imageView;
        public WebImageView headImg;
        public TextView nameTxt;

    }

}

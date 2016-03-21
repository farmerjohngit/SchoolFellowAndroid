package com.csuft.zzc.schoolfellow.host.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.fragment.BaseFragment;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.view.AbsPullToRefresh;
import com.csuft.zzc.schoolfellow.base.view.PullToListView;

/**
 * Created by wangzhi on 16/3/9.
 */
public class ChatFragment extends BaseFragment {

    @Override
    protected View createContentView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.char_fra, null);
    }

    @Override
    protected void initView() {
        final PullToListView pullToListView = (PullToListView) mContentView.findViewById(R.id.pullToRefresh);
        pullToListView.setOnRefreshListener(new AbsPullToRefresh.OnRefreshListener() {
            @Override
            public void onStartRefresh() {
                ScLog.i("onStart Refresh");
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(5000);
                            ScLog.i("doIng "+Thread.currentThread().equals(Looper.getMainLooper().getThread()));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        ScLog.i("refreshFinish");
                        pullToListView.refreshFinish();

                    }
                }.execute(null, null, null);
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
    }
}

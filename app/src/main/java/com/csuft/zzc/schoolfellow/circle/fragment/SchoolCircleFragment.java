package com.csuft.zzc.schoolfellow.circle.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.data.DataFactory;
import com.csuft.zzc.schoolfellow.base.fragment.BaseFragment;
import com.csuft.zzc.schoolfellow.base.fragment.BaseWaterfallFragment;
import com.csuft.zzc.schoolfellow.base.net.BaseApi;
import com.csuft.zzc.schoolfellow.base.net.CallBack;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.view.AbsAutoScrollLayout;
import com.csuft.zzc.schoolfellow.base.view.AbsPullToRefresh;
import com.csuft.zzc.schoolfellow.base.view.PullToStickNavView;
import com.csuft.zzc.schoolfellow.base.view.TopBar;
import com.csuft.zzc.schoolfellow.circle.act.NewsDetailAct;
import com.csuft.zzc.schoolfellow.circle.data.NewsBannerData;
import com.csuft.zzc.schoolfellow.circle.data.SchoolCirclePagerData;
import com.csuft.zzc.schoolfellow.host.fragment.DynamicWaterFallFragment;
import com.csuft.zzc.schoolfellow.publish.PublishAct;

import java.util.List;

/**
 * Created by wangzhi on 16/3/9.
 */
public class SchoolCircleFragment extends BaseFragment {

    List<SchoolCirclePagerData> mDataList;
    AbsAutoScrollLayout mScrollPager;
    PullToStickNavView mPullToRefresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("wangzhi", "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("wangzhi", "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected View createContentView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.school_circle_fra, container, false);
    }

    @Override
    protected void initView() {
        initData();
        TopBar topBar = (TopBar) findViewById(R.id.top_bar);
        topBar.setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PublishAct.class));
            }
        });
        mPullToRefresh = (PullToStickNavView) mContentView.findViewById(R.id.pullToRefresh);
        mPullToRefresh.setOnRefreshListener(new AbsPullToRefresh.OnRefreshListener() {
            @Override
            public void onStartRefresh() {
                ScLog.i("onStart Refresh");
                reqDataWithCache();
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
        mPullToRefresh.setOnLoadMoreListener(new AbsPullToRefresh.OnLoadMoreListener() {
            @Override
            public void onStartLoadMore() {
                ScLog.i(TAG, "onStartLoadMore");
            }

            @Override
            public void onLoadFinish() {

            }
        });
        mScrollPager = (AbsAutoScrollLayout) mContentView.findViewById(R.id.scrollPager);
        mScrollPager.addOnItemClickListener(new AbsAutoScrollLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object obj) {
                Intent intent = new Intent(getContext(), NewsDetailAct.class);
                if (obj instanceof NewsBannerData.BannerItem) {
//                    intent.putExtra("_id", ((NewsBannerData.BannerItem) obj).url);
//                    getContext().startActivity(intent);
                }

            }

        });
//        mScrollPager.setBannerData(DataFactory.createBannerDataList(2));
        mScrollPager.startAutoScroll();

        ViewPager pager = (ViewPager) mContentView.findViewById(R.id.id_stick_pager);

        TabLayout tabLayout = (TabLayout) mContentView.findViewById(R.id.id_stick_nav);

        SchoolCirclePagerAdapter adapter = new SchoolCirclePagerAdapter(getFragmentManager());
        pager.setAdapter(adapter);

        tabLayout.setTabsFromPagerAdapter(adapter);
        tabLayout.setTabTextColors(Color.BLACK, Color.RED);
        tabLayout.setupWithViewPager(pager);
    }


    private void initData() {
        reqDataWithCache();
        mDataList = DataFactory.createSchoolCirclePagerData(2);
    }


    public void reqData() {
        reqBannerData();
        reqWaterData();


    }

    public void reqDataWithCache() {
        reqData();
    }

    private static final String TAG = "SchoolCircleFragment";

    public void reqWaterData() {
        for (Fragment fragment : getFragmentManager().getFragments()) {
            if (fragment instanceof BaseWaterfallFragment) {
                ScLog.i(TAG, "reqWaterData");
                BaseWaterfallFragment baseWaterfallFragment = (BaseWaterfallFragment) fragment;
                baseWaterfallFragment.reqData();
            }

        }
    }

    public void reqBannerData() {
        BaseApi.getInstance().get(BaseApi.HOST_URL + "/news_banner", null, NewsBannerData.class, new CallBack<NewsBannerData>() {

            @Override
            public void onSuccess(NewsBannerData data) {
//                ScLog.i("onSucces " + data.result.bannerList.size());
                mScrollPager.setBannerData(data.result.bannerList);
                mPullToRefresh.refreshFinish();
            }

            @Override
            public void onFailure(int code, String error) {
                ScLog.i("onFailure");
                mPullToRefresh.refreshFinish();
            }
        });


    }

    class SchoolCirclePagerAdapter extends FragmentPagerAdapter {

        public SchoolCirclePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseWaterfallFragment baseWaterfallFragment = null;
            if (position == 0) {
                baseWaterfallFragment = new NewsWaterFallFragment();
            } else {
                baseWaterfallFragment = new DynamicWaterFallFragment();
            }
            return baseWaterfallFragment;
        }

        @Override
        public int getCount() {
            return mDataList == null ? 0 : mDataList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mDataList.get(position).title;
        }

    }


}

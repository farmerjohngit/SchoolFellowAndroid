package com.csuft.zzc.schoolfellow.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.act.BaseFragmentActivity;
import com.csuft.zzc.schoolfellow.base.data.DataFactory;
import com.csuft.zzc.schoolfellow.base.net.BaseApi;
import com.csuft.zzc.schoolfellow.base.net.CallBack;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.view.ScToast;
import com.csuft.zzc.schoolfellow.host.data.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangzhi on 16/5/6.
 */
public class SearchAct extends BaseFragmentActivity {
    private static final String TAG = "SearchAct";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<SearchTitleData> mTitleList;
    private EditText mEditText;
    private ContactPersonPagerAdapter mAdapter;
    List<SearchPersonFragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_act);
        mTitleList = DataFactory.createSearchPagerData();
        initView();
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitleList.size(); i++) {
            mFragments.add(new SearchPersonFragment());
        }
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.id_stick_nav);
        mEditText = (EditText) findViewById(R.id.search_edit);
        mViewPager = (ViewPager) findViewById(R.id.id_stick_pager);
        mAdapter = new ContactPersonPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);


        mTabLayout.setTabsFromPagerAdapter(mAdapter);
        mTabLayout.setTabTextColors(Color.BLACK, Color.RED);
        mTabLayout.setupWithViewPager(mViewPager);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || TextUtils.isEmpty(s.toString())) {
                    mFragments.get(mViewPager.getCurrentItem()).setDataList(null);
                    return;
                }
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("userName", s.toString());
                hashMap.put("queryMethod", "1");//by start match
                BaseApi.getInstance().get(BaseApi.HOST_URL + "/user_info", hashMap, QueryUserData.class, new CallBack<QueryUserData>() {
                    @Override
                    public void onSuccess(QueryUserData data) {
                        if (data.result.responseResult == QueryUserData.SUCCESS) {
                            List<UserData> users = data.result.users;
                            ScToast.toast("users: " + users.size());
                            mFragments.get(mViewPager.getCurrentItem()).setDataList(data.result.users);
//                            mAdapter.get
                        } else {
                            ScToast.toast(data.result.responseMsg);
                        }
                    }

                    @Override
                    public void onFailure(int code, String error) {

                    }
                });
                ScLog.i(TAG, "txt change " + s.toString());

            }
        });
    }

    public void back(View view) {
        finish();
    }


    class ContactPersonPagerAdapter extends FragmentPagerAdapter {

        public ContactPersonPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            SearchPersonFragment fragment = mFragments.get(position);
            Bundle bundle = new Bundle();
            bundle.putInt("type", mTitleList.get(position).type);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return mTitleList == null ? 0 : mTitleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position).title;
        }

    }

    public static class SearchTitleData {
        public String title;
        public int type;
        public static final int USER_TYPE = 0;
        public static final int GROUP_TYPE = 1;
    }
}

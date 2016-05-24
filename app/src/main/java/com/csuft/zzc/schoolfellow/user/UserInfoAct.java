package com.csuft.zzc.schoolfellow.user;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.act.BaseFragmentActivity;
import com.csuft.zzc.schoolfellow.base.data.DataFactory;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.view.WebImageView;
import com.csuft.zzc.schoolfellow.host.data.UserData;
import com.csuft.zzc.schoolfellow.host.fragment.DynamicWaterFallFragment;
import com.csuft.zzc.schoolfellow.host.util.DataFormatUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhi on 16/5/7.
 */
public class UserInfoAct extends BaseFragmentActivity {
    private static final String TAG = "UserInfoAct";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<DataFactory.TitleData> mTitleList;
    private ContactPersonPagerAdapter mAdapter;
    List<UserInfoFragment> mFragments;
    private UserData mUserData;
    TextView mUserNameTxt;
    TextView mIntroTxt;
    TextView mRankTxt;
    TextView mEduStartTxt;
    WebImageView mAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_act);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        mUserNameTxt = (TextView) findViewById(R.id.user_name);
        mIntroTxt = (TextView) findViewById(R.id.introduction_txt);
        mRankTxt = (TextView) findViewById(R.id.rank);
        mEduStartTxt = (TextView) findViewById(R.id.edu_start_date_spinner);
        mAvatar = (WebImageView) findViewById(R.id.avatar_img);
        if (bundle != null) {
            ScLog.i(TAG, "bundle not null");
            mUserData = (UserData) bundle.getSerializable("user");
            if (mUserData != null) {
                ScLog.i(TAG, "mUserData not null");
                ScLog.i(TAG, "<" + mUserData.name);
                mUserNameTxt.setText(mUserData.userName);
                mIntroTxt.setText(mUserData.introduction);
                mRankTxt.setText(DataFormatUtil.formatRank(mUserData.rank));
                mEduStartTxt.setText(mUserData.eduStartDate);
                mAvatar.setImageUrl(mUserData.avatar, true);
            }

        }
        mTitleList = DataFactory.createUserInfoPagerData();
        initView();
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitleList.size(); i++) {
            mFragments.add(new UserInfoFragment());
        }
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.id_stick_nav);
        mViewPager = (ViewPager) findViewById(R.id.id_stick_pager);
        mAdapter = new ContactPersonPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);


        mTabLayout.setTabsFromPagerAdapter(mAdapter);
        mTabLayout.setTabTextColors(Color.BLACK, Color.RED);
        mTabLayout.setupWithViewPager(mViewPager);

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
            Fragment fragment = null;
            if (position == 0) {
                fragment = mFragments.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", mUserData);
                fragment.setArguments(bundle);
            } else {
                fragment = new DynamicWaterFallFragment();
                Bundle bundle = new Bundle();
                bundle.putString("userName", mUserData.userName);
                fragment.setArguments(bundle);
            }


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


}

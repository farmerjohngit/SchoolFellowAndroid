package com.csuft.zzc.schoolfellow.im.act;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.act.BaseFragmentActivity;
import com.csuft.zzc.schoolfellow.base.data.DataFactory;
import com.csuft.zzc.schoolfellow.base.fragment.RecyclerViewFragment;
import com.csuft.zzc.schoolfellow.im.fragment.ContactPersonFragment;

import java.util.List;

/**
 * Created by wangzhi on 16/5/4.
 */
public class ContactPersonAct extends BaseFragmentActivity {


    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<ContactPersonTitleData> mTitleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_act);
        mTitleList = DataFactory.createContactPersonPagerData();
        initView();
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.id_stick_nav);

        mViewPager = (ViewPager) findViewById(R.id.id_stick_pager);
        ContactPersonPagerAdapter adapter = new ContactPersonPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        mTabLayout.setTabsFromPagerAdapter(adapter);
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
            RecyclerViewFragment fragment = new ContactPersonFragment();
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

    public static class ContactPersonTitleData {
        public String title;
        public int type;
        public static final int FRI_TYPE = 0;
        public static final int TEA_TYPE = 1;
    }


}

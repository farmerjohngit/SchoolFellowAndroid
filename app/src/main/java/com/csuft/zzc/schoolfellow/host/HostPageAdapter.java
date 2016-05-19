package com.csuft.zzc.schoolfellow.host;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.csuft.zzc.schoolfellow.host.fragment.FragmentFactory;

import java.util.List;

/**
 * Created by wangzhi on 16/3/9.
 */
public class HostPageAdapter extends FragmentPagerAdapter

{
    private List<PageData> mList;

    HostPageAdapter(FragmentManager fa, List list) {
        super(fa);
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.getFragment(mList.get(position));
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).title;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

}

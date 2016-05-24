package com.csuft.zzc.schoolfellow.base.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

import com.csuft.zzc.schoolfellow.base.view.AbsPullToRefresh;

/**
 * Created by wangzhi on 16/5/24.
 */
public class ScrollUtil {
    private static final String TAG = "ScrollUtil";
    public static boolean isScrollViewInTop(View scrolllView) {
        if (scrolllView instanceof ScrollView) {
            return (scrolllView).getScrollY() == 0;
        } else if (scrolllView instanceof RecyclerView) {
            RecyclerView rv = (RecyclerView) scrolllView;
            LinearLayoutManager lm = (LinearLayoutManager) rv.getLayoutManager();
            View topView = lm.findViewByPosition(lm.findFirstVisibleItemPosition());
            int topMargin = 0;
            if (topView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) topView.getLayoutParams();
                topMargin = marginLayoutParams.topMargin;
            }
            return topView == null ? true : (topView.getTop() == topMargin && lm.findFirstVisibleItemPosition() == 0);
        } else if (scrolllView instanceof ListView) {
            View child = ((ListView) scrolllView).getChildAt(0);
            return ((ListView) scrolllView).getFirstVisiblePosition() == 0 && child == null ? true : child.getTop() == 0;
        } else if (scrolllView instanceof AbsPullToRefresh.IRefreshAble) {
            return ((AbsPullToRefresh.IRefreshAble) scrolllView).onTop();
        }
        return false;
    }

    //todo
    public static boolean refreshViewInBottom(View mRefreshView) {
        if (mRefreshView instanceof ScrollView) {
            throw new RuntimeException("unsupport scrollview");
//            return ((ScrollView) mRefreshView).getChildCount() == 0 ? true : mRefreshView.getScrollY() <= 0;
        } else if (mRefreshView instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) mRefreshView;
            if (recyclerView.getAdapter().getItemCount() != 0) {
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                ScLog.i(TAG, "lastVisibleItemPosition " + lastVisibleItemPosition + "<>" + recyclerView.getAdapter().getItemCount());
                if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                    return true;
            }
            return false;
        } else if (mRefreshView instanceof ListView) {
            throw new RuntimeException("unsupport  list view");
        } else if (mRefreshView instanceof AbsPullToRefresh.IRefreshAble) {
            return ((AbsPullToRefresh.IRefreshAble) mRefreshView).onBottom();
        } else {
            throw new RuntimeException("unsupport unknown view");
        }
    }

}

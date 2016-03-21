package com.csuft.zzc.schoolfellow.host.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.fragment.BaseFragment;
import com.csuft.zzc.schoolfellow.base.view.EasyToast;

/**
 * Created by wangzhi on 16/3/10.
 */
public class MeFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected View createContentView(LayoutInflater inflater, @Nullable ViewGroup container) {

        return inflater.inflate(R.layout.me_fra, container, false);
    }

    @Override
    protected void initView() {
        Button button = (Button) mContentView.findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar snackbar =
//                        Snackbar.make(
//                                mContentView,
//                                "Snack Bar",
//                                Snackbar.LENGTH_SHORT);
//
//                snackbar.show();
                EasyToast.make(MeFragment.this.getActivity(), "test- ->>> <<<", EasyToast.LENGTH_LONG).show();
            }
        });
    }
}

package com.csuft.zzc.schoolfellow.user;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.fragment.BaseFragment;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.host.data.UserData;

/**
 * Created by wangzhi on 16/5/4.
 */
public class UserInfoFragment extends BaseFragment {

    private static final String TAG = "SearchPersonFragment";

    @Override
    protected View createContentView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.user_basic_info_fragment, null, false);
    }

    @Override
    protected void initView() {
        UserData user = (UserData) getArguments().getSerializable("user");
        if (user == null) {
            ScLog.i(TAG, "null!");
            return;
        }
        ScLog.i(TAG, "user.classs: " + user.classs);
        TextView mNameTxt = (TextView) findViewById(R.id.name_txt);
        TextView mClassTxt = (TextView) findViewById(R.id.class_txt);
        TextView mRankTxt = (TextView) findViewById(R.id.rank_txt);
        TextView mEduStartTxt = (TextView) findViewById(R.id.edu_start_txt);
        TextView mProfessionTxt = (TextView) findViewById(R.id.profession_txt);
        mNameTxt.setText(user.name);
        mClassTxt.setText(user.classs);
        switch (user.rank) {
            case 0:
                mRankTxt.setText("在校生");
                break;
            case 1:
                mRankTxt.setText("毕业生");
                break;
            case 2:
                mRankTxt.setText("教师");
                break;
        }

        mEduStartTxt.setText(user.eduStartDate);
        mProfessionTxt.setText(user.profession);
    }


}

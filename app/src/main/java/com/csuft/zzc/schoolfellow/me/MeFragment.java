package com.csuft.zzc.schoolfellow.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.fragment.BaseFragment;
import com.csuft.zzc.schoolfellow.base.net.BaseApi;
import com.csuft.zzc.schoolfellow.base.net.CallBack;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.view.EasyToast;
import com.csuft.zzc.schoolfellow.base.view.WebImageView;
import com.csuft.zzc.schoolfellow.im.act.ContactPersonAct;
import com.csuft.zzc.schoolfellow.login.LoginAct;
import com.csuft.zzc.schoolfellow.search.QueryUserData;
import com.csuft.zzc.schoolfellow.user.UserManager;

import java.util.HashMap;


/**
 * Created by wangzhi on 16/3/10.
 */
public class MeFragment extends BaseFragment {


    private WebImageView headImg;
    private TextView nameTxt;
    private TextView introductionTxt;
    private TextView mWbNumTxt;
    private TextView mCareNumTxt;
    private TextView mFollowTxt;
    private Button mExitBtn;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
//        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    protected View createContentView(LayoutInflater inflater, @Nullable ViewGroup container) {

        return inflater.inflate(R.layout.me_fra, container, false);
    }

    int i = 0;

    @Override
    protected void initView() {
        introductionTxt = (TextView) mContentView.findViewById(R.id.introduction_txt);
        nameTxt = (TextView) mContentView.findViewById(R.id.name_txt);
        mWbNumTxt = (TextView) mContentView.findViewById(R.id.weibo_num_text);
        mFollowTxt = (TextView) mContentView.findViewById(R.id.follow_num_text);
        mCareNumTxt = (TextView) mContentView.findViewById(R.id.care_num_text);
        mExitBtn = (Button) findViewById(R.id.exit_btn);
        mContentView.findViewById(R.id.layout1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "haha", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.go_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ContactPersonAct.class));
            }
        });

        headImg = (WebImageView) mContentView.findViewById(R.id.head_img);
        mExitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager.getInstance().logout();

                startActivity(new Intent(getActivity(), LoginAct.class));
            }
        });
        final Button button = (Button) mContentView.findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EasyToast.make(button, "h" + i++, "default", 2600).show();

            }
        });
        reqData();
    }

    private static final String TAG = "MeFragment";

    public void reqData() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("userName", UserManager.getInstance().getUser().userName);
        hashMap.put("wantData", "1");
        BaseApi.getInstance().get(BaseApi.HOST_URL + "/user_info", hashMap, QueryUserData.class, new CallBack<QueryUserData>() {
            @Override
            public void onSuccess(QueryUserData data) {
                ScLog.i(TAG, "onSuccess " + data.result.user.avatar);
                nameTxt.setText(data.result.user.name);
                if (!TextUtils.isEmpty(data.result.user.introduction)) {
                    introductionTxt.setText(data.result.user.introduction);
                }
                headImg.setImageUrl(data.result.user.avatar, true);
                mFollowTxt.setText(data.result.user.followerNum);
                mCareNumTxt.setText(data.result.user.careNum);
                mWbNumTxt.setText(data.result.user.weiboNum);
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
    }
}

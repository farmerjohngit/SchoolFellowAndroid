package com.csuft.zzc.schoolfellow.host.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
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
import com.csuft.zzc.schoolfellow.host.data.UserData;

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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Drawable textDrawable = null;
        Drawable actionDrawable = null;

        textDrawable = ResourcesCompat.getDrawable(getActivity().getResources(), R.mipmap.net, null);
        actionDrawable = ResourcesCompat.getDrawable(getActivity().getResources(), R.mipmap.ok, null);

        EasyToast.ToastStyle toastStyle = new EasyToast.ToastStyle();
        toastStyle.textSize = 16;
        toastStyle.bgColor = Color.WHITE;
//        toastStyle.textIcon = textDrawable;
        toastStyle.actionIcon = actionDrawable;
        toastStyle.maxTextLen = 10;
//        toastStyle.textGravity= Gravity.C;
        EasyToast.register("default", toastStyle);

        textDrawable = ResourcesCompat.getDrawable(getActivity().getResources(), R.mipmap.ok, null);
        actionDrawable = ResourcesCompat.getDrawable(getActivity().getResources(), R.mipmap.ok, null);
//        toastStyle = new EasyToast.ToastStyle(Color.WHITE, textDrawable
//                , actionDrawable, 16,0);
        //        EasyToast.register("default1", toastStyle);
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
        mContentView.findViewById(R.id.layout1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "haha", Toast.LENGTH_SHORT).show();
            }
        });

        headImg = (WebImageView) mContentView.findViewById(R.id.head_img);
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
        hashMap.put("userId", "20124609");
        BaseApi.getInstance().get(BaseApi.HOST_URL + "/user_info", hashMap, UserData.class, new CallBack<UserData>() {
            @Override
            public void onSuccess(UserData data) {
                ScLog.i(TAG, "onSuccess");
                nameTxt.setText(data.name);
                introductionTxt.setText(data.introduction);
                headImg.setImageUrl(data.avatar, true);
                mFollowTxt.setText(data.followerNums);
                mCareNumTxt.setText(data.careNum);
                mWbNumTxt.setText(data.weiboNum);
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
    }
}

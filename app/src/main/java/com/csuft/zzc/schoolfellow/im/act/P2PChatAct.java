package com.csuft.zzc.schoolfellow.im.act;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.act.BaseFragmentActivity;
import com.csuft.zzc.schoolfellow.base.im.ImUtil;
import com.csuft.zzc.schoolfellow.base.net.BaseApi;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.utils.ScreenUtil;
import com.csuft.zzc.schoolfellow.base.view.SpaceItemDecoration;
import com.csuft.zzc.schoolfellow.base.view.WebImageView;
import com.csuft.zzc.schoolfellow.host.data.UserData;
import com.csuft.zzc.schoolfellow.im.SCMessageHandler;
import com.csuft.zzc.schoolfellow.im.data.ImMsgData;
import com.csuft.zzc.schoolfellow.im.model.ImMsgItem;
import com.csuft.zzc.schoolfellow.user.UserManager;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangzhi on 16/4/27.
 */
public class P2PChatAct extends BaseFragmentActivity {
    private static final String TAG = "P2PChatAct";

    private UserData mOtherUser;
    private EditText mMEditText;
    private RecyclerView mMsgRecyclerView;

    protected List<ImMsgItem> mMsgList;
    UserData mLoginUser;
    private MsgAdapter mMAdapter;
    ChatMessageHandler mMessageHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p2pchat_act);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            mOtherUser = (UserData) bundle.getSerializable("user");
        } else {

        }
        TextView nameTxt = (TextView) findViewById(R.id.user_name);
        nameTxt.setText(mOtherUser.userName);
        mMsgList = new ArrayList<>();
        mLoginUser = UserManager.getInstance().getUser();
        mMEditText = (EditText) findViewById(R.id.text);
        mMsgRecyclerView = (RecyclerView) findViewById(R.id.msgs_view);
        mMsgRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMAdapter = new MsgAdapter();
        mMsgRecyclerView.setAdapter(mMAdapter);
        mMsgRecyclerView.addItemDecoration(new SpaceItemDecoration(ScreenUtil.instance().dip2px(10)));
        mMessageHandler = new ChatMessageHandler();
        ImUtil.getChatRecord(mOtherUser.userName, new AVIMMessagesQueryCallback() {
            @Override
            public void done(List<AVIMMessage> list, AVIMException e) {
                if (e != null) {
                    e.printStackTrace();
                } else if (list != null) {
                    for (AVIMMessage msg : list) {
                        ScLog.i(TAG, "msg=> " + msg.getFrom() + " " + ((AVIMTextMessage) msg).getText());
                        addMsg(new ImMsgItem(msg));
                    }
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        AVIMMessageManager.registerMessageHandler(AVIMMessage.class, mMessageHandler);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AVIMMessageManager.unregisterMessageHandler(AVIMMessage.class, mMessageHandler);
    }

    public void send(View view) {
        ScLog.i(TAG, "send");
        String msgContent = mMEditText.getText().toString();
        ImMsgItem imMsgItem = new ImMsgItem();
        imMsgItem.from = mLoginUser.userName;
        imMsgItem.msg = msgContent;
        imMsgItem.time = System.currentTimeMillis();
        addMsg(imMsgItem);
        ImUtil.sendTextMsgToSingle(mOtherUser.userName, msgContent);
        HashMap<String, String> params = new HashMap<>();
        params.put("from", UserManager.getInstance().getUserName());
        params.put("to", mOtherUser.userName);
        params.put("msg", msgContent);
        BaseApi.getInstance().post(BaseApi.HOST_URL + "/send_msg", params, null, null);
        mMsgRecyclerView.scrollToPosition(mMsgList.size() - 1);
        mMEditText.setText("");

    }

    protected void addMsg(ImMsgItem imMsgItem) {
        mMsgList.add(imMsgItem);
        mMAdapter.notifyDataSetChanged();
    }

    public void back(View view) {
        finish();
    }


    class MsgAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(P2PChatAct.this).inflate(R.layout.im_msg_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(root);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ImMsgItem item = mMsgList.get(position);
            boolean isMe = item.from.equals(mLoginUser.userName);

            if (isMe) {
                RelativeLayout.LayoutParams msgParams = new RelativeLayout.LayoutParams((ViewGroup.MarginLayoutParams) holder.msgTxt.getLayoutParams());
                msgParams.addRule(RelativeLayout.LEFT_OF, holder.avatar.getId());
                msgParams.addRule(RelativeLayout.CENTER_VERTICAL);
                holder.msgTxt.setLayoutParams(msgParams);
                holder.msgTxt.setBackgroundResource(R.drawable.jmui_msg_send_bg);
                RelativeLayout.LayoutParams avatarParams = new RelativeLayout.LayoutParams((ViewGroup.MarginLayoutParams) holder.avatar.getLayoutParams());
                avatarParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                avatarParams.addRule(RelativeLayout.CENTER_VERTICAL);
                holder.avatar.setLayoutParams(avatarParams);

                holder.avatar.setImageUrl(mLoginUser.avatar, true);
            } else {

                RelativeLayout.LayoutParams msgParams = new RelativeLayout.LayoutParams((ViewGroup.MarginLayoutParams) holder.msgTxt.getLayoutParams());
                msgParams.addRule(RelativeLayout.RIGHT_OF, holder.avatar.getId());
                msgParams.addRule(RelativeLayout.CENTER_VERTICAL);
                holder.msgTxt.setLayoutParams(msgParams);
                holder.msgTxt.setBackgroundResource(R.drawable.jmui_msg_receive_bg);
                RelativeLayout.LayoutParams avatarParams = new RelativeLayout.LayoutParams((ViewGroup.MarginLayoutParams) holder.avatar.getLayoutParams());
                avatarParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                avatarParams.addRule(RelativeLayout.CENTER_VERTICAL);
                holder.avatar.setLayoutParams(avatarParams);

                holder.avatar.setImageUrl(mOtherUser.avatar, true);
            }
            holder.msgTxt.setText(item.msg);

        }

        @Override
        public int getItemCount() {
            return mMsgList == null ? 0 : mMsgList.size();
        }
    }

    public class ChatMessageHandler extends AVIMMessageHandler {
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
            super.onMessage(message, conversation, client);
            if (message instanceof AVIMTextMessage) {
                ImMsgItem imMsgItem = new ImMsgItem(message);
                addMsg(imMsgItem);
                ScLog.i(TAG, "msg ==> " + message.getFrom() + " say: " + ((AVIMTextMessage) message).getText());
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            avatar = (WebImageView) itemView.findViewById(R.id.avatar_img);
            msgTxt = (TextView) itemView.findViewById(R.id.msg_txt);
        }

        public TextView msgTxt;
        public WebImageView avatar;
    }
}

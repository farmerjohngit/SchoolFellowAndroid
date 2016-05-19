package com.csuft.zzc.schoolfellow.base.im;

import android.util.Log;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.view.ScToast;
import com.csuft.zzc.schoolfellow.user.UserManager;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wangzhi on 16/5/18.
 */
public class ImUtil {
    private static final String TAG = "ImUtil";

    protected static void sendMsgInternal(final String sender, final List<String> receivers, final AVIMMessage msg) {
        AVIMClient tom = AVIMClient.getInstance(sender);

        String rece = "";
        for (int i = 0; i < receivers.size(); i++) {
            rece += receivers.get(i);
            if (i != receivers.size() - 1) {
                rece += " & ";
            }
        }
        ScLog.i(TAG, "sendMsg from " + sender + " to " + rece);
        // 与服务器连接
        final String finalRece = rece;
        tom.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient client, AVIMException e) {
                ScLog.i(TAG, "done " + e);
                if (e == null) {
                    // 创建与Jerry之间的对话
                    client.createConversation(receivers, sender + " ==> " + finalRece, null,
                            new AVIMConversationCreatedCallback() {

                                @Override
                                public void done(AVIMConversation conversation, AVIMException e) {
                                    if (e == null) {
//                                        AVIMTextMessage msg = new AVIMTextMessage();
//                                        msg.setText("耗子，起床！");
                                        // 发送消息
                                        conversation.sendMessage(msg, new AVIMConversationCallback() {

                                            @Override
                                            public void done(AVIMException e) {
                                                if (e == null) {
                                                    ScLog.i(TAG, "发送成功！");
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void sendTextMsgToSingle(String receiver, String text) {

        sendTextMsgToSingle(UserManager.getInstance().getUserName(), receiver, text);
    }

    public static void sendTextMsgToSingle(String sender, String receiver, String text) {
        sendTextMsg(sender, Arrays.asList(receiver), text);
    }

    public static void sendTextMsg(String sender, List<String> receivers, String text) {
        AVIMTextMessage msg = new AVIMTextMessage();
        msg.setText(text);
        sendMsgInternal(sender, receivers, msg);
    }

    public static void login(final String userName) {
        AVIMClient jerry = AVIMClient.getInstance(userName);
        jerry.open(new AVIMClientCallback() {

            @Override
            public void done(AVIMClient client, AVIMException e) {
                if (e == null) {
                    ScToast.toast(userName + " login ..");
                } else {
                    e.printStackTrace();
                }
            }
        });

    }
    public static void logout(final String userName){
        AVIMClient client = AVIMClient.getInstance(userName);
        client.close(null);
    }
}

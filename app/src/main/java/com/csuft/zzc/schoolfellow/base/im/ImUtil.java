package com.csuft.zzc.schoolfellow.base.im;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.csuft.zzc.schoolfellow.base.net.CallBack;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.view.ScToast;
import com.csuft.zzc.schoolfellow.im.data.RecentImMsgData;
import com.csuft.zzc.schoolfellow.user.UserManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wangzhi on 16/5/18.
 */
public class ImUtil {
    private static final String TAG = "ImUtil";

    protected static void sendMsgInternal(final String sender, final List<String> receivers, final AVIMMessage msg) {
        AVIMClient client = AVIMClient.getInstance(sender);

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
        client.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient client, AVIMException e) {
                if (e == null) {
                    // 创建与Jerry之间的对话
                    client.createConversation(receivers, sender + " ==> " + finalRece, null, false, true,
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


    public static void getChatRecord(final String otherName, final AVIMMessagesQueryCallback callback) {
        getChatRecord(UserManager.getInstance().getUserName(), otherName, callback);
    }

    public static void getChatRecord(final String userName, final String otherName, final AVIMMessagesQueryCallback callback) {
        AVIMClient tom = AVIMClient.getInstance(userName);
        tom.open(new AVIMClientCallback() {
                     @Override
                     public void done(AVIMClient client, AVIMException e) {
                         if (e == null) {
                             getChatRecordInternal(userName, otherName, client, callback);
                         }
                     }
                 }
        );
        ScLog.i(TAG, "========>  " + userName);
    }


    protected static void getChatRecordInternal(String userName, String otherName, final AVIMClient client, final AVIMMessagesQueryCallback callback) {
        AVQuery<AVObject> query = new AVQuery<>("_Conversation");
        query.whereContainsAll("m", Arrays.asList(userName, otherName));
        query.findInBackground(new FindCallback<AVObject>() {
                                   @Override
                                   public void done(List<AVObject> list, AVException e) {
                                       if (e != null) {
                                           e.printStackTrace();
                                           return;
                                       }
                                       if (list == null) {
                                           ScLog.i(TAG, "list is null");
                                           return;
                                       }
                                       ScLog.i(TAG, "list size " + list.size());
                                       List<RecentImMsgData.RecentImMsgItem> msgList = new ArrayList<>();
                                       for (AVObject object : list) {
                                           final RecentImMsgData.RecentImMsgItem recentImMsgItem = new RecentImMsgData.RecentImMsgItem();
                                           msgList.add(recentImMsgItem);
                                           ScLog.i(TAG, object.toString());

                                           AVIMConversation conv = client.getConversation(object.getObjectId());
                                           int limit = 20;// limit 取值范围 1~1000 之内的整数
                                           // 不使用 limit 默认返回 20 条消息
                                           conv.queryMessages(limit, callback);
                                       }
                                   }
                               }

        );

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

    public static void logout(final String userName) {
        AVIMClient client = AVIMClient.getInstance(userName);
        client.close(null);
    }
}

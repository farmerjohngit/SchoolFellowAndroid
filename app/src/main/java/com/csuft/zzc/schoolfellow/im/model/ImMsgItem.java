package com.csuft.zzc.schoolfellow.im.model;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

/**
 * Created by wangzhi on 16/5/19.
 */
public class ImMsgItem {
    public ImMsgItem() {
    }

    public ImMsgItem(AVIMMessage msg) {
        if (msg instanceof AVIMTextMessage) {
            this.from = msg.getFrom();
            this.msg = ((AVIMTextMessage) msg).getText();
            this.time = msg.getTimestamp();
        }else{
            throw new UnsupportedOperationException("unknow msg type");
        }
    }

    public String from;

    public String to;

    public String msg;

    public String msgType;

    public long time;
}

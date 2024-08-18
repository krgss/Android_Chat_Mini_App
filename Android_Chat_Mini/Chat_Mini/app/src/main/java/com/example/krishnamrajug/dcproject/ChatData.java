package com.example.krishnamrajug.dcproject;

import java.io.Serializable;

public class ChatData implements Serializable {
    private static final long serialVersionUID = 1L;
    String fromUser;
    String toUser;
    String chatText;

    public ChatData() {

    }

    public String getChatText() {
        return chatText;
    }

    public String getFromUser() {
        return fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setChatText(String chatText) {
        this.chatText = chatText;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public void assign(ChatData chatData){
        this.fromUser = chatData.fromUser;
        this.toUser = chatData.toUser;
        this.chatText = chatData.chatText;
    }
}

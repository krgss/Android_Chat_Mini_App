package com.example.krishnamrajug.dcproject;

class ChatThreadInstance {
    private static final ChatThreadInstance ourInstance = new ChatThreadInstance();
    private static ChatClientThread chatClientThread;

    static ChatThreadInstance getInstance(String fromUser, String transportAddress, int port) {
        if(chatClientThread == null) {
            chatClientThread = new ChatClientThread(fromUser, transportAddress, port);
            chatClientThread.start();
        }
        return ourInstance;
    }

    private ChatThreadInstance() {
    }

    public void sendMessage(ChatData chatData){
        chatClientThread.sendChatData(chatData);

    }
}

package com.example.krishnamrajug.dcproject;

import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class ChatClientThread extends Thread{

    String name;
    String dstAddress;
    int dstPort;

    String msgToSend = "";
    String msgLog="";
    boolean goOut = false;
    ChatData chatData;

    ChatClientThread(String name, String address, int port)
    {
        this.name = name;
        dstAddress = address;
        dstPort = port;
    }

    @Override
    public void run()
    {
        Socket socket = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            socket = new Socket(dstAddress, dstPort);

            objectOutputStream = new ObjectOutputStream(
                    socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            ChatData chatData = new ChatData();
            chatData.setFromUser(this.name);
            objectOutputStream.writeObject(chatData);
            objectOutputStream.flush();

                while (!goOut)
                {
                    if (objectInputStream.available() > 0)
                    {
                        ChatData rcvdChatData=null;
                        rcvdChatData = (ChatData)objectInputStream.readObject();
                        msgLog = ""+rcvdChatData.getChatText();
                        final MainActivity chatWindow = new MainActivity();
                        ChatWindow.this.runOnUiThread(new Runnable()
                        {

                            @Override
                            public void run() {
                                chatWindow.chatMsg.setText(msgLog);
                            }
                        });
                    }

                    if(!msgToSend.equals("")){
                        ChatData chatData1 = new ChatData();
                        chatData1.setFromUser(this.name);
                        objectOutputStream.writeObject(chatData);
                        objectOutputStream.flush();
                        msgToSend = "";
                    }
                }

        } catch (UnknownHostException e) {
            e.printStackTrace();
            final String eString = e.toString();
            ChatClientThread.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(ChatClientThread.this, eString, Toast.LENGTH_LONG).show();
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
            final String eString = e.toString();
            ChatClientThread.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, eString, Toast.LENGTH_LONG).show();
                }

            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    public void sendMsg(String msg){
        msgToSend = msg;
    }

    public void sendChatData(ChatData chatData){
        this.chatData = chatData;
        msgToSend = chatData.getChatText();
    }
}


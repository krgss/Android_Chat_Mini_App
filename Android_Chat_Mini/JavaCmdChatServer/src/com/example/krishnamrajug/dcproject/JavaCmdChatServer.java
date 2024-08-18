package com.example.krishnamrajug.dcproject;

import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class JavaCmdChatServer {

    static final int SocketServerPORT = 9090;

    String msgLog = "";

    //List<ChatClient> userList;
    List<String> userList;
    List<ChatClient> clientList;

    ServerSocket serverSocket;

    public static void main(String[] args) {
        JavaCmdChatServer ChatServer = new JavaCmdChatServer();

        while (true) {
        }
    }

    JavaCmdChatServer() {
        System.out.print(getIpAddress());
        userList = new ArrayList<>();
        clientList = new ArrayList<>();
        ChatServerThread chatServerThread = new ChatServerThread();
        chatServerThread.start();

    }

    private class ChatServerThread extends Thread {

        public void run() {
            Socket socket = null;

            try {
                serverSocket = new ServerSocket(SocketServerPORT);
                System.out.println("Server ON: "
                    + serverSocket.getLocalPort());
                    System.out.println("Press CTRL + C to quit");

                while (true) {
                    socket = serverSocket.accept();
                    ChatClient client = new ChatClient();
                    ConnectThread connectThread = new ConnectThread(client, socket);
                    client.chatThread = connectThread;
                    clientList.add(client);
                    connectThread.start();
                }

            } catch (IOException e) {
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
            }

        }

    }

    

    private class ConnectThread extends Thread {

        Socket socket;
        ChatClient connectClient;
        String msgToSend = "";
        ObjectOutputStream objectOutputStream = null;

        ConnectThread(ChatClient client, Socket socket) {
            connectClient = client;
            this.socket = socket;
            client.socket = socket;
            client.chatThread = this;
        }

        @Override
        public void run() {
            ObjectInputStream objectInputStream = null;
            

            try {
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                

                while (true) {
                        ChatData chatData = (ChatData)objectInputStream.readObject();
                        if(chatData != null && chatData.getToUser().isEmpty()) {
                        		if(!userList.contains(chatData.getFromUser())) {
                        			userList.add(chatData.getFromUser());
                        			connectClient.name = chatData.getFromUser();
                        			System.out.println("New user added " + chatData.getFromUser());
                        		}
                        }else {
                        		if(userList.contains(chatData.getToUser())) {
                        			if(hasClient(chatData)) {
                        				objectOutputStream.writeObject(msgToSend);
                        				msgToSend = "";
                        			}
                        		}else {
                        			System.out.println(chatData.getToUser() + " is not online");
                        		}
                        }
                        
                    }
                //}

            } catch (IOException e) {
            	// TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
            	// TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (objectInputStream != null) {
                    try {
                    	objectInputStream.close();
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

                userList.remove(connectClient);

                System.out.println(connectClient.name + " removed.");

                msgLog = "-- " + connectClient.name + " leaved\n";
                System.out.println(msgLog);

            }

        }

        private void sendMsg(String msg) {
            this.msgToSend = msg;
        }

    }

    private Boolean hasClient(ChatData chatData) {
        for (int i = 0; i < clientList.size(); i++) {
        	if(clientList.get(i).name.equals(chatData.getToUser())){
				clientList.get(i).chatThread.sendMsg(chatData.getChatText());
            	msgLog = "- send to " + clientList.get(i).name + "\n";
            	System.out.print(msgLog);
            	return true;
        	}
        }
        return false;
    }

    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                    .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                    .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "SiteLocalAddress: "
                            + inetAddress.getHostAddress() + "\n";
                    }

                }

            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        return ip;
    }

    class ChatClient {

        String name;
        Socket socket;
        ConnectThread chatThread;

    }

}
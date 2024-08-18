package com.example.krishnamrajug.dcproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChatWindow extends Activity {
    static final int SocketServerPORT = 9090;
    String textAddress = "192.168.0.108";
    LinearLayout loginPanel, chatPanel;

    EditText editTextUserName;
    Button buttonConnect;
    TextView chatMsg, textPort;
    EditText editTextSay;
    Button buttonSend;
    Button buttonDisconnect;

    String msgLog = "";

    ChatClientThread chatClientThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        chatPanel = (LinearLayout)findViewById(R.id.chatpanel);

        editTextUserName = (EditText) findViewById(R.id.username);

        chatMsg = (TextView) findViewById(R.id.chatmsg);


        editTextSay = (EditText)findViewById(R.id.say);
        buttonSend = (Button)findViewById(R.id.send);
        chatPanel.setVisibility(View.VISIBLE);
        buttonSend.setOnClickListener(buttonSendOnClickListener);
    }

    View.OnClickListener buttonDisconnectOnClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            if (chatClientThread == null)
            {
                return;
            }
            chatClientThread.disconnect();
        }
    };

    View.OnClickListener buttonSendOnClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            if (editTextSay.getText().toString().equals(""))
            {
                return;
            }

            if(chatClientThread==null)
            {
                return;
            }
            ChatData chatData = new ChatData();
            chatData.setFromUser("Nani");
            chatData.setToUser("Madhu");
            chatData.setChatText(editTextSay.getText().toString() + "\n");

            ChatThreadInstance chatThreadInstance = ChatThreadInstance.getInstance(chatData.getFromUser(),textAddress,SocketServerPORT);
            chatThreadInstance.sendMessage(chatData);
        }

    };

    View.OnClickListener buttonConnectOnClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            String textUserName = editTextUserName.getText().toString();
            if (textUserName.equals(""))
            {
                Toast.makeText(ChatWindow.this, "Enter User Name",
                        Toast.LENGTH_LONG).show();
                return;
            }

            msgLog = "";
            chatMsg.setText(msgLog);
            loginPanel.setVisibility(View.GONE);

        }

    };
}


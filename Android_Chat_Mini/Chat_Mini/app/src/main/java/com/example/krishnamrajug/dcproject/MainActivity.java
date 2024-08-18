package com.example.krishnamrajug.dcproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final int SocketServerPORT = 9090;

    LinearLayout loginPanel, chatPanel;

    EditText editTextUserName;
    Button buttonConnect;
    TextView chatMsg;

    String msgLog = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginPanel = (LinearLayout)findViewById(R.id.loginpanel);
        editTextUserName = (EditText) findViewById(R.id.username);
        buttonConnect = (Button) findViewById(R.id.connect);
        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
        chatMsg = (TextView) findViewById(R.id.chatmsg);
    }

    OnClickListener buttonConnectOnClickListener = new OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            String textUserName = editTextUserName.getText().toString();
            if (textUserName.equals(""))
            {
                Toast.makeText(MainActivity.this, "Enter User Name",
                        Toast.LENGTH_LONG).show();
                return;
            }

            String textAddress = "192.168.0.108";

            msgLog = "";
            chatMsg.setText(msgLog);
            loginPanel.setVisibility(View.GONE);
            ChatThreadInstance chatThreadInstance = ChatThreadInstance.getInstance(textUserName,textAddress,SocketServerPORT);
            startActivity(new Intent(MainActivity.this,UserList.class));
            loginPanel.setVisibility(View.VISIBLE);
        }

    };

}


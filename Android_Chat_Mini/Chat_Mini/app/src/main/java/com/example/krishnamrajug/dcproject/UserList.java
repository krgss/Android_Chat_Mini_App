package com.example.krishnamrajug.dcproject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserList extends ListActivity {
    TextView content;

    private TextView text;
    private List<String> listValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        text = (TextView) findViewById(R.id.mainText);
        listValues = new ArrayList<String>();
        listValues.add("Krishnam");
        listValues.add("Raju");
        listValues.add("Sindhu");
        listValues.add("Gayathri");

        ArrayAdapter<String> myAdapter = new ArrayAdapter <String>(this,
                R.layout.row_layout, R.id.listText, listValues);
        setListAdapter(myAdapter);
    }
    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
        String selectedItem = (String) getListView().getItemAtPosition(position);
        startActivity(new Intent(UserList.this,ChatWindow.class));
    }
}


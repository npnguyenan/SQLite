package com.example.sqlite_01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.TypedArrayUtils;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    DataUser dataUser;
    Button btn_add,btn_remove;
    EditText txtf_name;
    ListView listview_name;
    ArrayList nameList,idList;
    ArrayAdapter adapter;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataUser = new DataUser(this,
                "userdb.sqlite",
                null,1);

        idList = new ArrayList();
        nameList = new ArrayList();

        txtf_name = findViewById(R.id.txtf_name);
        btn_add = findViewById(R.id.btn_add);
        btn_remove = findViewById(R.id.btn_remove);
        listview_name= findViewById(R.id.listview_name);

        getNameList();

        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,nameList);
        listview_name.setAdapter(adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataUser.addUser(new User(txtf_name.getText().toString()));
                getNameList();
                adapter.notifyDataSetChanged();
                txtf_name.setText("");
            }
        });

        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataUser.removeUser((int)(idList.get(index)));
                Toast.makeText(MainActivity.this,
                        nameList.get(index)+ " removed", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                getNameList();
            }
        });
        listview_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int i, long id) {
                index = i;
            }
        });
    }
    private ArrayList getNameList(){
        nameList.clear();
        idList.clear();
        for (Iterator iterator = dataUser.getAll().iterator(); iterator.hasNext(); ) {
            User user =(User)  iterator.next();

            nameList.add(user.getName());
            idList.add(user.getId());
        }
        return nameList;
    }
}
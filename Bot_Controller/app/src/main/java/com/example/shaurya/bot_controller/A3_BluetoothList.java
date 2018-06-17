package com.example.shaurya.bot_controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class A3_BluetoothList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a3__bluetooth_list);
        final ArrayList<String> bluetoothlist = new ArrayList<>();
        bluetoothlist.add("a");bluetoothlist.add("b");bluetoothlist.add("c");bluetoothlist.add("d");bluetoothlist.add("e");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,bluetoothlist);


        ListView bluetoothlistview = (ListView) findViewById(R.id.A3_lst_bluetooth);
        bluetoothlistview.setAdapter(arrayAdapter);
        bluetoothlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Toast.makeText(A3_BluetoothList.this,"Connecting to "+bluetoothlist.get(i)+"..",Toast.LENGTH_SHORT).show();
               connectodevice(i);
               Intent intent = new Intent(A3_BluetoothList.this,A2_Remote.class);
               startActivity(intent);

            }
        });

    }

    private void connectodevice(int i) {
    }
}

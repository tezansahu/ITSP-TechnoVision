package com.example.shaurya.bot_controller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class A3_BluetoothList extends AppCompatActivity {

    int REQUEST_ENABLE_BT =1;
    ArrayList<String> bluetoothlist = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    BluetoothAdapter bluetoothAdapter ;
    ListView bluetoothlistview ;
    Button finddevicesbutton;
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK)
            {
                } else if (resultCode == RESULT_CANCELED) {
            }
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a3__bluetooth_list);
        bluetoothAdapter =  BluetoothAdapter.getDefaultAdapter();
        finddevicesbutton = (Button) findViewById(R.id.finddevicesbutton);
        bluetoothlistview = (ListView) findViewById(R.id.A3_lst_bluetooth);

/*

        if (bluetoothAdapter==null)
        {
            Toast.makeText(this,"Your device does not support Bluetooth",Toast.LENGTH_LONG).show();

        }
        else
        { if (!bluetoothAdapter.isEnabled())
        {
            Intent enablebluetoothintent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enablebluetoothintent,REQUEST_ENABLE_BT);
        }
        }
        */






        finddevicesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(A3_BluetoothList.this,"buttonclicked",Toast.LENGTH_SHORT).show();
              //  bluetoothAdapter.startDiscovery();
                Set<BluetoothDevice> bt = bluetoothAdapter.getBondedDevices();
                String[] strings= new String[bt.size()];
                int index =0;
                if (bt.size()>0)
                {
                    for (BluetoothDevice device : bt)
                    {
                        strings[index]= device.getName();
                        index++;
                        arrayAdapter= new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,strings);
                        bluetoothlistview.setAdapter(arrayAdapter);
                    }

                }
            }
        });


/*
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(broadcastReceiver,intentFilter);
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,bluetoothlist);
        bluetoothlistview.setAdapter(arrayAdapter);
    */
    }

   /* BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(A3_BluetoothList.this,"bradcast receiver",Toast.LENGTH_LONG).show();

            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action))
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                bluetoothlist.add(device.getName());
              arrayAdapter.notifyDataSetChanged();


            }
        }
    };
*/


    private void connectodevice(int i) {

    }


}




















/*


                bluetoothlist.add("a");bluetoothlist.add("b");bluetoothlist.add("c");bluetoothlist.add("d");bluetoothlist.add("e");




        bluetoothlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
@Override
public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(A3_BluetoothList.this,"Connecting to "+bluetoothlist.get(i)+"..",Toast.LENGTH_SHORT).show();
        connectodevice(i);
        Intent intent = new Intent(A3_BluetoothList.this,A2_Remote.class);
        startActivity(intent);

        }
        });


*/
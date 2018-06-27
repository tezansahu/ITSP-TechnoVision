package com.example.shaurya.bot_controller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class A3_BluetoothList extends AppCompatActivity {

    int REQUEST_ENABLE_BLUETOOTH =1;
    static final int STATE_LISTENING =1;
    static final int STATE_CONNECTING =2;
    static final int STATE_CONNECTED =3;
    static final int STATE_CONNECTION_FAILED =4;
    static final int STATE_MESSAGE_RECEIVED =5;
    private  static  final  String APP_NAME ="BOT_CONTROLLER";
    private  static  final  UUID my_uuid = UUID.fromString("f2334a44-d192-4da4-aef3-57c0889c9bf3");
    SendReceive sendReceive;

    String[] strings;
    ArrayAdapter<String> arrayAdapter;
    BluetoothAdapter bluetoothAdapter ;
    ListView bluetoothlistview ;
    TextView statusview;
    Button refreshbutton;
    Set<BluetoothDevice> bt;

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
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        refreshbutton = (Button) findViewById(R.id.refreshbutton);
        bluetoothlistview = (ListView) findViewById(R.id.A3_lst_bluetooth);
        statusview = (TextView) findViewById(R.id.status);


        if (!bluetoothAdapter.isEnabled())
        {
            Intent enableintent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableintent,REQUEST_ENABLE_BLUETOOTH);
        }
        
        implementlisteners();

        bt = bluetoothAdapter.getBondedDevices();
        strings = new String[bt.size()];
        int index =0;
        if (bt.size() > 0) {
            for (BluetoothDevice device : bt) {
                strings[index] = device.getName();
                index++;
            }
        }






        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, strings);
        bluetoothlistview.setAdapter(arrayAdapter);

        bluetoothlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(A3_BluetoothList.this, "Connecting to " + strings[i] + "..", Toast.LENGTH_SHORT).show();
                connectodevice(i);
               // Intent intent = new Intent(A3_BluetoothList.this,A2_Remote.class);
               // startActivity(intent);
                }
        });



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









/*
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(broadcastReceiver,intentFilter);
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,bluetoothlist);
        bluetoothlistview.setAdapter(arrayAdapter);
    */
    }
    private  class ServerClass extends Thread{
        private BluetoothServerSocket serverSocket;
        public  ServerClass()
        {
            try {
                serverSocket=bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME,my_uuid);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public void run(){
            BluetoothSocket socket = null;
            while (socket==null)
            {
                try {
                    Message message =Message.obtain();
                    message.what=STATE_CONNECTING;
                    handler.sendMessage(message);
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                    Message message =Message.obtain();
                    message.what=STATE_CONNECTION_FAILED;
                    handler.sendMessage(message);
                }
                if (socket!=null)
                { Message message =Message.obtain();
                    message.what=STATE_CONNECTED;
                    handler.sendMessage(message);

                    sendReceive = new SendReceive(socket);
                    sendReceive.start();
                    break;
                }
            }
        }

    }

    private  class ClientClass extends Thread{
        private BluetoothDevice device;
        private  BluetoothSocket socket;
        public  ClientClass(BluetoothDevice device1)
        { device = device1;
            try {
                socket = device.createRfcommSocketToServiceRecord(my_uuid);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public void run(){
            try {
                socket.connect();
                Message message = Message.obtain();
                message.what= STATE_CONNECTED;
                handler.sendMessage(message);
                SendReceive sendReceive = new SendReceive(socket);
                sendReceive.start();
            } catch (IOException e) {
                e.printStackTrace();
                Message message = Message.obtain();
                message.what= STATE_CONNECTION_FAILED;
                handler.sendMessage(message);
            }
        }

    }


    private class SendReceive extends Thread{
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendReceive(BluetoothSocket socket)
        { bluetoothSocket = socket;
         InputStream tempIn = null;
         OutputStream tempOut = null;
            try {
                tempIn = bluetoothSocket.getInputStream();
                tempOut = bluetoothSocket.getOutputStream();

            } catch (IOException e) {
                e.printStackTrace();
            }
            inputStream = tempIn;
            outputStream = tempOut;



        }
        public void run()
        {
            byte[] buffer = new byte[1024];
            int bytes;
            while (true) {
                try {
                    bytes = inputStream.read(buffer);
                    handler.obtainMessage(STATE_MESSAGE_RECEIVED, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        public void write(byte[] bytes) {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
           String string=null;
            switch (message.what)
            {
                case STATE_LISTENING: string= "LISTENING";break;
                case STATE_CONNECTING: string= "CONNECTING";break;
                case STATE_CONNECTED: string= "CONNECTED";break;
                case STATE_CONNECTION_FAILED: string= "CONNECTION FAILED";break;
                case STATE_MESSAGE_RECEIVED: byte[]  readBuff = (byte[]) message.obj;
                string = new String(readBuff,0,message.arg1);break;
                }
                statusview.setText("STATUS:"+ string);
            return true;
        }
    });





    private void implementlisteners() {
       // ServerClass serverClass = new ServerClass();
        //serverClass.start();
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

        BluetoothDevice[] devices = (BluetoothDevice[]) bt.toArray();
        ClientClass clientClass = new ClientClass(devices[i]);
        clientClass.start();
        statusview.setText("CONNECTING");


        }


    public void refreshbutton(View view) {
        Toast.makeText(A3_BluetoothList.this, "buttonclicked", Toast.LENGTH_SHORT).show();
        recreate();
        }

    public void logoutbutton(View view) {
        Intent intent = new Intent(this, A1_Login.class);
        startActivity(intent);
    }


}
















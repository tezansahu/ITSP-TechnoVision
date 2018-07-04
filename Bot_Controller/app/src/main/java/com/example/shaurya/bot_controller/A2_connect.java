package com.example.shaurya.bot_controller;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.Set;

public class A2_connect extends Activity {


    private static final String TAG = "BOT_CONTROLLER_A2_conne";

    private static final int REQUEST_ENABLE_BT = 3;
    public static BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    public static BluetoothChatService mChatService;
    private String mConnectedDeviceName = null;

    public final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    Toast.makeText(A2_connect.this, "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case Constants.MESSAGE_TOAST:
                    Toast.makeText(A2_connect.this, msg.getData().getString(Constants.TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG,"oncreatecalled");
        mChatService = new BluetoothChatService(A2_connect.this,mHandler);

        // Setup the window
        setContentView(R.layout.activity_a2_connect);


        // Set result CANCELED in case the user backs out


        // Initialize the button to perform device discovery
        Button scanButton = (Button) findViewById(R.id.scanbutton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG,"onclickcalled");
                doDiscovery();
                }
        });

        // Initialize array adapters. One for already paired devices and
        // one for newly discovered devices
        ArrayAdapter<String> pairedDevicesArrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        // Find and set up the ListView for paired devices
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices_listview);
        pairedListView.setAdapter(pairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // Find and set up the ListView for newly discovered devices
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices_listview);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);

        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                pairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
        } else {
            String noDevices = getResources().getText(R.string.none_paired).toString();
            pairedDevicesArrayAdapter.add(noDevices);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setStatus("Select a device to connect");
        if (!mBtAdapter.isEnabled())
        {
            Log.d(TAG,"ONSTART bluetooth not on_");
            A1_Login.BT_ENABLED=true;
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_ENABLE_BT)
        { if (resultCode!=RESULT_OK)
        {Toast.makeText(A2_connect.this,"App cannot work without bluetooth",Toast.LENGTH_LONG).show();
        finish();}
        else {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"ondestroycalled");

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {
        Log.d(TAG, "doDiscoverycalled");

        // Indicate scanning in the title

        setStatus("Scanning for devices…");


        // Turn on sub-title for new devices
        findViewById(R.id.new_devices).setVisibility(View.VISIBLE);
        findViewById(R.id.new_devices_listview).setVisibility(View.VISIBLE);

        // If we're already discovering, stop it
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
    }

    private void setStatus(String string) {
        Log.d(TAG, "setstatuscalled");
        TextView status = (TextView) findViewById(R.id.status);
        status.setText("STATUS: "+ string);
    }

    /**
     * The on-click listener for all devices in the ListViews
     */
    private AdapterView.OnItemClickListener mDeviceClickListener
            = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Cancel discovery because it's costly and we're about to connect
            setStatus("Connecting...");
            mBtAdapter.cancelDiscovery();

            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            // Create the result Intent and include the MAC address
            Log.d("DeviceListActivity", "Device address: "+address);
            final Intent intent = new Intent(A2_connect.this,A2_Remote.class);
            connect(address);
            new CountDownTimer(10000,1000){
                public void onTick(long millisUntilFinished) {
                    if (mChatService.getState()==BluetoothChatService.STATE_CONNECTED){
                        startActivity(intent);this.cancel();
                    }
                    }
                public void onFinish() {
                    setStatus("Select a device to connect");
                    }

            }.start();



            Log.d(TAG,"mchatservice_state: "+mChatService.getState());
            }
    };

    private void connect(String address) {
        Log.d(TAG,"connect called: address is " + address);
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        Log.d(TAG,"bluetoothdevice initialised: "+ device.getName());

        mChatService.connect(device,true);
        Log.d(TAG,"connect service called");

    }


    /**
     * The BroadcastReceiver that listens for discovered devices and changes the title when
     * discovery is finished
     */
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "onReceivecalled");

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
                setStatus("Select a device to connect");
                if (mNewDevicesArrayAdapter.getCount() == 0) {
                    String noDevices = getResources().getText(R.string.none_found).toString();
                    mNewDevicesArrayAdapter.add(noDevices);
                }
            }
        }
    };

    @Override
    public void onBackPressed() {Intent intent = new Intent(A2_connect.this,A1_Login.class);
        startActivity(intent);
        finish();

    }

    public void logoutbutton(View view) {
        Intent intent = new Intent(A2_connect.this,A1_Login.class);
        startActivity(intent);
        finish();

    }
}

package com.example.shaurya.bot_controller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class A2_Remote extends AppCompatActivity {

    ImageView up_image ;
    ImageView left_image ;
    ImageView right_image ;
    ImageView down_image ;
    ImageView servoleft_image;
    ImageView servoright_image;
    String TAG = "BOT_CONTROLLER_A2_REMOTE";


    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_ENABLE_BT = 3;

    private String mConnectedDeviceName = null;
    private BluetoothAdapter mBluetoothAdapter = null;
    public BluetoothChatService mChatService;


    private BotController mBotController;


   // private final Handler mHandlerk = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2__remote);
        Log.d(TAG,"onCReate called");
        up_image = (ImageView) findViewById(R.id.imageup);
        left_image = (ImageView) findViewById(R.id.imageleft);
        right_image = (ImageView) findViewById(R.id.imageright);
        down_image = (ImageView) findViewById(R.id.imagedown);
        servoleft_image = (ImageView) findViewById(R.id.servoleft);
        servoright_image = (ImageView) findViewById(R.id.servoright);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mChatService =A2_connect.mChatService;

       // mChatService  =((myBluetoothChatService) this.getApplication()).getMchatservice();



        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
        }

        mBotController = new BotController(mChatService);
        Log.d(TAG,"botcontroller initialised: "+mChatService.getState());

        up_image.setOnTouchListener(mBotController);
        left_image.setOnTouchListener(mBotController);
        right_image.setOnTouchListener(mBotController);
        down_image.setOnTouchListener(mBotController);
        servoleft_image.setOnTouchListener(mBotController);
        servoright_image.setOnTouchListener(mBotController);
        }



    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        Log.d(TAG,"onStart called");
        if (!mBluetoothAdapter.isEnabled()) {
            Log.d(TAG,"ONSTART bluetooth not on_");
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else if (mChatService == null) {
           // mChatService = new BluetoothChatService(this, mHandler);
            Log.d(TAG,"bluetoothchat service initialised");

        }
        mBotController.mChatService = mChatService;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDEstroycalled");
        if (mChatService != null) {
            mChatService.stop();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"onResume called");

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            Log.d(TAG,"mchatservice is not null");
            Log.d(TAG,"mChatService.state: "+ mChatService.getState());

            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();

            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"onActivityResult called");

        if(requestCode==REQUEST_ENABLE_BT){
            if(resultCode != RESULT_OK){
                finish();
            } else {
               // mChatService = new BluetoothChatService(this, mHandler);

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void A2_signout(View view) {
        mChatService.stop();
        Intent intent = new Intent(this,A1_Login.class);
        startActivity(intent);

    }
}

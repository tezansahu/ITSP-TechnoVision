package com.example.shaurya.bot_controller;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * class BotController
 *
 * A seperate controller object which processes UI touch input
 * and interfaces with the Bluetooth communication service.
 *
 * It generates and stores the proper bit sequence
 * for each corresponding button, and handles the process of
 * initiating a "send" operation whenever needed
 */
public class BotController implements View.OnTouchListener {
    BluetoothChatService mChatService;

    public static final String TAG = "BotControllerClass";

    private int mMotorState = 7; // Store the last bit sequence sent|to be sent

    // PIN mappings of motor control bits to ATtiny PORTB pins
    private static final int MOTOR_FWD=1;
    private static final int MOTOR_BCK=4;
    private static final int MOTOR_LEFT=2;
    private static final int MOTOR_RIGHT=3;
    private static final int SERVO_LEFT=5;
    private static final int SERVO_RIGHT=6;


    public BotController(BluetoothChatService chatService){
        mChatService=chatService;
        Log.d(TAG,"constructor called");

        }

    // Set the bit specified by "which" using the PIN mappping to "bit"(0|1)
    // Only affects individual bits so two buttons pressed simultaneously will work


    // reset bits to 0b0000000
    private void reset(){
        Log.d(TAG,"reset called");mMotorState = 0;
    }


    // Initiate a send operation with the message as the bit sequence "b"
    public void sendMessage(int b) {
        // Check that we're actually connected before trying anything
        Log.d(TAG,"sendmessage called");
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Log.w("BluetoothWarn", "Not connected to any device, Please connect first!");
            return;
        }
        byte msg[] = {(byte)b};
        mChatService.write(msg);
        Log.d(TAG,"mchatservice.write called");
    }




    // Handle touch events on every button
    @Override
    public boolean onTouch(View v, MotionEvent event){
        Log.d(TAG,"onTouch called");
        boolean updated = true;
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "Action Down");
                v.setPressed(true);

                // Switch case to set a corresponding
                // bit sequence according to the button pressed
                switch (v.getId()) {
                    case R.id.imageup:
                        mMotorState=MOTOR_FWD;
                        Log.d("BotControllerClass","up clicked");break;
                    case R.id.imagedown:
                        mMotorState=MOTOR_BCK;
                        break;
                    case R.id.imageleft:
                        mMotorState=MOTOR_LEFT;
                        break;
                    case R.id.imageright:
                        mMotorState=MOTOR_RIGHT;
                        break;
                    case R.id.servoleft:
                        mMotorState=SERVO_LEFT;
                        break;
                    case R.id.servoright:
                        mMotorState=SERVO_RIGHT;
                        break;

                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                Log.d(TAG, "Action Up");
                v.setPressed(false);
                updated=true;

                // Similar switch case to reset bits when button is left
                // In effect the motion of bot will last till the button is pressed
                switch (v.getId()) {
                    case R.id.imageup:
                    case R.id.imagedown:
                    case R.id.imageleft:
                    case R.id.imageright:
                        case R.id.servoleft:
                    case R.id.servoright:

                        reset();
                        break;
                        }
                break;
            default:
                updated=false;
                break;
        }

        // Check if updated, and then only send
        // We don want to unnecessarily send data
        if(updated) sendMessage(mMotorState);
        return true;
    }
}

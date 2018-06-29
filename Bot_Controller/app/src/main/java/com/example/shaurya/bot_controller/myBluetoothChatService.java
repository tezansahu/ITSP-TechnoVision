package com.example.shaurya.bot_controller;

import android.app.Application;

public class myBluetoothChatService extends Application {
    private BluetoothChatService mchatservice;
    public myBluetoothChatService()
    {mchatservice=null;}

    public BluetoothChatService getMchatservice() {
        return mchatservice;
    }

    public void setMchatservice(BluetoothChatService mchatservice) {
        this.mchatservice = mchatservice;
    }
}

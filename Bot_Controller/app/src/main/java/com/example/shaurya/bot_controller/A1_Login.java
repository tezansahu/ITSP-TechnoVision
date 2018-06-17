package com.example.shaurya.bot_controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class A1_Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a1__login);
    }

    public String url = "https://technovision.pythonanywhere.com/Prof_Data/";
    public String urlextension = "/?format=json";

    public void clicklogin(View view) {
        Log.d("clicklogin_method", "clicklogincalled");


        Ion.with(this)
                .load((url +"Prof"+ urlextension))
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.d("ion debug", "connection successful String recieved ="+result);
                        processdata(result);
                    }
                });
    }


    private void processdata(String data) {
        try {
            EditText usernameview = (EditText) findViewById(R.id.A1_txt_LoginID);
            String username = usernameview.getText().toString();
            Log.d("JSON", "processdata method called"+data);

            int c= 0;

            JSONArray jsonarray = new JSONArray(data);
            Log.d("JSON", "creating json array");
            JSONObject J;


            for (int i = 0; i < jsonarray.length(); ++i) {
                J = jsonarray.getJSONObject(i);
                if (username.equals(J.getString("Prof_Name"))) {
                    Log.d("JSONobject","username matched");
                    login(J);
                    c=1;
                    break;
                }

            }
            if (c==0)
            { Toast.makeText(this, "LoginID not found", Toast.LENGTH_SHORT).show(); }


        } catch (JSONException e) {
            Toast.makeText(this,"Server is down, please try after sometime",Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }
    }

    private void login(JSONObject jsonObject) throws JSONException {

        if (!jsonObject.isNull("Password")) {
            String password = jsonObject.getString("Password");
            Log.d("JSONobject","inside login");
            EditText passwordview = (EditText) findViewById(R.id.A1_txt_Pass);
            String enteredpassword = passwordview.getText().toString();
            if (enteredpassword.equals(password)) {
                Log.d("JSONobject","password matched");

                EditText login_username = (EditText) findViewById(R.id.A1_txt_LoginID);
                String jsonstring = jsonObject.toString();
                Intent intent = new Intent(this, A3_BluetoothList.class);
                intent.putExtra("jsonstring", jsonstring);
                Log.d("JSONobject","activity going to start");
                startActivity(intent);
                finish();
                Log.d("JSONobject","activity started");
            }
            else {
                Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show();

            }
        }

    }


}

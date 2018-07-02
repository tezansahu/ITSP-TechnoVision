package com.example.shaurya.bot_controller;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class A1_Login extends AppCompatActivity {
    public String TAG = "BOT_CONTROLLER";
    public static String IP_ADDRESS;
    public static String username;
    public static String password;
    public static boolean BT_ENABLED= false;

    Button loginbutton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a1__login);
        Log.d(TAG,"oncreate login called");
        loginbutton = (Button) findViewById(R.id.button);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
    }

    public String url = "https://technovision.pythonanywhere.com/Prof_Data/";
    public String urlextension = "/?format=json";

    public void clicklogin(View view) {
        Log.d("clicklogin_method", "clicklogincalled");
        loginbutton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        if (isNetworkAvailable()) {
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
        else{
            Toast.makeText(this,"No internet connection", Toast.LENGTH_LONG).show();
            loginbutton.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
            = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void processdata(String data) {
        try {
            EditText usernameview = (EditText) findViewById(R.id.A1_txt_LoginID);
            username = usernameview.getText().toString();
            Log.d("JSON", "processdata method called"+data);

            int c= 0;

            JSONArray jsonarray = new JSONArray(data);
            Log.d("JSON", "creating json array");
            JSONObject J;


            for (int i = 0; i < jsonarray.length(); ++i) {
                J = jsonarray.getJSONObject(i);
                if (username.equals(J.getString("Prof_Id"))) {
                    Log.d("JSONobject","username matched");
                    login(J);
                    c=1;
                    break;
                }

            }
            if (c==0)
            {  loginbutton.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "LoginID not found", Toast.LENGTH_SHORT).show(); }


        } catch (JSONException e) {
            loginbutton.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this,"Server seems to be down, please try after sometime",Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }
    }

    private void login(JSONObject jsonObject) throws JSONException {

        if (!jsonObject.isNull("Password")) {
            password = jsonObject.getString("Password");
            Log.d("JSONobject","inside login");
            EditText passwordview = (EditText) findViewById(R.id.A1_txt_Pass);
            String enteredpassword = passwordview.getText().toString();
            if (enteredpassword.equals(password)) {
                Log.d("JSONobject","password matched");
                EditText IP_address = (EditText) findViewById(R.id.A1_txt_IPadd);
                IP_ADDRESS = IP_address.getText().toString();
                if (IP_ADDRESScheck())
                {
                Intent intent = new Intent(this, A2_connect.class);
                startActivity(intent);
                }
                else
                {loginbutton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(A1_Login.this,"Invalid IP Address, Enter again..",Toast.LENGTH_SHORT).show();}
                Log.d("JSONobject","activity started");
            }
            else {
                loginbutton.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();

            }
        }

    }

    private boolean IP_ADDRESScheck() {
            try { String ip = IP_ADDRESS;
                if ( ip == null || ip.isEmpty() ) {
                    return false;
                }

                String[] parts = ip.split( "\\." );
                if ( parts.length != 4 ) {
                    return false;
                }

                for ( String s : parts ) {
                    int i = Integer.parseInt( s );
                    if ( (i < 0) || (i > 255) ) {
                        return false;
                    }
                }
                if ( ip.endsWith(".") ) {
                    return false;
                }

                return true;
            } catch (NumberFormatException nfe) {
                return false;
            }
        }

    @Override
    public void onBackPressed() {
      this.finishAffinity();
    }
}




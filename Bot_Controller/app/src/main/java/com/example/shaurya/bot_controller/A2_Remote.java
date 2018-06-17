package com.example.shaurya.bot_controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class A2_Remote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2__remote);
        ImageView up_image = (ImageView) findViewById(R.id.imageup);
        ImageView left_image = (ImageView) findViewById(R.id.imageleft);
        ImageView right_image = (ImageView) findViewById(R.id.imageright);
        ImageView down_image = (ImageView) findViewById(R.id.imagedown);
        ImageView servoleft_image = (ImageView) findViewById(R.id.servoleft);
        ImageView servoright_image = (ImageView) findViewById(R.id.servoright);


        up_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(A2_Remote.this, "You clicked up", Toast.LENGTH_SHORT).show();
            }
        });
        left_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(A2_Remote.this, "You clicked left", Toast.LENGTH_SHORT).show();
            }
        });
        right_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(A2_Remote.this, "You clicked right", Toast.LENGTH_SHORT).show();
            }
        });
        down_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(A2_Remote.this, "You clicked down", Toast.LENGTH_SHORT).show();
            }
        });
        servoleft_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(A2_Remote.this, "You clicked servo left", Toast.LENGTH_SHORT).show();
            }
        });
        servoright_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(A2_Remote.this, "You clicked servo right", Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void A2_signout(View view) {
        Intent intent = new Intent(this,A1_Login.class);
        startActivity(intent);

    }
}

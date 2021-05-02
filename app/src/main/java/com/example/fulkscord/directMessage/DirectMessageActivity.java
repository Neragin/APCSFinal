package com.example.fulkscord.directMessage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.fulkscord.R;

public class DirectMessageActivity extends AppCompatActivity {

    String user, friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_message);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = bundle.getString("username");
        friend = bundle.getString("friend");
    }
}
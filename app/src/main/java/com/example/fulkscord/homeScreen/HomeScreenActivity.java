package com.example.fulkscord.homeScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fulkscord.R;

public class HomeScreenActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String[] s1;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = bundle.getString("username");

        s1 = new String[]{"test", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2" , "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2" , "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2" , "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2"};

        recyclerView = findViewById(R.id.recyclerview);

        Adapter adapter = new Adapter(this, s1, user);


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

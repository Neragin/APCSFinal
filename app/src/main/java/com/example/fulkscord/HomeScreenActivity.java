package com.example.fulkscord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class HomeScreenActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String s1[], s2[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        recyclerView = findViewById(R.id.recyclerView);

        s1 = getResources().getStringArray(R.array.testing_relativeView);
        s2 = getResources().getStringArray(R.array.descriptions);

        Adapter adapter = new Adapter(this, s1, s2);
        adapter.
    }
}
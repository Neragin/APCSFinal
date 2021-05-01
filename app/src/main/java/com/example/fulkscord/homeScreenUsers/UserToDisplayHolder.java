package com.example.fulkscord.homeScreenUsers;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fulkscord.R;

public class UserToDisplayHolder extends RecyclerView.ViewHolder {

    TextView name;
    Button chat;
    View view;


    public UserToDisplayHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView
                .findViewById(R.id.name);
        chat = (Button)itemView
                .findViewById(R.id.chat);
        view = itemView;
    }
}

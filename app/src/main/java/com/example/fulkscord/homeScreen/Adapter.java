package com.example.fulkscord.homeScreen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fulkscord.R;
import com.example.fulkscord.directMessage.DirectMessageActivity;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
    String[] localDataSet;
    String user;

    public class viewHolder extends RecyclerView.ViewHolder {

        Button myBtn1;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            myBtn1 = itemView.findViewById(R.id.chat);
        }

        public Button getButton(){return myBtn1;}
    }

    Context context;
    public Adapter(Context ctx, String[] s1, String user ) {
        context = ctx;
        localDataSet = s1;
        this.user = user;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_row, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.getButton().setText(localDataSet[position]);
        holder.getButton().setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DirectMessageActivity.class);
            intent.putExtra("username", user);
            intent.putExtra("friend", holder.getButton().getText());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}

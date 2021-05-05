package com.example.fulkscord.directMessage;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fulkscord.R;
import com.example.fulkscord.messages.Message;

import java.util.LinkedList;
import java.util.Stack;

public class DMAdapter extends RecyclerView.Adapter<DMAdapter.ViewHolder>{

    private LinkedList<Message> messages;

    public DMAdapter(DirectMessageActivity directMessageActivity, @NonNull LinkedList<Message> messages) {
        this.messages = messages;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false));
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getUsername().setText(messages.get(position).getSender());
        holder.getMessage().setText(messages.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView username;
        private TextView message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.m);
        }
        public TextView getUsername() {
            return username;
        }

        public TextView getMessage() {
            return message;
        }
    }
}

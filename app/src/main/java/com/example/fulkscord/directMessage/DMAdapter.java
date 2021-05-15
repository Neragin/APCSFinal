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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

/**
 * The type Dm adapter.
 */
public class DMAdapter extends RecyclerView.Adapter<DMAdapter.ViewHolder>{

    private final ArrayList
            <Message> messages;

    /**
     * Instantiates a new Dm adapter.
     *
     * @param directMessageActivity the direct message activity
     * @param messages              the messages
     */
    public DMAdapter(DirectMessageActivity directMessageActivity, @NonNull ArrayList<Message> messages) {
        this.messages = messages;
    }

    @NonNull
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

    /**
     * The type View holder, which uses the RecyclerView ViewHolder, for the main adapter.
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView username;
        private final TextView message;

        /**
         * Instantiates a new View holder to add the username and message.
         *
         * @param itemView the item view
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.m);
        }

        /**
         * Gets username.
         *
         * @return the username
         */
        public TextView getUsername() {
            return username;
        }

        /**
         * Gets message.
         *
         * @return the message
         */
        public TextView getMessage() {
            return message;
        }
    }
}

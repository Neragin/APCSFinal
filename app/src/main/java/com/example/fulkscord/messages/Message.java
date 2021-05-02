package com.example.fulkscord.messages;

import android.widget.TextView;

import java.util.Date;

/**
 * Individual message that will be used to display on screen
 */
public class Message {

    String text, key, sender, receiver;
    Date date;

    public Message(String text, String key, String sender, String receiver, Date date) {
        this.text = text;
        this.key = key;
        this.sender = sender;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

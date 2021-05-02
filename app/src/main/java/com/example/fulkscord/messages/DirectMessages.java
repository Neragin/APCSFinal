package com.example.fulkscord.messages;

import com.example.fulkscord.user.User;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Direct messages, which are several messages, with users in question
 */
public class DirectMessages {

    private User user1, user2;
    private final Stack<Message> messages;

    public DirectMessages(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        messages = new Stack<Message>();
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public void addMessage(Message msg){ messages.push(msg); }

    public Message popMessage(){
        return messages.pop();
    }

    public void loadMessages(){
        //TODO Implement firebase code
    }

}

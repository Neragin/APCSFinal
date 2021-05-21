package com.example.fulkscord.messages;

import com.example.fulkscord.user.User;

import java.util.Stack;

/**
 * Direct messages, which are several messages, with users in question
 *
 * @author Leo X
 */
public class DirectMessages {

	private final Stack<Message> messages;
	private User user1, user2;

	/**
	 * Instantiates a new Direct messages.
	 *
	 * @param user1 the user 1
	 * @param user2 the user 2
	 */
	public DirectMessages(User user1, User user2) {
		this.user1 = user1;
		this.user2 = user2;
		messages = new Stack<Message>();
	}

	/**
	 * Gets user 1.
	 *
	 * @return the user 1
	 */
	public User getUser1() {
		return user1;
	}

	/**
	 * Sets user 1.
	 *
	 * @param user1 the user 1
	 */
	public void setUser1(User user1) {
		this.user1 = user1;
	}

	/**
	 * Gets user 2.
	 *
	 * @return the user 2
	 */
	public User getUser2() {
		return user2;
	}

	/**
	 * Sets user 2.
	 *
	 * @param user2 the user 2
	 */
	public void setUser2(User user2) {
		this.user2 = user2;
	}

	/**
	 * Add message.
	 *
	 * @param msg the msg
	 */
	public void addMessage(Message msg) {
		messages.push(msg);
	}

	/**
	 * Pop message message.
	 *
	 * @return the message
	 */
	public Message popMessage() {
		return messages.pop();
	}

	/**
	 * Load messages.
	 */
	public void loadMessages() {
		//TODO Implement firebase code
	}

}

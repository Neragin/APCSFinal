package com.example.fulkscord.messages;

import android.widget.TextView;

import java.util.Date;

/**
 * Individual message that will be used to display on screen
 */
public class Message {

	private String text, key, sender, receiver;
	private Date date;

	/**
	 * Instantiates a new Message.
	 *
	 * @param text     the text
	 * @param key      the key
	 * @param sender   the sender
	 * @param receiver the receiver
	 * @param date     the date
	 */
	public Message(String text, String key, String sender, String receiver, Date date) {
		this.text = text;
		this.key = key;
		this.sender = sender;
		this.date = date;
		this.receiver = receiver;
	}

	/**
	 * Gets text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets text.
	 *
	 * @param text the text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets key.
	 *
	 * @param key the key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Gets sender.
	 *
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Sets sender.
	 *
	 * @param sender the sender
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * Gets receiver.
	 *
	 * @return the receiver
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * Sets receiver.
	 *
	 * @param receiver the receiver
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * Gets date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets date.
	 *
	 * @param date the date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public String toString(){
		return sender + " to " + receiver + ": " + text;
	}
}

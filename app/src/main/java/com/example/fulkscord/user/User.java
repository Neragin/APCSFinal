package com.example.fulkscord.user;

import java.util.ArrayList;

/**
 * User object to store in database. Firebase is well integrated
 * and an ORDBMS, which means it can directly convert this object
 * into a json structured object, which is why we've done this to
 * User and several other classes.
 *
 * @author Kaustubh Khulbe
 */
public class User {

	private String username, email, phoneNumber, password;
	private ArrayList<String> friends;


	/**
	 * Instantiates a new User.
	 *
	 * @param username    the username
	 * @param email       the email
	 * @param phoneNumber the phone number
	 * @param password    the password
	 * @param friends     the friends
	 */
	public User(String username, String email, String phoneNumber, String password, ArrayList<String> friends) {
		this.username = username;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.friends = friends;
	}

	/**
	 * Gets username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets username.
	 *
	 * @param username the username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets email.
	 *
	 * @param email the email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets phone number.
	 *
	 * @return the phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets phone number.
	 *
	 * @param phoneNumber the phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets password.
	 *
	 * @param password the password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets friends.
	 *
	 * @return the friends
	 */
	public ArrayList<String> getFriends() {
		return friends;
	}

	/**
	 * Sets friends.
	 *
	 * @param friends the friends
	 */
	public void setFriends(ArrayList<String> friends) {
		this.friends = friends;
	}

	public boolean equals(Object other) {
		if (!(other instanceof User)) return false;
		User u = (User) other;
		return username.equals(u.getUsername()) && username.equals(u.getEmail());
	}

	public int hashCode() {
		return email.hashCode(); //not username since multiple users can have same username
	}

}

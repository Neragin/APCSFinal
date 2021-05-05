package com.example.fulkscord;

/**
 * Common keys used in the database
 * Used to prevent writing the wrong keys
 */
public class DatabaseKeys {

	/**
	 * The constant userKey.
	 */
	public static String userKey, friendsKey, dmKey;

	static {
		userKey = "user";
		friendsKey = "friends";
		dmKey = "dm";
	}

}

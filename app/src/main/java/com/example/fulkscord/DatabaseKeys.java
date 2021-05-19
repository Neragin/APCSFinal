package com.example.fulkscord;

/**
 * Common keys used in the database
 * Used to prevent writing the wrong keys
 *
 * @author Kaustubh Khulbe
 */
public class DatabaseKeys {

	/**
	 * The constant userKey.
	 */
	public static String userKey, friendsKey, dmKey;

	//May need to add something for all children also?

	static {
		userKey = "user";
		friendsKey = "friends";
		dmKey = "dm";
	}

}

package com.example.fulkscord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fulkscord.directMessage.DirectMessageActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This class is the title page that the app opens to
 * Here, users are allowed to either sign up and create an account,
 * or log in to a previously existing account. This activity simply
 * leads the user to one of the two proposed options.
 */
public class MainActivity extends AppCompatActivity {
	/**
	 * The Log in.
	 */
	Button logIn, /**
	 * The Sign up.
	 */
	signUp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//TEMPORARY: Meant for testing messages
//
//		Intent intent = new Intent(MainActivity.this, DirectMessageActivity.class);
//		intent.putExtra("username", "kkhulbe5");
//		intent.putExtra("friend", "bob");

		setUpButtons();

	}

	/**
	 * Defines the onClickListeners for the log in and sign up buttons --> leads to a
	 * different activity corresponding to the button clicked
	 */
	private void setUpButtons() {
		logIn = findViewById(R.id.logIn);
		logIn.setOnClickListener(v -> {
			Intent intent = new Intent(MainActivity.this, Login.class);
			startActivity(intent);
		});

		signUp = findViewById(R.id.SignUp);
		signUp.setOnClickListener(v -> {
			Intent intent = new Intent(MainActivity.this, SignUp.class);
			startActivity(intent);
		});
	}
}
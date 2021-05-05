package com.example.fulkscord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fulkscord.homeScreen.HomeScreenActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * This activity verifies a user's credentials
 * and logs them in, then takes them to Home Screen
 */
public class Login extends AppCompatActivity {

	private Button login;
	private EditText username;
	private EditText password;
	private String usernameString, passwordString;
	private DatabaseReference mDatabase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mDatabase = FirebaseDatabase.getInstance().getReference();

		login = findViewById(R.id.logIn2);

		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				username = findViewById(R.id.username);
				password = findViewById(R.id.password);
				usernameString = username.getText().toString().trim();
				passwordString = password.getText().toString().trim();


				ReceiveInformationFromText();

			}
		});
	}

	/**
	 * Extracts information from text views, verifies them using basic regex, and then logs in the user
	 */
	private void ReceiveInformationFromText() {
		logInUser();
	}

	/**
	 * Database handler code: extracts a Map of String -> Object and feeds it for credential checks
	 */
	private void logInUser() {

		mDatabase.child(DatabaseKeys.userKey).addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				checkIfCredentialsAreValid((Map<String, Object>) snapshot.getValue());
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

	}

	/**
	 * Verifies all credentials, if correct, moves on to Home Screen
	 *
	 * @param users - Map<String, Object> of users in the database
	 */
	private void checkIfCredentialsAreValid(Map<String, Object> users) {
        Toast toast = null;

		for (Map.Entry<String, Object> entry : users.entrySet()) {

			//Get user map
			Map singleUser = (Map) entry.getValue();
			//Get phone field and append to list
			if (singleUser.get("username").toString().equals(usernameString) && singleUser.get("password").toString().equals(passwordString)) {

                if (toast != null) {
                    toast.cancel();
                }
				show("Successful Login", toast);
				toast = null;
				Intent intent = new Intent(this, HomeScreenActivity.class);
				intent.putExtra("username", usernameString);
				startActivity(intent);

			} else if (!(singleUser.get("username").toString().equals(usernameString) && singleUser.get("password").toString().equals(passwordString))) {
                if (toast != null) {
                    toast.cancel();
                }
                show("Invalid Credentials", toast);
                toast = null;
			}
		}

	}
	//Toaster Method?!
	private void show (String message, Toast Toaster) {
		try
		{
			Toaster.getView().isShown();
			Toaster.setText(message);
		} catch (Exception e) {
			Toaster = Toast.makeText(this, message, Toast.LENGTH_LONG);
		}
		Toaster.show();
	}

}
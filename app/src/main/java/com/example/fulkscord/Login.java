package com.example.fulkscord;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fulkscord.homeScreen.HomeScreenActivity;
import com.google.firebase.database.*;

import java.util.Map;

/**
 * This activity verifies a user's credentials
 * and logs them in, then takes them to Home Screen
 *
 * @author Kaustubh Khulbe, Leo Xu, Niranjan Mathirajan
 * @version 1.0
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
        boolean invalid = false;
        boolean shown = false;

        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            if (singleUser.get("username").toString().equals(usernameString) && singleUser.get("password").toString().equals(passwordString)) {

                fulkster("Successful Login", toast);
                shown = true;
                Intent intent = new Intent(this, HomeScreenActivity.class);
                intent.putExtra("username", usernameString);
                startActivity(intent);


            } else if (!(singleUser.get("username").toString().equals(usernameString) && singleUser.get("password").toString().equals(passwordString))) {

                invalid = true;
//
            }
        }
        if (invalid && !shown) {
            fulkster("Invalid Credentials", toast);
            invalid = true;
            shown = false;
        }

    }

    /**
     * Meant to help display the toast.
     * Fulk's Epic Toaster Method?!
     *
     * @param message - message that toast should display
     * @param Toaster - toast object to display
     */
    private void fulkster(String message, Toast Toaster) {
        if (Toaster != null) {
            Toaster.cancel();
        }
        Toaster = Toast.makeText(this, message, Toast.LENGTH_LONG);
        Toaster.show();
    }
}

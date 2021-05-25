package com.example.fulkscord;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fulkscord.homeScreen.HomeScreenActivity;
import com.example.fulkscord.user.User;
import com.google.firebase.database.*;

import java.util.ArrayList;

/**
 * This class is the Sign Up action a user can do.
 * The class registers a new user into Firebase. It
 * goes through regex checking to verify the input data,
 * and makes sure the username is unique before creating the user.
 * It then loads up the HomeScreen activity for the user.
 *
 * @author Kaustubh Khulbe
 * @author Sources - https://stackoverflow.com/posts/16702965/revisions, https://emailregex.com/, https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a
 * @version 1.0
 */
public class SignUp extends AppCompatActivity {


    private String username, email, password, phone;
    private TextView usernameView, emailView, passwordView, phoneView;
    private Button register;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        setUpRegisterRequest();

    }

    /**
     * Initializes the input fields and the register button
     * and sets it's onClickListener
     */
    private void setUpRegisterRequest() {

        usernameView = findViewById(R.id.userName);
        emailView = findViewById(R.id.email);
        passwordView = findViewById(R.id.registrationPassword);
        phoneView = findViewById(R.id.editTextPhone);

        register = findViewById(R.id.logIn2);
        register.setOnClickListener(v -> {
            waitForDataAndCheck();
        });
    }

    /**
     * Responds to register's onClickListener. When clicked,
     * it extracts the text inputted, performs regex checks to
     * validate the data, and adds a new user to firebase iff
     * it is a unique user.
     * <p>
     * Regex obtained from Stack Overflow
     */
    private void waitForDataAndCheck() {
        username = (usernameView).getText().toString().trim();
        email = (emailView).getText().toString().trim();
        password = (passwordView).getText().toString().trim();
        phone = (phoneView).getText().toString().trim();
        System.err.println("Name: " + username + ", email: " + email + ", pwd: " + password);

        if (email == null || !email.matches(
                "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-" +
                        "\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])" +
                        "*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2" +
                        "[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:" +
                        "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])" //NOTE: This is from an online reference
        ) || email.equals("")) {

            makeNewToast("Enter a valid email");
            emailView.setHint("Email Address");
            emailView.setText("");

        } else if (username == null || !username.matches("^[A-Za-z]\\w{5,29}$")) {

            makeNewToast("Your username must be between 6 to 30 characters, start with a l" +
                    "etter, and only contain alphanumeric characters and underscores.");
            usernameView.setHint("Username");
            usernameView.setText("");

        } else if (password == null || !password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {

            makeNewToast("Please enter a valid password that: \\n is more than eight characters and contains at least 1 letter and 1 number.\"");
            passwordView.setHint("Password");
            passwordView.setText("");

        } else if (phone == null || !phone.matches("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$")) {
            makeNewToast("Please enter a valid phone number which has the Country Code attached to it.");
            phoneView.setHint("Phone Number");
            phoneView.setText("");
        } else {
            createUser();
        }
    }

    /**
     * Creates a new Toast object regarding a specific issue, if one occured
     *
     * @param issue - String message of the issue
     */
    private void makeNewToast(String issue) {
        Toast toast = Toast.makeText(this, issue, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Accessing code to the database and appends the user to it
     */
    private void createUser() {


        mDatabase.child(DatabaseKeys.userKey).child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot);
                if (dataSnapshot.exists()) {
                    // username is already taken
                    Toast toast = Toast.makeText(SignUp.this, "Username Already Exists", Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    // username is valid
                    User user = new User(username, email, phone, password, new ArrayList<String>());
                    mDatabase.child(DatabaseKeys.userKey).child(username).setValue(user);


                    goToHomeActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("cancelled");
            }
        });
    }

    /**
     * Sets up a new intent to take the user to Home Screen
     */
    private void goToHomeActivity() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}

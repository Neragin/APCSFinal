package com.example.fulkscord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fulkscord.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    String username, email, password;
    TextView usernameView, emailView, passwordView;
    Button register;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        setUpRegisterRequest();

    }

    private void setUpRegisterRequest() {

        usernameView = (TextView) findViewById(R.id.userName);
        emailView = (TextView) findViewById(R.id.email);
        passwordView = (TextView) findViewById(R.id.registrationPassword);

        register = (Button) findViewById(R.id.logIn2);
        register.setOnClickListener(v -> {
            waitForDataAndCheck();
        });
    }

    private void waitForDataAndCheck() {
        username = (usernameView).getText().toString().trim();
        email = (emailView).getText().toString().trim();
        password = (passwordView).getText().toString().trim();

        System.err.println("Name: " + username + ", email: " + email + ", pwd: " + password);

        if (email == null || !email.matches(
                "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
        ) || email.equals("")) {
            Toast toast = Toast.makeText(this, Html.fromHtml("<font color='#ff0000' ><b>" + "Please use a valid email." + "</b></font>"), Toast.LENGTH_LONG);
            toast.show();
            emailView.setHint("Email Address");
            emailView.setText("");
        } else if (username == null || !username.matches("^[A-Za-z]\\w{5,29}$")) {
            Toast toast = Toast.makeText(this, Html.fromHtml("<font color='#ff0000' ><b>" + "Your username must be between 6 to 30 characters, start with a letter, and only contain alphanumeric characters and underscores." + "</b></font>"), Toast.LENGTH_LONG);
            toast.show();
            usernameView.setHint("Username");
            usernameView.setText("");
        } else if (password == null || !password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
            Toast toast = Toast.makeText(this, Html.fromHtml("<font color='#ff0000' ><b>" +"Please enter a valid password that: \n is more than eight characters and contains at least 1 letter and 1 number." + "</b></font>"), Toast.LENGTH_LONG);
            toast.show();
            passwordView.setHint("Password");
            passwordView.setText("");
        } else {
            createUser();
        }
    }

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
                    User user = new User(username, email, "1231231234", password);
                    System.out.println("hello");
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

    private void goToHomeActivity() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
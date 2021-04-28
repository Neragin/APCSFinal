package com.example.fulkscord;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    String username, email, password;
    TextView usernameView, emailView, passwordView;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setUpRegisterRequest();

    }

    private void setUpRegisterRequest() {

        usernameView = (TextView) findViewById(R.id.userName);
        emailView = (TextView) findViewById(R.id.email);
        passwordView = (TextView) findViewById(R.id.registrationPassword);

        register = (Button) findViewById(R.id.logIn2);
        register.setOnClickListener(v -> {
            username = (usernameView).getText().toString().trim();
            email = (emailView).getText().toString().trim();
            password = (passwordView).getText().toString().trim();
            System.err.println("Name: " + username + ", email: " + email + ", pwd: " + password);

            if(email == null || !email.matches(
                    "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
            ) || email.equals("")){
                Toast toast = Toast.makeText(this, .fromHtml("<font color='#ff0000' ><b>" + "Please use a valid email." + "</b></font>");
                toast.show();
                emailView.setHint("Email Address");
                emailView.setText("");
            }

            if(username == null || !username.matches("^[A-Za-z]\\w{5,29}$")) {
                Toast toast = Toast.makeText(this, Html.fromHtml("<font color='#ff0000' ><b>" + "Your username must be between 6 to 30 characters, start with a letter, and only contain alphanumeric characters and underscores." + "</b></font>"), Toast.LENGTH_LONG);
                toast.show();
                usernameView.setHint("Username");
                usernameView.setText("");
            }

            if(password == null || !password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                Toast toast = Toast.makeText(this, "Please enter a valid password that: \n is more than eight characters and contains at least 1 letter and 1 number.", Toast.LENGTH_LONG);
                toast.show();
                passwordView.setHint("Password");
                passwordView.setText("");
            }

        });

//        if(email == null || email.equals("")) {
//
//        }

        //EMAIL VERIFICATION REGEX FROM ONLINE
        //if (username == null || username.equals(""))

    }
}
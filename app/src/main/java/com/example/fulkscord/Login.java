package com.example.fulkscord;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    Button login;
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.logIn2);

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                username = (EditText)findViewById(R.id.username);
                password = (EditText)findViewById(R.id.password);
                Toast toast = null;


                if(username.getText().toString().trim() == null || !username.getText().toString().trim().matches("^[A-Za-z]\\w{5,29}$")) {
                    if (toast != null) {
                        toast.cancel();
                    }

                    toast = Toast.makeText(Login.this, Html.fromHtml("<font color='#ff0000' ><b>" + "Invalid Password or Username" + "</b></font>"), Toast.LENGTH_LONG);
                    toast.show();

                    ((EditText)findViewById(R.id.username)).setHint("Username");
                    ((EditText)findViewById(R.id.username)).setText("");
                }

                if(password.getText().toString().trim()  == null || !password.getText().toString().trim().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {

                    if (toast != null) {
                        toast.cancel();
                    }

                    toast = Toast.makeText(Login.this, Html.fromHtml("<font color='#ff0000' ><b>" + "Invalid Password or Username" + "</b></font>"), Toast.LENGTH_LONG);
                    toast.show();
                    ((EditText)findViewById(R.id.password)).setHint("Password");
                    ((EditText)findViewById(R.id.password)).setText("");
                }

            }
        });
    }



}
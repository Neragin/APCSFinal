package com.example.fulkscord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Map;

public class Login extends AppCompatActivity {

    Button login;
    EditText username;
    EditText password;

    String usernameString, passwordString;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        login = findViewById(R.id.logIn2);

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                username = (EditText)findViewById(R.id.username);
                password = (EditText)findViewById(R.id.password);
                usernameString = username.getText().toString().trim();
                passwordString = password.getText().toString().trim();


                RecieveInformationFromText();

            }
        });
    }

    private void RecieveInformationFromText() {
        Toast toast = null;
        if(username.getText().toString().trim() == null || !username.getText().toString().trim().matches("^[A-Za-z]\\w{5,29}$")) {
            if (toast != null) {
                toast.cancel();
            }

            toast = Toast.makeText(Login.this, Html.fromHtml("<font color='#ff0000' ><b>" + "Invalid Password or Username" + "</b></font>"), Toast.LENGTH_LONG);
            toast.show();

            ((EditText)findViewById(R.id.username)).setHint("Username");
            ((EditText)findViewById(R.id.username)).setText("");
            ((EditText)findViewById(R.id.password)).setHint("Password");
            ((EditText)findViewById(R.id.password)).setText("");
        }

        else if(password.getText().toString().trim() == null || !password.getText().toString().trim().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {

            if (toast != null) {
                toast.cancel();
            }

            toast = Toast.makeText(Login.this, Html.fromHtml("<font color='#ff0000' ><b>" + "Invalid Password or Username" + "</b></font>"), Toast.LENGTH_LONG);
            toast.show();
            ((EditText)findViewById(R.id.password)).setHint("Password");
            ((EditText)findViewById(R.id.password)).setText("");
            ((EditText)findViewById(R.id.username)).setHint("Username");
            ((EditText)findViewById(R.id.username)).setText("");
        } else{
            logInUser();
        }
    }

    private void logInUser() {

        mDatabase.child(DatabaseKeys.userKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                checkIfCredentialsAreValid((Map<String,Object>) snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void checkIfCredentialsAreValid(Map<String, Object> users) {

        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            if(singleUser.get("username").toString().equals(usernameString) && singleUser.get("password").toString().equals(passwordString)){
                Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, HomeScreenActivity.class);
                intent.putExtra("username", usernameString);
                startActivity(intent);
            } else{
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        }

    }


}